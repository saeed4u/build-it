package com.esi.project.buildit.planthire.common.rest;




import com.esi.project.buildit.planthire.common.message.Message;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;


public class ResultWithMessages implements Result, MessageCollection {

    private Set<Message> errors = new LinkedHashSet<>();
    private Set<Message> infos = new LinkedHashSet<>();

    public ResultWithMessages() {
    }

    public ResultWithMessages(Message errorMessage) {
        addError(errorMessage);
    }

    protected ResultWithMessages(Message errorMessage, Message infoMessage) {
        addError(errorMessage);
        addInfo(infoMessage);
    }

    public ResultWithMessages(Collection<Message> errors) {
        this.errors = new LinkedHashSet<>(errors);
    }

    protected ResultWithMessages(MessageCollection messageCollection) {
        errors.addAll(messageCollection.getErrors());
        infos.addAll(messageCollection.getInfos());
    }

    public void addError(Message error) {
        errors.add(error);
    }

    public void addInfo(Message info) {
        infos.add(info);
    }

    @Override
    public Collection<Message> getErrors() {
        return new LinkedHashSet<>(errors);
    }

    public boolean hasError(Message message) {
        return errors.contains(message);
    }

    @Override
    public Collection<Message> getInfos() {
        return new LinkedHashSet<>(infos);
    }

    @Override
    public boolean wasSuccessful() {
        return errors == null || errors.isEmpty();
    }

}