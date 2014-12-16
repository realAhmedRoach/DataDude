/*******************************************************************************
 *     DataDude is a data managing application designed to have many types of data in one application
 *     Copyright (C) 2015  Ahmed R. (theTechnoKid)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package org.datadude;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.datadude.gui.NewDialog;
import org.datadude.gui.SettingsDialog;
import org.datadude.nodes.Node;
import org.datadude.nodes.WelcomeNode;

import com.petersoft.advancedswing.jclosabletabbedpane.JClosableTabbedPane;

public class CoreEngine extends JFrame {
	private static final long serialVersionUID = 1295L;

	private JPanel contentPane;
	JPanel nodePanel;
	JPanel box1;
	GridLayout box1l;
	JPanel box2;
	GridLayout box2l;
	JLabel welcome;
	JScrollPane a;
	JButton files, newFile, chat, settings, quit;
	JLabel lblCurrfolder;
	JProgressBar progressBar;
	static JClosableTabbedPane editorPane;
	static int x = 0;

	private ActionListener quitListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			exit();
		}

	};

	private ActionListener newListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			NewDialog.init();
		}
	};

	private ActionListener settingsListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			SettingsDialog.init();
		}
	};

	private void exit() {
		try {
			Login.getUser().save();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
					CoreEngine usableEngine = new CoreEngine();
					usableEngine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void addTab(Node n) {
		editorPane.addTab(n.getTitle(), n);
		System.out.println(editorPane.getTabCount());
		editorPane.setSelectedIndex(editorPane.getTabCount()-1);
	}

	/**
	 * Create the frame.
	 */
	public CoreEngine() {
		long start = System.currentTimeMillis();
		System.out.println("\nInside Core Engine Constructor");
		// SET ICON {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				CoreEngine.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
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
		setBounds(100, 100, 874, 625);
		// }

		// MENU ITEMS {
		System.out.println("Initializing Menu Items");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.addActionListener(newListener);
		mnFile.add(mntmNew);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(quitListener);
		mnFile.add(mntmQuit);
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

		JLabel lblWelcome = new JLabel("Welcome " + Login.getUser().getName());
		infoPanel.add(lblWelcome);

		lblCurrfolder = new JLabel("<html><b>User Folder:</b> " + Login.getUser().getUserFolder()+"</html>");
		infoPanel.add(lblCurrfolder);

		JButton btnNew = new JButton("New");
		btnNew.addActionListener(newListener);
		infoPanel.add(btnNew);

		JButton btnFiles = new JButton("Files");
		infoPanel.add(btnFiles);
		// }

		// PROGRESS PANEL {
		System.out.println("Initializing Progress Panel");
		JPanel progressPanel = new JPanel();
		progressPanel.setBackground(new Color(46, 139, 87));

		progressBar = new JProgressBar();
		progressBar.setToolTipText("Not Loading");
		progressBar.setStringPainted(true);
		progressPanel.add(progressBar);
		// }

		// COMMAND PANEL {
		System.out.println("Initializing Command Panel");
		JPanel commandPanel = new JPanel();
		commandPanel.setBackground(Color.ORANGE);
		commandPanel.setBorder(new TitledBorder(null, "Commands", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));

		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.setIcon(new ImageIcon(CoreEngine.class
				.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		commandPanel.add(btnStartServer);

		JButton btnChat = new JButton("Chat");
		btnChat.setIcon(new ImageIcon(CoreEngine.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		commandPanel.add(btnChat);

		JButton btnSettings = new JButton("Settings");
		btnSettings.setIcon(new ImageIcon(CoreEngine.class
				.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		btnSettings.addActionListener(settingsListener);
		commandPanel.add(btnSettings);

		JButton btnQuit = new JButton("Quit");
		commandPanel.add(btnQuit);
		btnQuit.setIcon(new ImageIcon(CoreEngine.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		btnQuit.addActionListener(quitListener);
		// }

		System.gc();

		// EDITOR {
		System.out.println("Initializing Editor");
		WelcomeNode n;
		n = new WelcomeNode();
		// n.setIcon(true);
		n.setBackground(Color.WHITE);

		editorPane = new JClosableTabbedPane();
		editorPane.setBorder(new TitledBorder(null, "Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editorPane.setLayout(new FlowLayout());
		editorPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		editorPane.setBackground(Color.GRAY);
		editorPane.add(n);
		editorPane.setTitleAt(x, "Welcome!");
//		editorPane.setEnabledAt(x, true);
//		x++;
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(infoPanel, BorderLayout.NORTH);
		contentPane.add(editorPane, BorderLayout.CENTER);
		contentPane.add(commandPanel, BorderLayout.EAST);
		contentPane.add(progressPanel, BorderLayout.SOUTH);

		/*
		 * JPanel panel = new JPanel(); panel.setBackground(Color.CYAN);
		 * panel.setBorder(new TitledBorder(null, "File Explorer",
		 * TitledBorder.LEADING, TitledBorder.TOP, null, null)); panel.add(new
		 * FileTree()); contentPane.add(panel, BorderLayout.WEST);
		 */

		long end = System.currentTimeMillis();

		System.out.println("\nCompleted Initializing Core Engine" + "\nTotal time: " + (end - start) + "ms");
		// }
		// }

	}
}
