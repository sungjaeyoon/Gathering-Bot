package com.kt.yoon.exception;

import org.json.simple.JSONObject;

public class JsonErrorResponse{
    private JSONObject jsonObject = new JSONObject();
    public JsonErrorResponse(int status, String message) {
        this.jsonObject.put("status",status);
        this.jsonObject.put("message",message);
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
