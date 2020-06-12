package com.kt.yoon.exception;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class CommonException {
    public static boolean bindResultException(BindingResult bindingResult, JSONObject jsonObject) {
        if (bindingResult.hasErrors()) {
            JSONArray jsonArray = new JSONArray();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                jsonArray.add(fieldError.getDefaultMessage());
            }
            jsonObject.put("status", 400);
            jsonObject.put("message", jsonArray);
            return true;
        }
        return false;
    }
}
