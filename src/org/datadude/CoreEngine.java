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
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

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
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.datadude.gui.NewDialog;
import org.datadude.gui.SettingsDialog;
import org.datadude.nodes.BasicNode;
import org.datadude.nodes.WelcomeNode;

import com.petersoft.advancedswing.jclosabletabbedpane.JClosableTabbedPane;

public class CoreEngine extends JFrame {
	private static final long serialVersionUID = 1295L;

	private JPanel contentPane;
	JButton files, newFile, chat, settings, quit, server;
	JLabel lblCurrfolder;
	JProgressBar progressBar;
	static JClosableTabbedPane editorPane;
	static int x = 0;

	private ActionListener quitListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			exit();
			System.exit(0);
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

	private ActionListener filesListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				Desktop.getDesktop().open(new File(Login.getUser().getUserFolder()));
			} catch (IOException ioe) {
				/* How did this happen ? */
			}
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
					CoreEngine usableEngine = new CoreEngine();
					usableEngine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void addTab(BasicNode n) {
		editorPane.addTab(n.getTitle(), n);
		editorPane.setSelectedIndex(editorPane.getTabCount() - 1);
	}

	/**
	 * Create the frame.
	 */
	public CoreEngine() {
		long start = System.currentTimeMillis();
		System.out.println("\nInside Core Engine Constructor");
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

		lblCurrfolder = new JLabel("<html><b>User Folder:</b> " + Login.getUser().getUserFolder() + "</html>");
		infoPanel.add(lblCurrfolder);

		newFile = new JButton("New");
		newFile.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/page_add.png")));
		newFile.addActionListener(newListener);
		infoPanel.add(newFile);

		JButton files = new JButton("Files");
		files.addActionListener(filesListener);
		files.setToolTipText("Opens platform explorer to your directory");
		files.setIcon(new ImageIcon(CoreEngine.class.getResource("/images/silk/icons/application_view_detail.png")));
		infoPanel.add(files);
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
		commandPanel.setBackground(Color.YELLOW);
		commandPanel.setBorder(new TitledBorder("Commands"));
		commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));

		JButton server = new JButton("Start Server");
		server.setIcon(new ImageIcon(CoreEngine.class
				.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		server.setEnabled(false);
		commandPanel.add(server);

		chat = new JButton("Chat");
		chat.setIcon(new ImageIcon(CoreEngine.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		chat.setEnabled(false);
		commandPanel.add(chat);

		JButton settings = new JButton("Settings");
		settings.setIcon(new ImageIcon(CoreEngine.class
				.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		settings.addActionListener(settingsListener);
		commandPanel.add(settings);

		JButton quit = new JButton("Quit");
		commandPanel.add(quit);
		quit.setIcon(new ImageIcon(CoreEngine.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		quit.addActionListener(quitListener);
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

		contentPane.setLayout(new BorderLayout(0, 5));
		contentPane.add(infoPanel, BorderLayout.NORTH);
		contentPane.add(editorPane, BorderLayout.CENTER);
		contentPane.add(commandPanel, BorderLayout.EAST);
		contentPane.add(progressPanel, BorderLayout.SOUTH);

		long end = System.currentTimeMillis();

		System.out.println("\nCompleted Initializing Core Engine" + "\nTotal time: " + (end - start) + "ms");
		// }
		// }

	}
}
