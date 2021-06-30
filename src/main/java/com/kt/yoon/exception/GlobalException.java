package com.kt.yoon.exception;

import com.kt.yoon.domain.JsonErrorResponse;
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
import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalException {

    //바인딩 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonErrorResponse processValidationError(MethodArgumentNotValidException exception) {
        log.warn("EXCEPTION: Binding Result Exception");
        JSONArray jsonArray = new JSONArray();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            jsonArray.add(fieldError.getDefaultMessage());
        }
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "입력값이 잘못되었습니다.");
        jsonErrorResponse.put("message", jsonArray);
        return jsonErrorResponse;
    }

    //존재하지 않는 팀
    @ExceptionHandler(InvalidTeamNameException.class)
    public JsonErrorResponse invalidTeamNameException() {
        log.warn("EXCEPTION: not found team");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "존재하지 않는 팀이름입니다.");
        return jsonErrorResponse;
    }

    //해당 자료 권한 없음
    @ExceptionHandler(AccessDeniedException.class)
    public JsonErrorResponse resourceAccessDeniedError() {
        log.warn("EXCEPTION: HANDLE_ACCESS_DENIED Exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "해당 자료에 권한이 없습니다.");
        return jsonErrorResponse;
    }

    //해당 자원이 존재하지 않음
    @ExceptionHandler(EntityNotFoundException.class)
    public JsonErrorResponse entityNotFoundException() {
        log.warn("EXCEPTION: EntityNotFoundException");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "해당 자료가 없습니다.");
        return jsonErrorResponse;
    }

    //해당 자원이 존재하지 않음2
    @ExceptionHandler(NoResultException.class)
    public JsonErrorResponse noResultException() {
        log.warn("EXCEPTION: no Result Exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "해당 자료가 없습니다.");
        return jsonErrorResponse;
    }

    //읽을수 없는 자원
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonErrorResponse NotReadableError() {
        log.warn("EXCEPTION: NotReadable Error Exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(405, "읽을 수 없습니다.");
        return jsonErrorResponse;
    }

    //존재하지 않는 ID
    @ExceptionHandler(InvalidIdException.class)
    public JsonErrorResponse invalidIdError() {
        log.warn("EXCEPTION: no id exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "존재하지 않는 ID입니다.");
        return jsonErrorResponse;
    }

    //중복된 이메일
    @ExceptionHandler(DuplicateKeyException.class)
    public JsonErrorResponse emailDuplicateError() {
        log.warn("EXCEPTION: Duplicate email Exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "중복된 이메일입니다.");
        return jsonErrorResponse;
    }

    //가입되지 않은 이메일
    @ExceptionHandler(InvalidEmailException.class)
    public JsonErrorResponse emailInputInvalidError() {
        log.warn("EXCEPTION: InvalidEmailException Exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "가입되지 않은 이메일 입니다.");
        return jsonErrorResponse;
    }

    //패스워드 다름
    @ExceptionHandler(InvalidPasswordException.class)
    public JsonErrorResponse passwordInputInvalidError() {
        log.warn("EXCEPTION: InvalidPassword Exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "패스워드가 다릅니다.");
        return jsonErrorResponse;
    }

    //잠긴 계정으로 로그인 시도
    @ExceptionHandler(BlockedAccountException.class)
    public JsonErrorResponse blockedAccountError() {
        log.warn("EXCEPTION: Blocked Account Exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(403, "비밀번호 오류로 잠긴 계정입니다.");
        return jsonErrorResponse;
    }

    //비밀번호 오류로 계정이 잠김
    @ExceptionHandler(BlockAccountException.class)
    public JsonErrorResponse blockAccountError() {
        log.warn("EXCEPTION: Block Account Exception");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(403, "비밀번호 오류로 계정이 잠깁니다,");
        return jsonErrorResponse;
    }

    //이미 종료된 시트
    @ExceptionHandler(AlreadyExitSheet.class)
    public JsonErrorResponse alreadyExitSheet(Exception e) {
        log.warn("EXCEPTION: already exit sheet");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(405, "이미 종료된 시트입니다.");
        return jsonErrorResponse;
    }

    @ExceptionHandler(DoNotSendEmailException.class)
    public JsonErrorResponse doNoteSendEmail(Exception e) {
        log.warn("EXCEPTION: 메일 재발송 불가");
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "10분내에 재발송이 불가능합니다.");
        return jsonErrorResponse;
    }

    //todo Nullpointer Exception ( 아마 세션 문제인듯)
    @ExceptionHandler(NullPointerException.class)
    public JsonErrorResponse nullException(Exception e) {
        log.warn("Nullpointer:" + e.getMessage());
        e.printStackTrace();
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(500, "세션이 만료되었습니다. 다시 로그인 해주세요.");
        return jsonErrorResponse;
    }

    //id 값이 Long이 아닐때
    @ExceptionHandler(NumberFormatException.class)
    public JsonErrorResponse numberFormatException(Exception e) {
        log.warn("Numberformat exception:" + e.getMessage());
        e.printStackTrace();
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(400, "타입이 잘못되었습니다.");
        return jsonErrorResponse;
    }

    //알수 없는 에러는 여기서 관리함.
    @ExceptionHandler(Exception.class)
    public JsonErrorResponse allException(Exception e) {
        log.warn("EXCEPTION: exception");
        e.printStackTrace();
        JsonErrorResponse jsonErrorResponse = new JsonErrorResponse(500, "알수없는 에러입니다. 관리자에게 문의 바랍니다.");
        return jsonErrorResponse;
    }

}
