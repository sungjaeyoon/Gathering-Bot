package com.kt.yoon.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.AccessDeniedException;

public class CheckAuth {

    public static void checkUserCanAccess(String email, Authentication authentication) throws Exception {
        try {
            String requestUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            if (!email.equals(requestUserEmail)) {
                throw new AccessDeniedException("권한이 없습니다.");
            }
        } catch (NullPointerException e) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }
}
