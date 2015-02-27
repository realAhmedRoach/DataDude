package org.datadude.chat;

import java.io.*;

/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server.
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class ChatMessage implements Serializable {

	protected static final long serialVersionUID = 1112122200L;

	static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
	private int type;
	private String message;

	ChatMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}

	int getType() {
		return type;
	}

	String getMessage() {
		return message;
	}
}
