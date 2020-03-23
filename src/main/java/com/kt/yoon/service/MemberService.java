package com.kt.yoon.service;

import com.kt.yoon.domain.Member;
import com.kt.yoon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(Member member){
        memberRepository.save(member);
    }
}
