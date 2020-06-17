package com.kt.yoon.repository;

import com.kt.yoon.domain.MemberSheet;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.type.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public Sheet getSheet(Long sheetId) throws NoResultException {
        return entityManager.createQuery("select s from Sheet s where s.id=:sheetId",Sheet.class)
                .setParameter("sheetId",sheetId)
                .getSingleResult();
    }

    public void updateMemberSheet(Long sheetId, Long userId, String response) {
        entityManager.createQuery("update MemberSheet ms set ms.response=:response, ms.responseDate=:responseDate,ms.requestStatus=:requestStatus where ms.member.id=:userId and ms.sheet.id=:sheetId")
                .setParameter("response",response)
                .setParameter("userId",userId)
                .setParameter("sheetId",sheetId)
                .setParameter("responseDate", LocalDateTime.now())
                .setParameter("requestStatus", RequestStatus.YES)
                .executeUpdate();
    }
}
