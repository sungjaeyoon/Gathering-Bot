package com.kt.yoon.service;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.MemberSheet;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.type.SheetStatus;
import com.kt.yoon.repository.MemberRepository;
import com.kt.yoon.repository.MemberSheetRepository;
import com.kt.yoon.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SheetService {

    private final SheetRepository sheetRepository;
    private final MemberSheetRepository memberSheetRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(Sheet sheet) {
        sheetRepository.save(sheet);

        List<MemberSheet> memberSheetList = sheet.getMemberSheetList();
        for (MemberSheet memberSheet : memberSheetList) {
            memberSheetRepository.save(memberSheet);
        }

    }

    public List<Sheet> getSheetByUserId(Long userId, int offset, int limit, String type) throws Exception {
        SheetStatus sheetStatus = null;
        if (type.equals("WAIT")) {
            sheetStatus = SheetStatus.WAIT;
        } else if (type.equals("PROCEEDING")) {
            sheetStatus = SheetStatus.PROCEEDING;
        } else if (type.equals("FINISHED")) {
            sheetStatus = SheetStatus.FINISHED;
        }

        Member member = memberRepository.findById(userId);
        return sheetRepository.getSheetByMember(member, offset, limit, sheetStatus);
    }

    public List<MemberSheet> getSheetDetail(Long sheetId) {
        List<MemberSheet> sheetDetail = memberSheetRepository.getSheetDetail(sheetId);
        return sheetDetail;
    }

    public Sheet getSheetById(Long sheetId) {
        return memberSheetRepository.getSheet(sheetId);
    }

    public void startSheet(Long sheetId) throws Exception {
        sheetRepository.startSheet(sheetId);
    }

    public void endSheet(Long sheetId) throws Exception {
        sheetRepository.endSheet(sheetId);
    }
}
