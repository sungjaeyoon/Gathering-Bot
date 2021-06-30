package com.kt.yoon.repository;

import com.kt.yoon.domain.Response;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.form.ResponseSheetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    List<Response> findAllByEmailAndSheetId(String email, Long sheetId);

    List<Response> findAllBySheet(Sheet sheet);

    void deleteAllBySheet(Sheet sheet);

    @Query(value = "SELECT new com.kt.yoon.domain.form.ResponseSheetDto(r.id,r.token,s.token,r.requestStatus,r.responseDate,s.id,s.title,s.sheetStatus, s.shareType) FROM Response r LEFT OUTER JOIN Sheet s ON r.sheet = s WHERE r.email=:email and not s.sheetStatus='WAIT'")
    Page<ResponseSheetDto> findAllByEmail(@Param("email") String email, Pageable pageable);
}


