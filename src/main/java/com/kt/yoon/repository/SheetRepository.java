package com.kt.yoon.repository;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.MemberSheet;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.dto.MemberSheetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class SheetRepository {

    private final EntityManager entityManager;

    public void save(Sheet sheet){
        entityManager.persist(sheet);
    }

    public List<Sheet> getSheetByMember(Member member){
        return entityManager.createQuery("select s from Sheet s where s.createdMember=:member order by s.createdDate DESC ",Sheet.class)
                .setParameter("member",member)
                .getResultList();
    }

}
