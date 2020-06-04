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


    /**
     * 멤버 가입 -> validate
     * */
    @Transactional
    public Long save(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 멤버 조회
     * */
    public List<Member> findMembers() {
        return memberRepository.findMembers();
    }

    /**
     * 이메일로 검색
     * */
    public Member findMemberByEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 이메일입니다.");
        }
        return memberRepository.findByEmail(email).get(0);
    }

    /**
     * 가입시 중복 유저 확인
     * */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
