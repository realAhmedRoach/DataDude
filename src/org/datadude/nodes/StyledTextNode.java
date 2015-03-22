package org.datadude.nodes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.*;

import org.datadude.DataDude;
import org.datadude.Login;

public class StyledTextNode extends BasicNode {

	private static final long serialVersionUID = 3467L;

	JTextPane textPane;
	AbstractDocument doc;
	static final int MAX_CHARACTERS = 300;
	String newline = System.getProperty("line.seperator");
	HashMap<Object, Action> actions;

	// undo helpers
	protected UndoAction undoAction;
	protected RedoAction redoAction;
	protected UndoManager undo = new UndoManager();

	public StyledTextNode(String _title) {
		super(_title);
		init();
		// Create the text pane and configure it.
		textPane = new JTextPane();
		textPane.setCaretPosition(0);
		textPane.setMargin(new Insets(5, 5, 5, 5));
		StyledDocument styledDoc = textPane.getStyledDocument();
		if (styledDoc instanceof AbstractDocument) {
			doc = (AbstractDocument) styledDoc;
		} else {
			System.err.println("Text pane's document isn't an AbstractDocument!");
			System.exit(-1);
		}
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setPreferredSize(new Dimension(300, 300));

		// Add the component.
		pane.add(scrollPane, BorderLayout.CENTER);

		// Set up the menu bar.
		actions = createActionTable(textPane);
		createEditMenu();
		JMenu styleMenu = createStyleMenu();
		menuBar.add(styleMenu);
		setJMenuBar(menuBar);

		// Add some key bindings.
		addBindings();
		textPane.setCaretPosition(0);

		// Start watching for undoable edits and caret changes.
		doc.addUndoableEditListener(new MyUndoableEditListener());
	}

	// This one listens for edits that can be undone.
	protected class MyUndoableEditListener implements UndoableEditListener {

		public void undoableEditHappened(UndoableEditEvent e) {
			// Remember the edit and update the menus.
			undo.addEdit(e.getEdit());
			undoAction.updateUndoState();
			redoAction.updateRedoState();
		}
	}

	// And this one listens for any changes to the document.
	// Add a couple of emacs key bindings for navigation.
	protected void addBindings() {
		InputMap inputMap = textPane.getInputMap();

		// Ctrl-b to go backward one character
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
		inputMap.put(key, DefaultEditorKit.backwardAction);

		// Ctrl-f to go forward one character
		key = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);
		inputMap.put(key, DefaultEditorKit.forwardAction);

