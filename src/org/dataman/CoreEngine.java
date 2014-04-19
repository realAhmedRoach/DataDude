package org.dataman;

import java.awt.*;

import javax.swing.*;

import org.dataman.gui.*;
import org.dataman.nodes.*;

import java.io.*;
import java.awt.event.*;

import javax.swing.border.*;

import com.petersoft.advancedswing.jclosabletabbedpane.JClosableTabbedPane;
import com.petersoft.white.FileChooserTreeModel;

import javax.swing.tree.*;



public class CoreEngine extends JFrame {
	private static final long serialVersionUID = 1295L;

	private JPanel contentPane;
	JPanel nodePanel;
	JPanel box1;
	GridLayout box1l;
	JPanel box2;
	GridLayout box2l;
	JLabel welcome;
	JLabel currfold;
	JScrollPane a;
	JButton files;
	JButton newFile;
	JButton chat;
	JButton settings;
	JButton quit;
	String nodeSelection;
	JLabel lblCurrfolder;
	JProgressBar progressBar;

	private ActionListener quitListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			exit();
		}

	};

	private ActionListener newListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			NewDialog.init();
		}
	};

	private ActionListener settingsListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			SettingsDialog.init();
		}
	};
	
	private void exit() {
		try {
			Login.getUser().save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoreEngine frame = new CoreEngine();
					UIManager.setLookAndFeel("org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CoreEngine() {
		System.out.println("\nInside Core Engine Constructor");
		// SET ICON {
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						CoreEngine.class
								.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		// }
		// WINDOW LISTENER{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		// }
		// SETTING VALUES {
		setTitle(Login.VERSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 874, 625);
		// }

		// MENU ITEMS {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.addActionListener(newListener);
		mnFile.add(mntmNew);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		// }

		// CONTENT {
		contentPane = new JPanel();
		contentPane.setBackground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// INFO PANEL {
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.ORANGE);
		infoPanel.setBorder(new TitledBorder(null, "Info",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

		JLabel lblWelcome = new JLabel("Welcome "
				+ Login.getUser().getUserName());
		infoPanel.add(lblWelcome);

		lblCurrfolder = new JLabel(Login.getUser().getUserFolder());
		infoPanel.add(lblCurrfolder);

		JButton btnNew = new JButton("New");
		btnNew.addActionListener(newListener);
		infoPanel.add(btnNew);

		JButton btnFiles = new JButton("Files");
		infoPanel.add(btnFiles);
		// }

		// PROGRESS PANEL {
		JPanel progresPanel = new JPanel();
		progresPanel.setBackground(new Color(46, 139, 87));

		progressBar = new JProgressBar();
		progressBar.setToolTipText("Not Loading");
		progressBar.setStringPainted(true);
		progresPanel.add(progressBar);
		// }

		// COMMAND PANEL {
		JPanel commandPanel = new JPanel();
		commandPanel.setBackground(Color.ORANGE);
		commandPanel.setBorder(new TitledBorder(null, "Commands",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));

		JButton btnStartServer = new JButton("Start Server");
		btnStartServer
				.setIcon(new ImageIcon(
						CoreEngine.class
								.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		commandPanel.add(btnStartServer);

		JButton btnChat = new JButton("Chat");
		btnChat.setIcon(new ImageIcon(
				CoreEngine.class
						.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		commandPanel.add(btnChat);

		JButton btnSettings = new JButton("Settings");
		btnSettings
				.setIcon(new ImageIcon(
						CoreEngine.class
								.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		btnSettings.addActionListener(settingsListener);
		commandPanel.add(btnSettings);

		JButton btnQuit = new JButton("Quit");
		commandPanel.add(btnQuit);
		btnQuit.setIcon(new ImageIcon(
				CoreEngine.class
						.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		// }

		// EDITOR {
		WelcomeNode n;
		n = new WelcomeNode();
		n.setBackground(Color.WHITE);

		JClosableTabbedPane editorPane = new JClosableTabbedPane();
		editorPane.setBorder(new TitledBorder(null, "Editor",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editorPane.setLayout(new FlowLayout());
		editorPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		editorPane.setBackground(Color.GRAY);
		editorPane.add(n);
		editorPane.setTitleAt(0, "Welcome!");
		editorPane.setEnabledAt(0, true);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(infoPanel, BorderLayout.NORTH);
		contentPane.add(editorPane, BorderLayout.CENTER);
		contentPane.add(commandPanel, BorderLayout.EAST);
		contentPane.add(progresPanel, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setBorder(new TitledBorder(null, "File Explorer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.WEST);
		
		JTree tree = new JTree();
		//TreeModel model = new FileTreeModel(new File(System.getProperty("user.dir")));
		//tree.setModel(model);
		panel.add(tree);
		// }
		// }
		System.out.println("\n Completed Initializing Core Engine");
	}
}
