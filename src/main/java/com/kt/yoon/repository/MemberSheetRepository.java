package com.kt.yoon.repository;

import com.kt.yoon.domain.MemberSheet;
import com.kt.yoon.domain.dto.MemberSheetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberSheetRepository {

    private final EntityManager entityManager;

    public void save(MemberSheet memberSheet){
        entityManager.persist(memberSheet);
    }

    public List<MemberSheet> getSheetDetail(Long sheetId){
        return entityManager.createQuery("select ms from MemberSheet ms where ms.sheet.id=:sheetId ",MemberSheet.class)
                .setParameter("sheetId",sheetId)
                .getResultList();
    }
}
