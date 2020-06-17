package com.kt.yoon.exception;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Slf4j
public class CommonException {
    public static boolean bindResultException(BindingResult bindingResult, JSONObject jsonObject) {
        if (bindingResult.hasErrors()) {
            JSONArray jsonArray = new JSONArray();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.warn("바인딩 에러:"+fieldError.getDefaultMessage());
                jsonArray.add(fieldError.getDefaultMessage());
            }
            jsonObject.put("status", 400);
            jsonObject.put("message", jsonArray);
            return true;
        }
        return false;
    }
}
