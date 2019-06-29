package com.esi.project.buildit.planthire.common.message;

import com.esi.project.buildit.planthire.common.rest.ResultWithMessages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MessageCollector {

	private List<Message> messages = new ArrayList<>();

	public MessageCollector addIf(boolean shouldAddMessage, Message message) {
		if (shouldAddMessage)
			messages.add(message);
		return this;
	}

	public MessageCollector addIf(ResultWithMessages result) {
		if (result.failed()) {
			messages.addAll(result.getErrors());
		}
		return this;
	}

	public MessageCollector add(Message message) {
		messages.add(message);
		return this;
	}

	public MessageCollector add(Collection<Message> messagesToAdd) {
		messages.addAll(messagesToAdd);
		return this;
	}

	public List<Message> collect() {
		return messages;
	}

	public boolean isEmpty() {
		return messages.isEmpty();
	}


}