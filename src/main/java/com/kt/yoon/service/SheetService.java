package com.kt.yoon.service;

import com.kt.yoon.domain.Sheet;
import com.kt.yoon.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SheetService {

    private final SheetRepository sheetRepository;

    @Transactional
    public void save(Sheet sheet) {
        sheetRepository.save(sheet);
    }

}
