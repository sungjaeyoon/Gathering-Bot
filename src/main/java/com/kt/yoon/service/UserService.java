package com.kt.yoon.service;

import com.kt.yoon.domain.Team;
import com.kt.yoon.domain.User;
import com.kt.yoon.domain.form.LoginForm;
import com.kt.yoon.domain.form.PasswordUserForm;
import com.kt.yoon.domain.form.UserForm;
import com.kt.yoon.domain.form.UserUpdateForm;
import com.kt.yoon.exception.*;
import com.kt.yoon.repository.TeamRepository;
import com.kt.yoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    /***
     * Create & 중복검사
     */
    @Transactional
    public void signUpUser(UserForm userForm) throws Exception {
        validateDuplicateMember(userForm.getEmail());

        Team team = teamRepository.findByTeamName(userForm.getTeamName())
                .orElseThrow(() -> new InvalidTeamNameException());
        User user = User.createUser(userForm, team);

        userRepository.save(user);
    }

    public void validateDuplicateMember(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateKeyException("이미 존재하는 회원입니다.");
        }
    }

    /***
     * 로그인
     */
    @Transactional
    public User login(LoginForm loginForm) throws Exception {
        User user = userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new InvalidEmailException("가입되지 않은 E-MAIL 입니다."));

        checkUserBlocked(user);
        checkUserPasswordValid(loginForm.getPassword(), user);
        initUserFailCount(user);
        return user;
    }

    public void checkUserBlocked(User user) throws BlockedAccountException {
        if (user.isUserBlocked()) {
            throw new BlockedAccountException("5회 이상 틀려 잠긴 계정입니다.");
        }
    }

    public void checkUserPasswordValid(String password, User user) throws InvalidPasswordException, BlockAccountException {
        if (!user.matchPassword(password)) {
            checkUserFailCountOver(user);
            addUserFailCount(user);
            throw new InvalidPasswordException("잘못된 비밀번호입니다.");
        }
    }

    public void checkUserFailCountOver(User user) throws BlockAccountException {
        if (user.isFailCountOver()) {
            user.blockUser();
            userRepository.save(user);
            throw new BlockAccountException("비밀번호가 5회 이상 틀려 계정이 잠깁니다.");
        }
    }

    public void addUserFailCount(User user) {
        user.addFailCount();
        userRepository.save(user);
    }

    public void initUserFailCount(User user) {
        user.initFailCount();
        userRepository.save(user);
    }

    /***
     * Read
     */
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 입니다."));
    }

    /***
     * Update
     */
    @Transactional
    public void changeUserPassword(PasswordUserForm passwordUserForm) throws Exception {
        User user = userRepository.findByEmail(passwordUserForm.getEmail())
                .orElseThrow(() -> new InvalidEmailException("가입되지 않은 E-MAIL 입니다."));

        checkUserBlocked(user);
        checkUserPasswordValid(passwordUserForm.getCurrentPassword(), user);

        user.updatePassword(passwordUserForm.getNewPassword());
        userRepository.save(user);
    }

    @Transactional
    public void changeUserInfo(UserUpdateForm userUpdateForm) throws Exception{

        User user = userRepository.findByEmail(userUpdateForm.getEmail())
                .orElseThrow(() -> new InvalidEmailException("가입되지 않은 E-MAIL 입니다."));

        if(!userUpdateForm.getTeamName().equals("NULL")){

            Team team = teamRepository.findByTeamName(userUpdateForm.getTeamName()).orElseThrow(() -> new InvalidTeamNameException());
            user.updateUser(userUpdateForm, team);
        }else{
            user.updateUser(userUpdateForm,null);
        }
        userRepository.save(user);
    }
}
