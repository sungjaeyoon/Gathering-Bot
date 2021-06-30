package com.kt.yoon.service;

import com.kt.yoon.domain.Response;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.User;
import com.kt.yoon.domain.form.ResponseUserForm;
import com.kt.yoon.domain.form.SheetForm;
import com.kt.yoon.domain.type.SheetStatus;
import com.kt.yoon.exception.InvalidIdException;
import com.kt.yoon.repository.ResponseRepository;
import com.kt.yoon.repository.SheetRepository;
import com.kt.yoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SheetService {

    private final UserRepository userRepository;
    private final SheetRepository sheetRepository;
    private final ResponseRepository responseRepository;

    /***
     * Create
     */
    @Transactional
    public void createSheet(SheetForm sheetForm) throws Exception {
        User user = userRepository.findById(Long.parseLong(sheetForm.getCreatedMemberId())).orElseThrow(() -> new InvalidIdException());
        Sheet sheet = Sheet.createSheet(sheetForm, user);

        List<ResponseUserForm> responseUserFormList = sheetForm.getUserList();
        List<Response> responseList = new ArrayList<>();
        for (ResponseUserForm responseUserForm : responseUserFormList) {
            Response response = Response.createResponse(responseUserForm, sheet, Sheet.generateRandomToken());
            responseList.add(response);
        }
        responseRepository.saveAll(responseList);
        sheetRepository.save(sheet);
    }

    /***
     * Read
     */
    public Page<Sheet> getAllSheetsByUser(User requestUser, int start, int display, String word, String type) {

        PageRequest pageRequest = PageRequest.of(start, display, Sort.by(Sort.Direction.DESC, "createdDate"));

        SheetStatus sheetStatus = null;
        if (type.equals("WAIT")) {
            sheetStatus = SheetStatus.WAIT;
        } else if (type.equals("PROCEEDING")) {
            sheetStatus = SheetStatus.PROCEEDING;
        } else if (type.equals("FINISHED")) {
            sheetStatus = SheetStatus.FINISHED;
        }

        if (sheetStatus == null) {
            return sheetRepository.findAllByEmailAndTitleLike(requestUser.getEmail(), pageRequest, "%" + word + "%");
        } else {
            return sheetRepository.findAllByEmailAndSheetStatusAndTitleLike(requestUser.getEmail(), sheetStatus, pageRequest, "%" + word + "%");
        }
    }

    public Sheet getSheetById(Long sheetId) {
        return sheetRepository.findById(sheetId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시트 입니다."));
    }


    /***
     * Update
     */
    @Transactional
    public void startSheet(Long sheetId) {
        Sheet sheet = sheetRepository.findById(sheetId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시트 입니다."));
        if (sheet.isSheetWait()) {
            sheet.startSheet();
            sheetRepository.save(sheet);
        }
    }

    @Transactional
    public void endSheet(Long sheetId) {
        Sheet sheet = sheetRepository.findById(sheetId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시트 입니다."));
        if (sheet.isSheetProceed()) {
            sheet.endSheet();
            sheetRepository.save(sheet);
        }
    }

    @Transactional
    public void setSheetLastSendMailDate(Sheet sheet) {
        sheet.setLastSendMailDate(LocalDateTime.now());
        List<Response> responseList = responseRepository.findAllBySheet(sheet);
        for (Response response : responseList) {
            response.setLastSendMailDate(LocalDateTime.now());
        }
        sheetRepository.save(sheet);
        responseRepository.saveAll(responseList);

    }

    /***
     * remove
     */
    @Transactional
    public void removeSheet(Long sheetId) {
        Sheet sheet = sheetRepository.findById(sheetId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시트 입니다."));
        responseRepository.deleteAllBySheet(sheet);
        sheetRepository.delete(sheet);
    }
}
