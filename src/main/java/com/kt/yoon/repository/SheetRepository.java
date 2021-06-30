package com.kt.yoon.repository;

import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.type.SheetStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetRepository extends JpaRepository<Sheet, Long> {

    Page<Sheet> findAllByEmailAndTitleLike(String email, Pageable pageable, String word);

    Page<Sheet> findAllByEmailAndSheetStatusAndTitleLike(String email, SheetStatus sheetStatus, Pageable pageable, String word);

}
