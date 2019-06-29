package com.esi.project.buildit.planthire.common.rest;

import com.esi.project.buildit.planthire.common.message.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Response<T> implements Result {

    private T data;

    private Result result = new Result();

    public Response(T data) {
        this.data = data;
    }

    public Response() {
    }

    public void setData(T data) {
        this.data = data;
    }


    public void setResult(Result result) {
        this.result = result;
    }

    public Response(Message error) {
        addError(error);
    }

    public Response(Collection<Message> errorMessages) {
        result.addErrors(errorMessages);
    }

    public Response(MessageCollection messageCollection) {
        result.addErrors(messageCollection.getErrors());
        result.addInfos(messageCollection.getInfos());
    }

    protected Response(T data, MessageCollection errorCollection) {
        this(errorCollection);
        this.data = data;
    }

    protected Response(T data, InfoCollection errorCollection) {
        result.addInfos(errorCollection.getInfos());
        this.data = data;
    }

    public void addError(Message error) {
        result.addError(error);
    }

    public void addInfo(Message info) {
        result.addInfo(info);
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T getData() {
        return data;
    }

    @Override
    public boolean wasSuccessful() {
        return result.wasSuccessful();
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Result getResult() {
        // We return null so that it won't be present in json
        return result.isEmpty() ? null : result;
    }

    public boolean hasError(Message message) {
        return result.hasError(message);
    }

    public class Result {

        private Set<Message> errors = new LinkedHashSet<>();
        private Set<Message> infos = new LinkedHashSet<>();

        @JsonProperty
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public Collection<Message> getErrors() {
            return errors;
        }

        @JsonProperty
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public Collection<Message> getInfos() {
            return infos;
        }

        public void addInfos(Collection<Message> infoMessages) {
            this.infos.addAll(infoMessages);
        }

        public void addInfo(Message info) {
            infos.add(info);
        }

        public void addErrors(Collection<Message> errorMessages) {
            this.errors.addAll(errorMessages);
        }

        public void addError(Message error) {
            errors.add(error);
        }

        public boolean wasSuccessful() {
            return errors == null || errors.isEmpty();
        }

        public boolean hasError(Message message) {
            return errors.contains(message);
        }

        private boolean isEmpty() {
            return errors.isEmpty() && infos.isEmpty();
        }
    }
}
