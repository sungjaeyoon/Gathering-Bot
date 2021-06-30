package com.kt.yoon.trash;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSheetService {
//
//    private final MemberSheetRepository memberSheetRepository;
//
//    /***
//     * Read
//     */
//    public MemberSheet findMemberSheet(Long memberId, Long sheetId) {
//        return memberSheetRepository.findMemberSheetByMemberIdAndSheetId(memberId, sheetId).orElseThrow(() -> new EntityNotFoundException(".존재하지 않는 답변 입니다."));
//    }
//
//    public MemberSheet findMemberSheetById(Long membersheetId){
//        return memberSheetRepository.findById(membersheetId).orElseThrow(() -> new EntityNotFoundException(".존재하지 않는 답변 입니다."));
//    }
//
//    /***
//     * Update
//     */
//    @Transactional
//    public void updateMemberSheet(Long sheetId, Long memberId, String response) {
//        MemberSheet memberSheet = memberSheetRepository.findMemberSheetByMemberIdAndSheetId(memberId, sheetId).orElseThrow(() -> new EntityNotFoundException(".존재하지 않는 답변 입니다."));
//        memberSheet.saveResponse(response);
//        memberSheetRepository.save(memberSheet);
//    }

}
