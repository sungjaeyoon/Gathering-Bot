package com.kt.yoon.domain;

import org.json.simple.JSONObject;

public class JsonErrorResponse extends JSONObject {
    private JSONObject jsonObject = new JSONObject();

    public JsonErrorResponse(int status, String message) {
        super();
        this.put("status", status);
        this.put("message", message);
    }
}
