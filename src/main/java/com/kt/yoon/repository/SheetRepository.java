package com.kt.yoon.repository;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.Sheet;
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
        return entityManager.createQuery("select s from Sheet s where s.createdMember=:member",Sheet.class)
                .setParameter("member",member)
                .getResultList();
    }

//    public

//    # select * from sheet where sheet_id=3;
//    select
//    ms.member_sheet_id,
//    ms.modified_date,
//    ms.request_status,
//    ms.response,
//    ms.response_date,
//    m.member_id,
//    ms.sheet_id,
//    m.email,
//    m.name,
//    m.position,
//    m.team_name
//            from
//    member_sheet as ms left join member as m
//    on ms.member_id = m.member_id
//    where sheet_id=3;

}
