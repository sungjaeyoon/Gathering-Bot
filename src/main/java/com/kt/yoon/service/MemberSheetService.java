package com.kt.yoon.service;

import com.kt.yoon.repository.MemberSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSheetService {

    private final MemberSheetRepository memberSheetRepository;

    public void updateMemberSheet(Long sheetId, Long userId, String response) throws Exception{
        memberSheetRepository.updateMemberSheet(sheetId, userId, response);
    }

}
