package com.example.overnightweb.controller.admin;


import com.example.common.entity.RoleUser;
import com.example.common.entity.StatusSeller;
import com.example.common.entity.User;
import com.example.common.entity.UserBook;
import com.example.overnightweb.exception.UserNotFoundException;
import com.example.overnightweb.security.CurrentUser;
import com.example.overnightweb.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    @Value("${overnight.images.folder}")
    private String folderPath;


    @GetMapping("/adminPage")
    public String adminPage(@PageableDefault(size = 5) Pageable pageable,
                            @RequestParam(value = "status", required = false) StatusSeller status,
                            ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {

        Page<User> users = adminService.findUsersByUserRole(RoleUser.SELLER,
                pageable, status);
        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        modelMap.addAttribute("users", users);
        log.info("Controller/adminPage called by {}", currentUser.getUsername());
        return "admin/adminPage";
    }

    @GetMapping("/user")
    public String orders(@PageableDefault(size = 5) Pageable pageable,
                         @RequestParam(value = "startDate", required = false) String startDate,
                         @RequestParam(value = "endDate", required = false) String endDate,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @AuthenticationPrincipal CurrentUser currentUser,
                         ModelMap modelMap) {

        Page<UserBook> orders = adminService.findUserBookAll(pageable, startDate, endDate, keyword);
        int totalPages = orders.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("orders", orders);
        log.info("Controller/user called by {}", currentUser.getUsername());
        return "admin/adminPageUser";
    }

    @GetMapping("/profileSettings")
    public String profSett(@AuthenticationPrincipal CurrentUser currentUser,
                           ModelMap modelMap) {
        Optional<User> userByEmail = adminService.getUserByEmail(currentUser.getUsername());
        if(userByEmail.isPresent()){
            modelMap.addAttribute("user", userByEmail.get());
            return "/admin/adminProfilePage";
        }else {
            throw new UserNotFoundException(currentUser.getUser().getId());
        }
    }

    @PostMapping("/password/change")

    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,

                                 @AuthenticationPrincipal CurrentUser currentUser,
                                 ModelMap modelMap) {
        Optional<User> userByEmail = adminService.getUserByEmail(currentUser.getUsername());
        if (userByEmail.isPresent()) {
            if (confirmPassword.equals(newPassword) && passwordEncoder.matches(oldPassword, userByEmail.get().getPassword())) {
                userByEmail.get().setPassword(passwordEncoder.encode(newPassword));
                adminService.save(userByEmail.get());
                log.info("Change password {}", currentUser.getUsername());
                return "redirect:/";
            }
        }
        modelMap.addAttribute("errorMessage", "DOES NOT UPDATE");
        log.info("Failed to change password {}", currentUser.getUsername());
        return "/admin/adminProfilePage";
    }

    @PostMapping("/admin/update")
    public String updateProfile(@Valid @ModelAttribute User user, BindingResult result,
                                @AuthenticationPrincipal CurrentUser currentUser,
                                ModelMap modelMap) {
        log.info("Controller /admin/update called by {}", currentUser.getUsername());
        if (result.hasErrors()) {
            modelMap.addAttribute("user", user);
            return "/admin/adminProfilePage";
        }
        adminService.update(user);
        log.info("Update admin profile by {}", currentUser.getUsername());
        return "redirect:/";
    }

    @PostMapping("/image/add")
    public String addImage(@AuthenticationPrincipal CurrentUser currentUser,
                           @RequestParam(value = "userImage", required = false) MultipartFile file) throws IOException {
        adminService.image(currentUser, file);
        return "redirect:/user";
    }

    @GetMapping(value = "/user/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/status/edit")
    public String editStatus(@RequestParam("id") int id,
                             @RequestParam(value = "statusUser", required = false) StatusSeller status) {
        adminService.edit(id, status);
        return "redirect:/adminPage";
    }

}