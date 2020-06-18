package com.kt.yoon.repository;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.type.SheetStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class SheetRepository {

    private final EntityManager entityManager;

    /***
     * 시트 저장
     * @param sheet
     */
    public void save(Sheet sheet) {
        entityManager.persist(sheet);
    }

    /***
     * 하나의 항목 조회
     * @param sheetId
     * @return
     * @throws NoResultException
     */
    public Sheet getSheet(Long sheetId) throws NoResultException {
        return entityManager.createQuery("select s from Sheet s where s.id=:sheetId",Sheet.class)
                .setParameter("sheetId",sheetId)
                .getSingleResult();
    }

    /***
     * 유저의 시트 목록 조회
     * @param member
     * @param offset
     * @param limit
     * @param sheetStatus
     * @return
     */
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

    /***
     * 시트 시작
     * @param sheetId
     */
    public void startSheet(Long sheetId) {
        entityManager.createQuery("update Sheet s set s.sheetStatus='PROCEEDING' where s.id=:sheetId")
                .setParameter("sheetId",sheetId)
                .executeUpdate();
    }

    /***
     * 시트 종료
     * @param sheetId
     */
    public void endSheet(Long sheetId) {
        entityManager.createQuery("update Sheet s set s.sheetStatus='FINISHED' where s.id=:sheetId")
                .setParameter("sheetId",sheetId)
                .executeUpdate();
    }

}
