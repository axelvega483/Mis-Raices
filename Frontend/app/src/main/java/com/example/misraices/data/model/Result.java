package com.example.misraices.data.model;

public class Result<T> {
    private T data;
    private String error;

    public Result(T data) {
        this.data = data;
    }

    public Result(String errorMessage) {
        this.error = errorMessage;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
