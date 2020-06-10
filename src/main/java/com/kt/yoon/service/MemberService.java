package com.kt.yoon.service;

import com.kt.yoon.domain.Member;
import com.kt.yoon.repository.MemberRepository;
import com.kt.yoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Member login(Map<String, String> user) throws IllegalArgumentException{

        Member member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return member;
    }

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
