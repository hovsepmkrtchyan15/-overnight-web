package com.example.overnightweb.controller.admin;


import com.example.common.entity.User;
import com.example.overnightweb.security.CurrentUser;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice

public class MyControllerAdvice {
    @ModelAttribute(name = "currentUser")
    public User currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            return currentUser.getUser();
        }
        return null;
    }

    @ModelAttribute(name = "currentUrl")
    public String currentUrl(HttpServletRequest request) {
        return request.getRequestURI();

    }
}
