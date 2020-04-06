package com.kt.yoon.service;

import com.kt.yoon.domain.Member;
import com.kt.yoon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(Member member){
        memberRepository.save(member);
    }

    public List<Member> findMembers(){
        return memberRepository.findMembers();
    }
}
