package com.supachok.exam.scoreannouncement.dto;

import java.util.ArrayList;
import java.util.List;

public class Linemessage {
	List<Message>messages = new ArrayList<>();

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(Message messages) {
		this.messages.add(messages);
	}
	
	
	
}
