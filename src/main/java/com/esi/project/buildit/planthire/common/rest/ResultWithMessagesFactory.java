package com.esi.project.buildit.planthire.common.rest;


import com.esi.project.buildit.planthire.common.message.Message;

import java.util.Collection;

public class ResultWithMessagesFactory {

    public static ResultWithMessages error(Collection<Message> errors) {
        return new ResultWithMessages(errors);
    }

    public static ResultWithMessages error(Message errorMessage) {
        return new ResultWithMessages(errorMessage);
    }

    public static ResultWithMessages errorWithInfo(Message errorMessage, Message infoMessage) {
        return new ResultWithMessages(errorMessage, infoMessage);
    }

    public static ResultWithMessages error(MessageCollection messageCollection) {
        return new ResultWithMessages(messageCollection);
    }

    public static ResultWithMessages success() {
        return new ResultWithMessages();
    }

    public static ResultWithMessages success(Message infoMessage) {
        ResultWithMessages resultWithMessages = new ResultWithMessages();
        resultWithMessages.addInfo(infoMessage);

        return resultWithMessages;
    }

}