package com.kt.yoon.service;

import com.kt.yoon.repository.MemberSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSheetService {

    private final MemberSheetRepository memberSheetRepository;

    /***
     * 응답 결과 저장
     * @param sheetId
     * @param userId
     * @param response
     */
    public void updateMemberSheet(Long sheetId, Long userId, String response) {
        memberSheetRepository.updateMemberSheet(sheetId, userId, response);
    }

}
