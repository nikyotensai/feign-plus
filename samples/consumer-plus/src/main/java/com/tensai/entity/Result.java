package com.tensai.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Result<T> {

    private String code;
    private String message;
    private T data;


    Result(String code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public static <T> Result<T> create(String code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> create(String code, String message, T data) {
        return new Result<>(code, message, data);
    }

}
