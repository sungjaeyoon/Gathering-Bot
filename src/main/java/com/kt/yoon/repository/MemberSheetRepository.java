package com.kt.yoon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberSheetRepository {

    private final EntityManager entityManager;

    
}
