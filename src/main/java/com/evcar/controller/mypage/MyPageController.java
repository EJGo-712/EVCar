package com.evcar.controller.mypage;

import com.evcar.domain.user.UserStatus;
import com.evcar.dto.mypage.MyConsultationResponseDto;
import com.evcar.dto.mypage.MyInquiryResponseDto;
import com.evcar.dto.mypage.MyPageInfoResponseDto;
import com.evcar.dto.mypage.MyPageInfoUpdateRequestDto;
import com.evcar.dto.mypage.MyPageSummaryResponseDto;
import com.evcar.dto.mypage.MyWishlistResponseDto;
import com.evcar.dto.mypage.WithdrawRequestDto;
import com.evcar.service.mypage.MyPageService;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private static final String ACCESS_DENIED_VIEW = "mypage/memberAccessDenied";

    private final MyPageService myPageService;

    @GetMapping
    public String myPageRedirect() {
        return "redirect:/mypage/main";
    }

    @GetMapping("/main")
    public String myPageMain(HttpSession session, Model model) {
        return handlePage(session, model, userId -> {
            MyPageInfoResponseDto info = myPageService.getMyPageInfo(userId);
            MyPageSummaryResponseDto summary = myPageService.getMyPageSummary(userId);

            model.addAttribute("currentUserId", userId);
            model.addAttribute("myPageInfo", info);
            model.addAttribute("summary", summary);
            return "mypage/myPageMain";
        });
    }

    @GetMapping("/info")
    public String myInfo(HttpSession session, Model model) {
        return handlePage(session, model, userId -> {
            MyPageInfoResponseDto info = myPageService.getMyPageInfo(userId);

            model.addAttribute("currentUserId", userId);
            model.addAttribute("myPageInfo", info);

            if (!model.containsAttribute("myPageInfoUpdateRequestDto")) {
                model.addAttribute("myPageInfoUpdateRequestDto", toUpdateRequestDto(info));
            }

            return "mypage/myInfo";
        });
    }

    @PostMapping("/info")
    public String updateMyInfo(HttpSession session,
                               @ModelAttribute MyPageInfoUpdateRequestDto dto,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        String userId = getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            myPageService.updateMyPageInfo(userId, dto);
            redirectAttributes.addFlashAttribute("message", "내 정보가 정상적으로 수정되었습니다.");
            return "redirect:/mypage/info";
        } catch (IllegalArgumentException e) {
            MyPageInfoResponseDto info = myPageService.getMyPageInfo(userId);

            model.addAttribute("currentUserId", userId);
            model.addAttribute("myPageInfo", info);
            model.addAttribute("myPageInfoUpdateRequestDto", toUpdateRequestDto(info));
            model.addAttribute("infoErrorMessage", e.getMessage());

            return "mypage/myInfo";
        }
    }

    @GetMapping("/wishlist")
    public String myWishlist(HttpSession session, Model model) {
        return handlePage(session, model, userId -> {
            MyPageInfoResponseDto info = myPageService.getMyPageInfo(userId);

            model.addAttribute("currentUserId", userId);
            model.addAttribute("myPageInfo", info);
            return "mypage/myWishlist";
        });
    }

    @GetMapping("/wishlist/api")
    @ResponseBody
    public List<MyWishlistResponseDto> wishlistApi(HttpSession session) {
        String userId = getUserId(session);
        if (userId == null) {
            return Collections.emptyList();
        }

        return myPageService.getMyWishlist(userId);
    }

    @PostMapping("/wishlist/delete")
    @ResponseBody
    public void deleteWishlist(HttpSession session,
                               @RequestParam("wishlistId") String wishlistId) {

        String userId = getUserId(session);
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        myPageService.deleteWishlist(userId, wishlistId);
    }

    @GetMapping("/consultation")
    public String myConsultation(HttpSession session, Model model) {
        return handlePage(session, model, userId -> {
            List<MyConsultationResponseDto> list = myPageService.getMyConsultations(userId);

            model.addAttribute("currentUserId", userId);
            model.addAttribute("consultations", list);
            return "mypage/myConsultation";
        });
    }

    @PostMapping("/consultation/cancel")
    public String cancelConsult(HttpSession session,
                                @RequestParam("consultId") String consultId) {

        String userId = getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        myPageService.cancelMyConsultation(userId, consultId);
        return "redirect:/mypage/consultation";
    }

    @GetMapping("/inquiry")
    public String myInquiry(HttpSession session, Model model) {
        return handlePage(session, model, userId -> {
            List<MyInquiryResponseDto> list = myPageService.getMyInquiries(userId);

            model.addAttribute("currentUserId", userId);
            model.addAttribute("inquiries", list);
            return "mypage/myInquiry";
        });
    }

    @GetMapping("/inquiry/{id}")
    public String myInquiryDetail(@PathVariable("id") String id,
                                  HttpSession session,
                                  Model model) {

        return handlePage(session, model, userId -> {
            MyInquiryResponseDto inquiry = myPageService.getMyInquiryDetail(userId, id);

            model.addAttribute("currentUserId", userId);
            model.addAttribute("inquiry", inquiry);
            return "mypage/myInquiryDetail";
        });
    }

    @GetMapping("/withdraw")
    public String myWithdraw(HttpSession session, Model model) {
        return handlePage(session, model, userId -> {
            MyPageInfoResponseDto info = myPageService.getMyPageInfo(userId);

            model.addAttribute("currentUserId", userId);
            model.addAttribute("myPageInfo", info);

            if (!model.containsAttribute("withdrawRequestDto")) {
                model.addAttribute("withdrawRequestDto", WithdrawRequestDto.builder().build());
            }

            return "mypage/myWithdraw";
        });
    }

    @PostMapping("/withdraw")
    public String withdraw(HttpSession session,
                           @ModelAttribute WithdrawRequestDto dto,
                           RedirectAttributes redirectAttributes) {

        String userId = getUserId(session);
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            myPageService.withdraw(userId, dto);
            session.invalidate();
            redirectAttributes.addFlashAttribute("message", "회원탈퇴가 완료되었습니다.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("withdrawErrorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("withdrawRequestDto", dto);
            return "redirect:/mypage/withdraw";
        }
    }

    private String handlePage(HttpSession session, Model model, PageHandler handler) {
        String userId = getUserId(session);

        if (userId == null) {
            return "redirect:/login";
        }

        try {
            MyPageInfoResponseDto info = myPageService.getMyPageInfo(userId);

            if (UserStatus.WITHDRAWN.name().equalsIgnoreCase(info.getUserStatus())) {
                session.invalidate();
                model.addAttribute("message", "탈퇴한 회원은 이용할 수 없습니다.");
                return ACCESS_DENIED_VIEW;
            }

            return handler.handle(userId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", "존재하지 않는 회원입니다.");
            return ACCESS_DENIED_VIEW;
        }
    }

    private String getUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId != null ? String.valueOf(userId) : null;
    }

    private MyPageInfoUpdateRequestDto toUpdateRequestDto(MyPageInfoResponseDto dto) {
        return MyPageInfoUpdateRequestDto.builder()
                .name(dto.getName())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .email(dto.getEmail())
                .hasVehicle(dto.getHasVehicle())
                .vehicleModel(dto.getVehicleModel())
                .vehicleYear(dto.getVehicleYear())
                .drivingDistance(dto.getDrivingDistance())
                .build();
    }

    @FunctionalInterface
    private interface PageHandler {
        String handle(String userId);
    }
}