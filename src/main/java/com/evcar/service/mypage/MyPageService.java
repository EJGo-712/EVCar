package com.evcar.service.mypage;

import com.evcar.dto.mypage.MyConsultationResponseDto;
import com.evcar.dto.mypage.MyInquiryResponseDto;
import com.evcar.dto.mypage.MyPageInfoResponseDto;
import com.evcar.dto.mypage.MyPageInfoUpdateRequestDto;
import com.evcar.dto.mypage.WithdrawRequestDto;
import java.util.List;

public interface MyPageService {

    MyPageInfoResponseDto getMyPageInfo(String userId);

    void updateMyPageInfo(String userId, MyPageInfoUpdateRequestDto requestDto);

    List<MyConsultationResponseDto> getMyConsultations(String userId);

    void cancelMyConsultation(String userId, String consultId);

    List<MyInquiryResponseDto> getMyInquiries(String userId);

    void withdraw(String userId, WithdrawRequestDto withdrawRequestDto);
}

