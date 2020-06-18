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

    /***
     * 시트 저장
     * @param sheet
     */
    @Transactional
    public void save(Sheet sheet) {
        sheetRepository.save(sheet);
        List<MemberSheet> memberSheetList = sheet.getMemberSheetList();
        for (MemberSheet memberSheet : memberSheetList) {
            memberSheetRepository.save(memberSheet);
        }

    }

    /***
     * 유저 시트 목록 조회
     * @param userId
     * @param offset
     * @param limit
     * @param type
     * @return
     */
    public List<Sheet> getSheetByUserId(Long userId, int offset, int limit, String type) {
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

    /***
     * 멤버 시트 항목 조회
     * @param sheetId
     * @return
     */
    public List<MemberSheet> getSheetDetail(Long sheetId) {
        List<MemberSheet> sheetDetail = memberSheetRepository.getSheetDetail(sheetId);
        return sheetDetail;
    }

    /***
     * 시트 아이디로 검색
     * @param sheetId
     * @return
     */
    public Sheet getSheetById(Long sheetId) {
        return sheetRepository.getSheet(sheetId);
    }


    /***
     * 시트 시작
     * @param sheetId
     */
    public void startSheet(Long sheetId) {
        sheetRepository.startSheet(sheetId);
    }

    /***
     * 시트 종료
     * @param sheetId
     */
    public void endSheet(Long sheetId) {
        sheetRepository.endSheet(sheetId);
    }
}
