package com.esi.project.buildit.planthire.common.message;



import java.util.Objects;


public class Message {
	private String code;
	private String reason;

	private Message(String code) {
		this.code = code;
	}

	private Message(String code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	public String getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Message message = (Message) o;

		return Objects.equals(code, message.code);
	}

	@Override
	public int hashCode() {
		return code != null ? code.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Message{" +
				"code='" + code + '\'' +
				", reason='" + reason + '\'' +
				'}';
	}


	public static Message message(String code) {
		return new Message(code);
	}

	public static Message message(String code, String reason) {
		return new Message(code, reason);
	}


}
