package com.esi.project.buildit.planthire.common.rest;

public interface Result {

    boolean wasSuccessful();

    default boolean failed(){
        return !wasSuccessful();
    }
}
