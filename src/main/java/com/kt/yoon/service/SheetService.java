package com.kt.yoon.service;

import com.kt.yoon.domain.MemberSheet;
import com.kt.yoon.domain.Sheet;
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


    @Transactional
    public void save(Sheet sheet) {
        sheetRepository.save(sheet);

        List<MemberSheet> memberSheetList = sheet.getMemberSheetList();
        for (MemberSheet memberSheet : memberSheetList) {
            memberSheetRepository.save(memberSheet);
        }

    }

}
