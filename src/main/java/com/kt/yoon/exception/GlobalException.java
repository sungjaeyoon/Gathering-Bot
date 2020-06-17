package com.kt.yoon.exception;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import static com.kt.yoon.exception.ErrorCode.*;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalException {

    //바인딩 에러 - INVALID_INPUT_VALUE(400, "C001", "Input 오류입니다."),
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonErrorResponse processValidationError(MethodArgumentNotValidException exception) {
        log.warn("EXCEPTION: Binding Result Exception");
        JSONArray jsonArray = new JSONArray();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            jsonArray.add(fieldError.getDefaultMessage());
        }
        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(INVALID_INPUT_VALUE.getStatus(),INVALID_INPUT_VALUE.getMessage());
        jsonErrorResponse.put("message",jsonArray);
        return jsonErrorResponse;
    }

//    //todo 로그인 필요(수정) HANDLE_ACCESS_DENIED(401, "C002", "로그인이 필요합니다."),
//    @ExceptionHandler()
//    public JsonErrorResponse handleAccessDeniedError() {
//        log.warn("EXCEPTION: HANDLE_ACCESS_DENIED Exception");
//        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(HANDLE_ACCESS_DENIED.getStatus(),HANDLE_ACCESS_DENIED.getMessage());
//        return jsonErrorResponse;
//    }
//
//    //todo 해당 자료 권한 없음(수정) RESOURCE_ACCESS_DENIED(403, "C003", "권한이 없습니다."),
//    @ExceptionHandler()
//    public JSONObject resourceAccessDeniedError() {
//        log.warn("EXCEPTION: HANDLE_ACCESS_DENIED Exception");
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("status", RESOURCE_ACCESS_DENIED.getStatus());
//        jsonObject.put("message", RESOURCE_ACCESS_DENIED.getMessage());
//        return jsonObject;
//    }

    //해당 자원이 존재하지 않음 - RESOURCE_NOT_FOUND(400, "C004", "해당 자료가 없습니다."),
    @ExceptionHandler(EntityNotFoundException.class)
    public JsonErrorResponse entitiyNotFoundException() {
        log.warn("EXCEPTION: EntityNotFoundException");
        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(RESOURCE_NOT_FOUND.getStatus(),RESOURCE_NOT_FOUND.getMessage());
        return jsonErrorResponse;
    }

    //해당 자원이 존재하지 않음2 - RESOURCE_NOT_FOUND(400, "C004", "해당 자료가 없습니다."),
    @ExceptionHandler(NoResultException.class)
    public JsonErrorResponse noResultException() {
        log.warn("EXCEPTION: no Result Exception");
        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(RESOURCE_NOT_FOUND.getStatus(),RESOURCE_NOT_FOUND.getMessage());
        return jsonErrorResponse;
    }

    //읽을수 없는 자원 - NOT_READABLE(405, "C005", "읽을 수 없습니다."),
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonErrorResponse NotReadableError() {
        log.warn("EXCEPTION: NotReadable Error Exception");
        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(NOT_READABLE.getStatus(),NOT_READABLE.getMessage());
        return jsonErrorResponse;
    }

    //중복된 이메일 - EMAIL_DUPLICATION(400, "M001", "중복된 이메일입니다."),
    @ExceptionHandler(DuplicateKeyException.class)
    public JsonErrorResponse emailDuplicateError() {
        log.warn("EXCEPTION: Duplicate email Exception");
        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(EMAIL_DUPLICATION.getStatus(),EMAIL_DUPLICATION.getMessage());
        return jsonErrorResponse;
    }

    //가입되지 않은 이메일 - EMAIL_INPUT_INVALID(400, "M002", "가입되지 않은 이메일 입니다."),
    @ExceptionHandler(InvalidEmailException.class)
    public JsonErrorResponse emailInputInvalidError() {
        log.warn("EXCEPTION: InvalidEmailException Exception");
        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(EMAIL_INPUT_INVALID.getStatus(),EMAIL_INPUT_INVALID.getMessage());
        return jsonErrorResponse;
    }

    //패스워드 다름 - PASSWORD_INPUT_INVALID(400, "M003", "패스워드가 다릅니다.");
    @ExceptionHandler(InvalidPasswordException.class)
    public JsonErrorResponse passwordInputInvalidError() {
        log.warn("EXCEPTION: InvalidPassword Exception");
        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(PASSWORD_INPUT_INVALID.getStatus(),PASSWORD_INPUT_INVALID.getMessage());
        return jsonErrorResponse;
    }

    //일단 알수 없는 에러는 여기서 관리함.
    @ExceptionHandler(Exception.class)
    public JsonErrorResponse allException(Exception e) {
        log.warn("EXCEPTION: 알수없는 Exception");
        JsonErrorResponse jsonErrorResponse= new JsonErrorResponse(500,e.getMessage());
        return jsonErrorResponse;
    }

}
