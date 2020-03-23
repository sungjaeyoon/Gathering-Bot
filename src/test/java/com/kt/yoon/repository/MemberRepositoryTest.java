package com.kt.yoon.repository;

import com.kt.yoon.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
class MemberRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void memberTest(){
        Member member1 = new Member();
        member1.setMemberName("A");
        Member member2 = new Member();
        member2.setMemberName("B");

        entityManager.persist(member1);
        entityManager.persist(member2);
    }
}