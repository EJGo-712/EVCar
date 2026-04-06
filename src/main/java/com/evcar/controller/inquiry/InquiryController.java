package com.evcar.controller.inquiry;

import com.evcar.domain.user.User;
import com.evcar.dto.inquiry.InquiryCreateRequestDto;
import com.evcar.repository.user.UserRepository;
import com.evcar.service.inquiry.InquiryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final UserRepository userRepository;

    @GetMapping("/form")
    public String inquiryForm(HttpSession session, Model model) {
        String userId = getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!model.containsAttribute("inquiryCreateRequestDto")) {
            model.addAttribute("inquiryCreateRequestDto", InquiryCreateRequestDto.builder().build());
        }

        model.addAttribute("currentUserId", user.getUserId());
        model.addAttribute("currentLoginId", user.getLoginId());
        model.addAttribute("currentUserName", user.getName());

        return "inquiry/form";
    }

    @PostMapping("/form")
    public String createInquiry(@ModelAttribute InquiryCreateRequestDto inquiryCreateRequestDto,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        String userId = getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            inquiryService.createInquiry(userId, inquiryCreateRequestDto);
            redirectAttributes.addFlashAttribute("message", "문의가 등록되었습니다.");
            return "redirect:/mypage/inquiry";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("inquiryCreateRequestDto", inquiryCreateRequestDto);
            return "redirect:/inquiry/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String cancelInquiry(@PathVariable("id") String inquiryId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        String userId = getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            inquiryService.cancelInquiry(userId, inquiryId);
            redirectAttributes.addFlashAttribute("message", "문의가 취소되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/mypage/inquiry";
    }

    private String getUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId != null ? String.valueOf(userId) : null;
    }
}