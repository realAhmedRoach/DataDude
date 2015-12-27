/*******************************************************************************
 * DataDude is a data managing application designed to have many types of data
 * in one application Copyright (C) 2015 Ahmed R. (theTechnoKid)
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.datadude;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.datadude.chat.ClientGUI;
import org.datadude.chat.ServerGUI;
import org.datadude.gui.*;
import org.datadude.nodes.*;

import com.alee.extended.statusbar.WebMemoryBar;

public class CoreEngine extends JFrame {

	private static final long serialVersionUID = 1295L;

	private JPanel contentPane;
	private FileTree tree = new FileTree(Login.getUser().getUserFolder());
	JButton files, newFile, chat, settings, quit, server;
	JLabel lblCurrfolder;
	JProgressBar progressBar;
	public static JTabbedPane editorPane;

	public static int currentTab = 0;
	private final ArrayList<Node> nodes = new ArrayList<>();

	private ActionListener quitListener = (ActionEvent e) -> {
		DataDude.exit();
		System.exit(0);
	};

	private ActionListener newListener = (ActionEvent e) -> NewDialog.init();

	private ActionListener settingsListener = (ActionEvent e) -> SettingsDialog.init();

	private ActionListener filesListener = (ActionEvent e) -> {
		try {
			Desktop.getDesktop().open(new File(Login.getUser().getUserFolder()));
		} catch (IOException ioe) {
			/* WTF? */
		}
	};

	private void showHelp() {
		JFrame f = new JFrame("About & Help");
		f.getContentPane().add(new JLabel(DataDude.HTML_HLP_TXT));
		f.setSize(300, 540);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private void showCredits() {
		JFrame f = new JFrame("Credits");
		f.getContentPane().add(new JLabel(DataDude.HTML_CRDT_TXT));
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public static void init() {
		EventQueue.invokeLater(() -> {
			try {
				CoreEngine usableEngine = new CoreEngine();
				usableEngine.setVisible(true);
				DataDude.setCurrentEngine(usableEngine);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void addTab(BasicNode n) {
		nodes.add(n);
		editorPane.addTab(n.getTitle(), n);
		editorPane.setSelectedIndex(editorPane.getTabCount() - 1);
		editorPane.setTabComponentAt(currentTab, new ButtonTabComponent(true));
		currentTab = editorPane.getTabCount();
	}

	public void removeTab(Node n) {
		editorPane.removeTabAt(nodes.indexOf(n));
		nodes.remove(n);
		currentTab--;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setTitleAt(int index, String title) {
		if (editorPane.getTabCount()>0)
			editorPane.setTitleAt(index-1, title);
	}

	/**
	 * Create the frame.
	 */
	public CoreEngine() {
		long start = System.currentTimeMillis();
		// SET ICON {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
		// }
		// WINDOW LISTENER{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DataDude.exit();
			}
		});
		// }
		// SETTING VALUES {
		System.out.println("Setting Values");
		setTitle(DataDude.VERSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(874, 625);
		setLocationRelativeTo(null);
		// }

		// MENU ITEMS {
		System.out.println("Initializing Menu Items");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/page_add.png")));
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		mntmNew.addActionListener(newListener);
		mnFile.add(mntmNew);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/cross.png")));
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		mntmQuit.addActionListener(quitListener);

		JMenuItem mntmLoad = new JMenuItem("Load...");
		mntmLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		mntmLoad.addActionListener((ActionEvent e) -> {
			String toLoad = DataDude.getFile();
			addTab(DataDude.open(toLoad));
		});
		mnFile.add(mntmLoad);
		mnFile.add(mntmQuit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmShowAbout = new JMenuItem("Show About & Help");
		mntmShowAbout.addActionListener((ActionEvent e) -> showHelp());
		mnHelp.add(mntmShowAbout);

		JMenuItem mntmCredits = new JMenuItem("Credits");
		mntmCredits.addActionListener((ActionEvent e) -> showCredits());
		mnHelp.add(mntmCredits);
		// }

		// CONTENT {
		System.out.println("Adding Content");
		contentPane = new JPanel();
		contentPane.setBackground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// INFO PANEL {
		System.out.println("Adding Info Panel");
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.ORANGE);
		infoPanel.setBorder(new TitledBorder("Info"));
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

		JLabel lblWelcome = new JLabel("Welcome, " + Login.getUser().getName());
		infoPanel.add(lblWelcome);

		lblCurrfolder = new JLabel("<html><b>User Folder:</b> " + Login.getUser().getUserFolder() + "</html>");
		infoPanel.add(lblCurrfolder);

		newFile = new JButton("New");
		newFile.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/page_add.png")));
		newFile.addActionListener(newListener);
		infoPanel.add(newFile);

		files = new JButton("Files");
		files.addActionListener(filesListener);
		files.setToolTipText("Opens platform explorer to your directory");
		files.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/application_view_detail.png")));
		infoPanel.add(files);
		// }

		// PROGRESS PANEL {
		System.out.println("Initializing Progress Panel");
		JPanel progressPanel = new JPanel();
		progressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 21, 5));
		progressPanel.setBackground(new Color(46, 139, 87));

		progressBar = new JProgressBar();
		progressBar.setToolTipText("Not Loading");
		progressBar.setStringPainted(true);

		WebMemoryBar memBar = new WebMemoryBar();
		memBar.setShowMaximumMemory(true);
		progressPanel.add(progressBar);
		progressPanel.add(memBar);
		// }

		// COMMAND PANEL {
		System.out.println("Initializing Command Panel");
		JPanel commandPanel = new JPanel();
		commandPanel.setBackground(Color.YELLOW);
		commandPanel.setBorder(new TitledBorder("Commands"));
		commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));

		server = new JButton("Start Server");
		server.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/application_osx_terminal.png")));
		server.addActionListener((ActionEvent e)->{
			new ServerGUI(1500);
		});
		commandPanel.add(server);

		chat = new JButton("Chat");
		chat.setIcon(new ImageIcon(CoreEngine.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		chat.addActionListener((ActionEvent e) ->{
			new ClientGUI("",1500);
		});
		commandPanel.add(chat);

		settings = new JButton("Settings");
		settings.setIcon(new ImageIcon(getClass().getResource("/images/silk/icons/wrench.png")));
		settings.addActionListener(settingsListener);
		commandPanel.add(settings);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/arrow_refresh.png")));
		btnRefresh.addActionListener((ActionEvent e) -> {
			lblCurrfolder.setText("<html><b>User Folder:</b> " + Login.getUser().getUserFolder() + "</html>");
			lblWelcome.setText("Welcome, " + Login.getUser().getName());
			tree.refresh();
			revalidate();
			repaint();
		});
		commandPanel.add(btnRefresh);

		quit = new JButton("Quit");
		commandPanel.add(quit);
		quit.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/cross.png")));
		quit.addActionListener(quitListener);
		// }

		System.gc();

		// EDITOR {
		System.out.println("Initializing Editor");
		WelcomeNode n;
		n = new WelcomeNode();
		nodes.add(n);
		n.setBackground(Color.WHITE);

		editorPane = new JTabbedPane();
		editorPane
				.setBorder(new TitledBorder(null, "Editor", TitledBorder.LEADING, TitledBorder.TOP, null, Color.CYAN));
		editorPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		editorPane.setBackground(new Color(255, 153, 102));
		if (Main.getFirstTime()) {
			editorPane.add(n);
			editorPane.setTitleAt(currentTab, "Welcome!");
			editorPane.setTabComponentAt(currentTab, new ButtonTabComponent(false));
			currentTab++;
		}

		contentPane.setLayout(new BorderLayout(0, 5));
		contentPane.add(infoPanel, BorderLayout.NORTH);
		contentPane.add(editorPane, BorderLayout.CENTER);
		contentPane.add(commandPanel, BorderLayout.EAST);
		contentPane.add(tree.getPane(), BorderLayout.WEST);
		contentPane.add(progressPanel, BorderLayout.SOUTH);

		long end = System.currentTimeMillis();

		System.out.println("\nCompleted Initializing Core Engine\nTotal time: " + (end - start) + "ms");
		// }
		// }
	}
}
