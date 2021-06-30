package com.kt.yoon.service;


import com.kt.yoon.domain.Response;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.User;
import com.kt.yoon.domain.form.ResponseData;
import com.kt.yoon.domain.form.ResponseForm;
import com.kt.yoon.domain.form.ResponseRemoveForm;
import com.kt.yoon.domain.form.ResponseSheetDto;
import com.kt.yoon.exception.InvalidIdException;
import com.kt.yoon.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository responseRepository;

    public List<Response> findResponse(User user, Sheet sheet) {
        return responseRepository.findAllByEmailAndSheetId(user.getEmail(), sheet.getId());
    }

    public Page<ResponseSheetDto> findUserResponse(int index, String email){
        PageRequest pageRequest = PageRequest.of(index, 10, Sort.by(Sort.Direction.DESC, "id"));
        return responseRepository.findAllByEmail(email, pageRequest);
    }

    public void saveResponse(ResponseForm responseForms, Sheet sheet) throws Exception {

        ResponseData[] responseDataList = responseForms.getResponseData();

        //첫 response 가져옴
        Response firstResponse = responseRepository.findById(Long.parseLong(responseForms.getFirstResponseId()))
                .orElseThrow(() -> new InvalidIdException());

        //token 체크
        if (!firstResponse.getToken().equals(responseForms.getToken())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        //있는 응답은 저장, 추가된 응답은 새로 생성
        for (ResponseData responseData : responseDataList) {
            Response response = null;

            if (responseData.getResponseId().equals("new")) {
                response = Response.addResponse(responseForms.getUserName(), responseForms.getPosition(), responseForms.getTeamName(), responseForms.getEmail(), "", responseForms.getToken(), sheet);
            } else {
                response = responseRepository.findById(Long.parseLong(responseData.getResponseId()))
                        .orElseThrow(() -> new InvalidIdException());
            }

            response.saveResponse(responseData.getResponse());
            responseRepository.save(response);
        }
    }

    public void removeResponse(ResponseRemoveForm[] responseRemoveForms) throws Exception {
        for (ResponseRemoveForm responseRemoveForm : responseRemoveForms) {
            Response response = responseRepository.findById(Long.parseLong(responseRemoveForm.getResponseId())).orElseThrow(() -> new InvalidIdException());
            if (!response.getToken().equals(responseRemoveForm.getToken())) {
                throw new AccessDeniedException("권한이 없습니다.");
            }
            responseRepository.delete(response);
        }
    }

    public List<Response> findAllResponseBySheet(Sheet sheet) {
        return responseRepository.findAllBySheet(sheet);
    }

    public List<Response> findById(String id) throws Exception {
        List<Response> responseList = new ArrayList<>();
        responseList.add(responseRepository.findById(Long.parseLong(id)).orElseThrow(() -> new InvalidIdException()));
        return responseList;
    }

    public Response findOne(String responseId) throws Exception {
        return responseRepository.findById(Long.parseLong(responseId)).orElseThrow(() -> new InvalidIdException());
    }


    public void setLastSendMailDate(Response response) {
        response.setLastSendMailDate(LocalDateTime.now());
        responseRepository.save(response);
    }
}
