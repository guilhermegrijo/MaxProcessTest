package com.example.maxprocesstest.model;


/* Essa classe especifica os estados da tela */

import java.util.List;

public class Response<T> {

    public T data;

    public Status status;

    public Throwable error;

    private Response(Status status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response error(Throwable error) {

        return new Response(Status.ERROR, null, error);
    }

    public static Response success(List<Contact> data) {

        return new Response(Status.SUCCESS, data, null);
    }

    public static Response success(ContactPhones data) {

        return new Response(Status.SUCCESS, data, null);
    }

    public static Response empty() {
        return new Response(Status.EMPTY, null, null);
    }

    public static Response emptyQuery() {
        return new Response(Status.EMPTYQUERY, null, null);
    }

    public static Response removed() {
        return new Response(Status.REMOVED, null, null);
    }

    public static Response updated() {
        return new Response(Status.UPDATED, null, null);
    }


    public static Response completed() {
        return new Response(Status.COMPLETED, null, null);
    }
}