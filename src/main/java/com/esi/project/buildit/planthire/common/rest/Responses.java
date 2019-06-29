package com.esi.project.buildit.planthire.common.rest;




import com.esi.project.buildit.planthire.common.message.Message;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Responses {

    private Responses() {
    }

    public static <T> Response<T> error(Message... messages) {
        return new Response<>(Arrays.asList(messages));
    }

    public static <T> Response<T> error(Collection<Message> messages) {
        return new Response<>(messages);
    }

    public static <T> Response<T> error(MessageCollection messages) {
        return new Response<>(messages);
    }

    public static <T> Response<T> error(T data, MessageCollection messages) {
        return new Response<>(data, messages);
    }

    public static <T> Response<T> success(T data, InfoCollection infoCollection) {
        return new Response<>(data, infoCollection);
    }

    public static <T> Response<T> success(T data, Message infoMessage) {
        Response<T> response = new Response<>(data);
        response.addInfo(infoMessage);
        return response;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }


    public static <T> Response<T> success() {
        return new Response<>((T) new HashMap<>());
    }
}
