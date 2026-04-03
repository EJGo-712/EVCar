package com.evcar.controller.login;

import com.evcar.domain.user.User;
import com.evcar.dto.login.IdRecoveryDto;
import com.evcar.dto.login.LoginRequestDto;
import com.evcar.dto.login.PasswordResetDto;
import com.evcar.service.login.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "login/login";
    }

    @PostMapping
    public String login(@ModelAttribute LoginRequestDto dto,
                        HttpSession session,
                        Model model) {
        try {
            User loginUser = loginService.login(dto);
            session.setAttribute("loginUser", loginUser);
            return "redirect:/main";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("loginRequestDto", dto);
            return "login/login";
        }
    }

    @GetMapping("/find-id")
    public String idRecoveryPage(Model model) {
        model.addAttribute("idRecoveryDto", new IdRecoveryDto());
        return "login/idrecovery";
    }

    @PostMapping("/find-id")
    public String findUserLoginId(@ModelAttribute IdRecoveryDto dto, Model model) {
        try {
            String foundId = loginService.findUserLoginId(dto);
            model.addAttribute("foundId", foundId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "login/idrecovery";
    }

    @GetMapping("/reset-pw")
    public String pwResetPage(Model model) {
        model.addAttribute("passwordResetDto", new PasswordResetDto());
        return "login/pwreset";
    }

    @PostMapping("/reset-pw")
    public String resetPassword(@ModelAttribute PasswordResetDto dto, Model model) {
        try {
            loginService.resetPassword(dto);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("passwordResetDto", dto);
            return "login/pwreset";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}