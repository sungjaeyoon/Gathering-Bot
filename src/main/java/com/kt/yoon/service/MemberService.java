package com.kt.yoon.service;

import com.kt.yoon.domain.Member;
import com.kt.yoon.exception.InvalidEmailException;
import com.kt.yoon.exception.InvalidPasswordException;
import com.kt.yoon.repository.MemberRepository;
import com.kt.yoon.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     * */
    public Member login(Map<String, String> user) throws Exception {
        Member member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new InvalidEmailException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new InvalidPasswordException("잘못된 비밀번호입니다.");
        }
        return member;
    }

    /**
     * 회원 가입
     * */
    @Transactional
    public void save(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
    }

    /**
     * 가입시 중복 유저 확인
     * */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new DuplicateKeyException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 멤버 조회
     * */
    public List<Member> findMembers() {
        return memberRepository.findMembers();
    }

    /**
     * id 값으로 조회
     * */
    public Member findById(Long id) {
        Member member = memberRepository.findById(id);
        if(member==null){
            throw new EntityNotFoundException("유저가 존재하지 않습니다.");
        }
        return member;
    }

    /**
     * 이메일로 조회
     * */
    public Member findMemberByEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.isEmpty()) {
            throw new EntityNotFoundException("유저가 존재하지 않습니다.");
        }
        return memberRepository.findByEmail(email).get(0);
    }

}
