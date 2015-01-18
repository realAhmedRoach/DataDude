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

import org.datadude.gui.*;
import org.datadude.nodes.*;

import com.alee.extended.statusbar.WebMemoryBar;

public class CoreEngine extends JFrame {

	private static final long serialVersionUID = 1295L;

	private JPanel contentPane;
	JButton files, newFile, chat, settings, quit, server;
	JLabel lblCurrfolder;
	JProgressBar progressBar;
	public static JTabbedPane editorPane;

	public static int currentTab = 0;
	private final ArrayList<Node> nodes = new ArrayList<>();

	private ActionListener quitListener = (ActionEvent e) -> {
		exit();
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

	private void exit() {
		try {
			Login.getUser().save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showHelp() {
		JFrame f = new JFrame("About & Help");
		f.getContentPane().add(new JLabel(DataDude.HTML_HLP_TXT));
		f.setSize(300, 540);
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
		currentTab++;
		editorPane.setTabComponentAt(currentTab, new ButtonTabComponent(true));
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
		editorPane.setTitleAt(index, title);
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
				exit();
			}
		});
		// }
		// SETTING VALUES {
		System.out.println("Setting Values");
		setTitle(DataDude.VERSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(874, 625);
		// }

		// MENU ITEMS {
		System.out.println("Initializing Menu Items");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/page_add.png")));
		mntmNew.addActionListener(newListener);
		mnFile.add(mntmNew);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/cross.png")));
		mntmQuit.addActionListener(quitListener);
		mnFile.add(mntmQuit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmShowAbout = new JMenuItem("Show About & Help");
		mntmShowAbout.addActionListener((ActionEvent e) -> showHelp());
		mnHelp.add(mntmShowAbout);
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
		infoPanel.setBorder(new TitledBorder(null, "Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		progressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 21, 0));
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
		server.setIcon(new ImageIcon(CoreEngine.class
				.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		server.setEnabled(false);
		commandPanel.add(server);

		chat = new JButton("Chat");
		chat.setIcon(new ImageIcon(CoreEngine.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		chat.setEnabled(false);
		commandPanel.add(chat);

		settings = new JButton("Settings");
		settings.setIcon(new ImageIcon(CoreEngine.class
				.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		settings.addActionListener(settingsListener);
		commandPanel.add(settings);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener((ActionEvent e) -> {
			lblCurrfolder.setText("<html><b>User Folder:</b> " + Login.getUser().getUserFolder() + "</html>");
			lblWelcome.setText("Welcome, " + Login.getUser().getName());
			revalidate();
			repaint();
		});
		commandPanel.add(btnRefresh);

		quit = new JButton("Quit");
		commandPanel.add(quit);
		quit.setIcon(new ImageIcon(CoreEngine.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
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
		editorPane.add(n);
		editorPane.setTitleAt(currentTab, "Welcome!");
		editorPane.setTabComponentAt(currentTab, new ButtonTabComponent(false));

		contentPane.setLayout(new BorderLayout(0, 5));
		contentPane.add(infoPanel, BorderLayout.NORTH);
		contentPane.add(editorPane, BorderLayout.CENTER);
		contentPane.add(commandPanel, BorderLayout.EAST);
		contentPane.add(progressPanel, BorderLayout.SOUTH);

		long end = System.currentTimeMillis();

		System.out.println("\nCompleted Initializing Core Engine\nTotal time: " + (end - start) + "ms");
		// }
		// }

	}
}
