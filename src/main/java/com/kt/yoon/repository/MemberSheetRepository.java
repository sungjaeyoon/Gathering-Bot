package com.kt.yoon.repository;

import com.kt.yoon.domain.MemberSheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberSheetRepository {

    private final EntityManager entityManager;

    public void save(MemberSheet memberSheet){
        entityManager.persist(memberSheet);
    }
}
