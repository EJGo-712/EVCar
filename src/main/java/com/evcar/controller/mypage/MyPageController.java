package com.evcar.controller.mypage;

import com.evcar.dto.mypage.MyConsultationResponseDto;
import com.evcar.dto.mypage.MyInquiryResponseDto;
import com.evcar.dto.mypage.MyPageInfoResponseDto;
import com.evcar.dto.mypage.MyPageInfoUpdateRequestDto;
import com.evcar.dto.mypage.WithdrawRequestDto;
import com.evcar.service.mypage.MyPageService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public String myPageRedirect() {
        return "redirect:/mypage/main";
    }

    @GetMapping("/main")
    public String myPageMain(HttpSession session, Model model) {
        String userId = getUserId(session);
        MyPageInfoResponseDto myPageInfo = myPageService.getMyPageInfo(userId);

        model.addAttribute("myPageInfo", myPageInfo);
        return "mypage/myPageMain";
    }

    @GetMapping("/info")
    public String myInfo(HttpSession session, Model model) {
        String userId = getUserId(session);
        MyPageInfoResponseDto myPageInfo = myPageService.getMyPageInfo(userId);

        model.addAttribute("myPageInfo", myPageInfo);
        model.addAttribute("myPageInfoUpdateRequestDto", toUpdateRequestDto(myPageInfo));

        return "mypage/myInfo";
    }

    @PostMapping("/info")
    public String updateMyInfo(
            HttpSession session,
            @ModelAttribute MyPageInfoUpdateRequestDto myPageInfoUpdateRequestDto
    ) {
        String userId = getUserId(session);
        myPageService.updateMyPageInfo(userId, myPageInfoUpdateRequestDto);

        return "redirect:/mypage/info";
    }

    @GetMapping("/wishlist")
    public String myWishlist() {
        return "mypage/myWishlist";
    }

    @GetMapping("/consultation")
    public String myConsultation(HttpSession session, Model model) {
        String userId = getUserId(session);
        List<MyConsultationResponseDto> consultations = myPageService.getMyConsultations(userId);

        model.addAttribute("consultations", consultations);
        return "mypage/myConsultation";
    }

    @PostMapping("/consultation/cancel")
    public String cancelMyConsultation(
            HttpSession session,
            @RequestParam("consultId") String consultId
    ) {
        String userId = getUserId(session);
        myPageService.cancelMyConsultation(userId, consultId);

        return "redirect:/mypage/consultation";
    }

    @GetMapping("/inquiry")
    public String myInquiry(HttpSession session, Model model) {
        String userId = getUserId(session);
        List<MyInquiryResponseDto> inquiries = myPageService.getMyInquiries(userId);

        model.addAttribute("inquiries", inquiries);
        return "mypage/myInquiry";
    }

    @GetMapping("/withdraw")
    public String myWithdraw(HttpSession session, Model model) {
        String userId = getUserId(session);
        MyPageInfoResponseDto myPageInfo = myPageService.getMyPageInfo(userId);

        model.addAttribute("myPageInfo", myPageInfo);
        model.addAttribute("withdrawRequestDto", WithdrawRequestDto.builder().build());

        return "mypage/myWithdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(
            HttpSession session,
            @ModelAttribute WithdrawRequestDto withdrawRequestDto
    ) {
        String userId = getUserId(session);
        myPageService.withdraw(userId, withdrawRequestDto);

        session.invalidate();
        return "redirect:/";
    }
    /*로그인이 완성되면 이 부분의 주석을 풀 것 
    private String getUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");

        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return String.valueOf(userId);
    }
    */
    
    private String getUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");

        if (userId == null) {
            return "user0001";
        }

        return String.valueOf(userId);
    }

    private MyPageInfoUpdateRequestDto toUpdateRequestDto(MyPageInfoResponseDto responseDto) {
        return MyPageInfoUpdateRequestDto.builder()
                .name(responseDto.getName())
                .birthDate(responseDto.getBirthDate() != null
                        ? responseDto.getBirthDate()
                        : java.time.LocalDate.of(1990, 1, 1))
                .gender(responseDto.getGender())
                .phone(responseDto.getPhone())
                .address(responseDto.getAddress())
                .addressDetail(responseDto.getAddressDetail())
                .email(responseDto.getEmail())
                .hasVehicle(responseDto.getHasVehicle())
                .vehicleModel(responseDto.getVehicleModel())
                .vehicleYear(responseDto.getVehicleYear())
                .drivingDistance(responseDto.getDrivingDistance())
                .build();
    }
}