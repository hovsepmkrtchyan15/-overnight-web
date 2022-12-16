package com.example.overnightweb.controller;


import com.example.common.entity.User;
import com.example.overnightweb.exception.DuplicateResourceException;
import com.example.overnightweb.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.mail.MessagingException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/verify")
    public String verifyUser(@RequestParam("email") String email,
                             @RequestParam("token") String token) throws Exception {
        userService.verifyUser(email, token);
        return "signin";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "signin";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, ModelMap modelMap) throws DuplicateResourceException, MessagingException {
        Optional<User> byEmail = userService.getUserByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            modelMap.addAttribute("errorMassage", "Email already in use");
            return "signin";
        }
        userService.save(user);
        return "/signin";

    }
}