		// Ctrl-p to go up one line
		key = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK);
		inputMap.put(key, DefaultEditorKit.upAction);

		// Ctrl-n to go down one line
		key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
		inputMap.put(key, DefaultEditorKit.downAction);
	}

	// Create the edit menu.
	protected JMenu createEditMenu() {
		// Undo and redo are actions of our own creation.
		undoAction = new UndoAction();
		editM.add(undoAction);

		redoAction = new RedoAction();
		editM.add(redoAction);

		return editM;
	}

	// Create the style menu.
	protected JMenu createStyleMenu() {
		JMenu menu = new JMenu("Style");

		JMenu fontype = new JMenu("Font Type");

		Action action = new StyledEditorKit.BoldAction();
		action.putValue(Action.NAME, "Bold");
		fontype.add(action);

		action = new StyledEditorKit.ItalicAction();
		action.putValue(Action.NAME, "Italic");
		fontype.add(action);

		action = new StyledEditorKit.UnderlineAction();
		action.putValue(Action.NAME, "Underline");
		fontype.add(action);

		menu.add(fontype);

		JMenu fontsize = new JMenu("Font Size");

		fontsize.add(new StyledEditorKit.FontSizeAction("12", 12));
		fontsize.add(new StyledEditorKit.FontSizeAction("14", 14));
		fontsize.add(new StyledEditorKit.FontSizeAction("18", 18));
		fontsize.add(new StyledEditorKit.FontSizeAction("21", 21));
		fontsize.add(new StyledEditorKit.FontSizeAction("72", 72));

		menu.add(fontsize);

		JMenu font = new JMenu("Font");

		font.add(new StyledEditorKit.FontFamilyAction("Serif", "Serif"));
		font.add(new StyledEditorKit.FontFamilyAction("SansSerif", "SansSerif"));
		font.add(new StyledEditorKit.FontFamilyAction("Consolas", "Consolas"));

		menu.add(font);

		JMenu color = new JMenu("Color");

		color.add(new StyledEditorKit.ForegroundAction("Red", Color.red));
		color.add(new StyledEditorKit.ForegroundAction("Green", Color.green));
		color.add(new StyledEditorKit.ForegroundAction("Blue", Color.blue));
		color.add(new StyledEditorKit.ForegroundAction("Black", Color.black));

		menu.add(color);

		return menu;
	}

	// The following two methods allow us to find an
	// action provided by the editor kit by its name.
	private HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
		HashMap<Object, Action> actions = new HashMap<Object, Action>();
		Action[] actionsArray = textComponent.getActions();
		for (int i = 0; i < actionsArray.length; i++) {
			Action a = actionsArray[i];
			actions.put(a.getValue(Action.NAME), a);
		}
		return actions;
	}

	private Action getActionByName(String name) {
		return actions.get(name);
	}

	class UndoAction extends AbstractAction {

		private static final long serialVersionUID = 1356L;

		public UndoAction() {
			super("Undo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undo.undo();
			} catch (CannotUndoException ex) {
				System.out.println("Unable to undo: " + ex);
				ex.printStackTrace();
			}
			updateUndoState();
			redoAction.updateRedoState();
		}

		protected void updateUndoState() {
			if (undo.canUndo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getUndoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "Undo");
			}
		}
	}

	class RedoAction extends AbstractAction {

		private static final long serialVersionUID = 456L;

		public RedoAction() {
			super("Redo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undo.redo();
			} catch (CannotRedoException ex) {
				System.out.println("Unable to redo: " + ex);
				ex.printStackTrace();
			}
			updateRedoState();
			undoAction.updateUndoState();
		}

		protected void updateRedoState() {
			if (undo.canRedo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getRedoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "Redo");
			}
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		final StyledTextNode node = new StyledTextNode("StyledText");

		// Display the window.
		node.setSize(500, 500);
		node.setVisible(true);
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(node);
		f.pack();
		f.setVisible(true);
	}

	// The standard main method.
	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == loadI) {
			JFileChooser l = new JFileChooser();
			l.setCurrentDirectory(new File(Login.getUser().getUserFolder()));
			l.setDialogTitle("Open Styled Text File");
			l.showOpenDialog(this);
			if (load(l.getSelectedFile().getAbsolutePath()))
				lblStatus.setText("Succesfully loaded text file");
			else
				lblStatus.setText("Error while loading!");
		} else if (choice == cutI) {
			DataDude.setClipboard(textPane.getSelectedText());
			textPane.replaceSelection("");
		} else if (choice == copyI)
			DataDude.setClipboard(textPane.getSelectedText());
		else if (choice == pasteI)
			try {
				textPane.getDocument().insertString(textPane.getCaretPosition(), DataDude.getClipboard(), null);
			} catch (BadLocationException e1) {
				DataDude.showError(e1, "Bad Location");
			}
		else if (choice == selectI)
			textPane.selectAll();
	}

	public boolean load(String filename) {
		textPane.setContentType("text/html");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			String line = null;
			while ((line = reader.readLine()) != null) {
				textPane.setText(textPane.getText() + line + "\n");
			}

			reader.close();

			return true;
		} catch (Exception ex) {
			DataDude.showError(ex);
			return false;
		}
	}

	@Override
	public boolean save(String file) {
		File saveFile = new File(file);
		if (textPane.getText().length() > 0) {

			StyledDocument doc = (StyledDocument) textPane.getDocument();

			HTMLEditorKit kit = new HTMLEditorKit();

			BufferedOutputStream out;

			try {
				out = new BufferedOutputStream(new FileOutputStream(saveFile));

				kit.write(out, doc, doc.getStartPosition().getOffset(), doc.getLength());
			} catch (FileNotFoundException e) {
				return false;
			} catch (IOException e) {
				return false;
			} catch (BadLocationException e) {
				return false;
			}
		}
		return true;
	}

}
