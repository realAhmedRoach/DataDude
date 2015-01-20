package org.datadude.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

public final class StandardDialog {

	public StandardDialog(JFrame aOwner, String title, boolean isModal, int onClose, JPanel aBody,
			java.util.List<JButton> aButtons) {
		fDialog = new JDialog(aOwner, title, isModal);
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		aBody.setAlignmentX(Component.CENTER_ALIGNMENT);
		content.add(aBody);
		content.add(Box.createVerticalStrut(10));
		content.add(buildButtonPanel(aButtons));
		fDialog.add(content);
		fDialog.setResizable(false);
		fDialog.setDefaultCloseOperation(onClose);
		addCancelByEscapeKey(onClose);
	}

	public void display() {
		fDialog.pack();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension window = fDialog.getSize();
		// ensure that no parts of aWindow will be off-screen
		if (window.height > screen.height) {
			window.height = screen.height;
		}
		if (window.width > screen.width) {
			window.width = screen.width;
		}
		int xCoord = (screen.width / 2 - window.width / 2);
		int yCoord = (screen.height / 2 - window.height / 2);
		fDialog.setLocation(xCoord, yCoord);

		fDialog.setVisible(true);
	}

	public void setDefaultButton(JButton aButton) {
		fDialog.getRootPane().setDefaultButton(aButton);
	}

	public void dispose() {
		fDialog.dispose();
	}

	public JDialog getDialog() {
		return fDialog;
	}

	// PRIVATE
	private JDialog fDialog;

	private void addCancelByEscapeKey(final int aOnClose) {
		String CANCEL_ACTION_KEY = "CANCEL_ACTION_KEY";
		int noModifiers = 0;
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, noModifiers, false);
		InputMap inputMap = fDialog.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(escapeKey, CANCEL_ACTION_KEY);
		AbstractAction cancelAction = new AbstractAction() {
			private static final long serialVersionUID = 2345L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (JFrame.DO_NOTHING_ON_CLOSE == aOnClose) {
					// do nothing
				} else if (JFrame.DISPOSE_ON_CLOSE == aOnClose) {
					fDialog.dispose();
				} else if (JFrame.HIDE_ON_CLOSE == aOnClose) {
					fDialog.setVisible(false);
				} else if (JFrame.EXIT_ON_CLOSE == aOnClose) {
					fDialog.dispose();
					System.exit(0);
				} else {
					throw new AssertionError("Unexpected branch for this value of OnClose: " + aOnClose);
				}
			}
		};
		fDialog.getRootPane().getActionMap().put(CANCEL_ACTION_KEY, cancelAction);
	}

	private JPanel buildButtonPanel(java.util.List<JButton> aButtons) {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.LINE_AXIS));
		result.add(Box.createHorizontalGlue());
		int count = 0;
		for (JButton button : aButtons) {
			count++;
			result.add(button);
			if (count < aButtons.size()) {
				result.add(Box.createHorizontalStrut(6));
			}
		}
		result.add(Box.createHorizontalGlue());
		return result;
	}
}
