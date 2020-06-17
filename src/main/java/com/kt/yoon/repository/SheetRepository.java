package com.kt.yoon.repository;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.type.SheetStatus;
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

    public void save(Sheet sheet) {
        entityManager.persist(sheet);
    }

    public List<Sheet> getSheetByMember(Member member, int offset, int limit, SheetStatus sheetStatus) {
        if (sheetStatus == null) {
            String query = "select s from Sheet s where s.createdMember=:member order by s.createdDate DESC";
            return entityManager.createQuery(query, Sheet.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .setParameter("member", member)
                    .getResultList();
        } else {
            String query = "select s from Sheet s where (s.createdMember=:member and s.sheetStatus=:type) order by s.createdDate DESC";
            return entityManager.createQuery(query, Sheet.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .setParameter("member", member)
                    .setParameter("type", sheetStatus)
                    .getResultList();
        }
    }

    public void startSheet(Long sheetId) {
        entityManager.createQuery("update Sheet s set s.sheetStatus='PROCEEDING' where s.id=:sheetId")
                .setParameter("sheetId",sheetId)
                .executeUpdate();
    }
    public void endSheet(Long sheetId) {
        entityManager.createQuery("update Sheet s set s.sheetStatus='FINISHED' where s.id=:sheetId")
                .setParameter("sheetId",sheetId)
                .executeUpdate();
    }

}
