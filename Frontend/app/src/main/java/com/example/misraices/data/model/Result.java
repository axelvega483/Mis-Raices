package com.example.misraices.data.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private T data;
    private String error;

    public Result(T data) {
        this.data = data;
    }

    public Result(String errorMessage) {
        this.error = errorMessage;
    }
}
