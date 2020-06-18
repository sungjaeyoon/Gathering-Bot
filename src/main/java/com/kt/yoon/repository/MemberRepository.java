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

    /***
     * 회원가입
     * @param member
     */
    public void save(Member member) {
        entityManager.persist(member);
    }


    /***
     * id로 검색
     * @param id
     * @return
     */
    public Member findById(Long id) {
        return entityManager.find(Member.class, id);
    }

    /***
     * 이메일로 검색
     * @param email
     * @return
     */
    public List<Member> findByEmail(String email) {
        return entityManager.createQuery("select m from  Member m where m.email=:email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    /***
     * 멤버 목록 조회
     * @return
     */
    public List<Member> findMembers() {
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }


}
