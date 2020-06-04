package com.kt.yoon.repository;

import com.kt.yoon.domain.Sheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class SheetRepository {

    private final EntityManager entityManager;

    public void save(Sheet sheet){
        entityManager.persist(sheet);
    }
}
