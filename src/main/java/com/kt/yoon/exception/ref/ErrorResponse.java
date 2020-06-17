package com.kt.yoon.exception.ref;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

//@Getter
public class ErrorResponse {

//    private String message;
//    private int status;
//    private List<String> errorMessages;
//    private String code;
//
//    public ErrorResponse(String message, int status, String code, BindingResult errors) {
//        this.message = message;
//        this.status = status;
//        this.code = code;
//        for (FieldError fieldError : errors.getFieldErrors()) {
//            this.errorMessages.add(fieldError.getDefaultMessage());
//        }
//    }
}

//{
//    "message": " Invalid Input Value",
//    "status": 400,
//    // "errors":[], 비어있을 경우 null 이 아닌 빈 배열을 응답한다.
//    "errors": [
//        {
//        "field": "name.last",
//        "value": "",
//        "reason": "must not be empty"
//        },
//        {
//        "field": "name.first",
//        "value": "",
//        "reason": "must not be empty"
//        }
//    ],
//    "code": "C001"
//}
