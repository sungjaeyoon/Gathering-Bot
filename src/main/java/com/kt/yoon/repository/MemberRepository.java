package com.kt.yoon.repository;

import com.kt.yoon.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class MemberRepository {

   private final EntityManager entityManager;

    public void save(Member member){
        entityManager.persist(member);
    }

    public Member findOne(Long id){
        return entityManager.find(Member.class,id);
    }

    public List<Member> findMembers(){
        return entityManager.createQuery("select m from Member m",Member.class).getResultList();
    }


}
