package com.kt.yoon.repository;

import com.kt.yoon.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberRepository {

    private final EntityManager entityManager;

    public void save(Member member) {
        log.info("유저 정보 저장 시도");
        entityManager.persist(member);
        log.info("유저 정보 저장 완료");
    }

    public Member findById(Long id) {
        return entityManager.find(Member.class, id);
    }

    public List<Member> findByEmail(String email) {
        log.info("이메일 검색 쿼리 실행");
        return entityManager.createQuery("select m from  Member m where m.email=:email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }


    public List<Member> findMembers() {
        log.info("전체 멤버 조회 쿼리 실행");
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }


}
