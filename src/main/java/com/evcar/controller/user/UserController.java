package com.evcar.controller.user;

import com.evcar.dto.user.UserSignupDto;
import com.evcar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class UserController {

    private final UserService userService;

    @GetMapping("/term")
    public String termsPage() {
        return "user/agreement";
    }

    @GetMapping
    public String signupPage(@RequestParam(name = "agreePrivacy", required = false) Boolean agreePrivacy,
                             @RequestParam(name = "agreeTerms", required = false) Boolean agreeTerms,
                             Model model) {
        model.addAttribute("userSignupDto", new UserSignupDto());
        return "user/signup";
    }

    @PostMapping
    public String signup(@ModelAttribute UserSignupDto dto, Model model) {

        System.out.println("===== DTO 전체 =====");
        System.out.println(dto);
        System.out.println("loginId = " + dto.getLoginId());
        System.out.println("password = " + dto.getPassword());
        System.out.println("name = " + dto.getName());
        System.out.println("email = " + dto.getEmail());
        System.out.println("phone = " + dto.getPhone());

        try {
            userService.signup(dto);
            System.out.println("회원가입 완료");
            model.addAttribute("signupUser", dto);
            return "user/signupcomplete";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userSignupDto", dto);
            return "user/signup";
        }
    }

    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkDuplicate(@RequestParam("loginId") String loginId) {
        return userService.isUserLoginIdDuplicate(loginId);
    }

    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmailDuplicate(@RequestParam("email") String email) {
        return userService.isUserEmailDuplicate(email);
    }
}
