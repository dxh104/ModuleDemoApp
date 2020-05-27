package com.administer.example.module_user.entity;

/**
 * Created by XHD on 2020/05/26
 */
public class LoginResult {
    private int code;
    private String message;

    public LoginResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public LoginResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LoginResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
