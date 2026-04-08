package com.evcar.service.mypage;

import com.evcar.dto.mypage.*;
import java.util.List;

public interface MyPageService {

	MyPageInfoResponseDto getMyPageInfo(String userId);

	void updateMyPageInfo(String userId, MyPageInfoUpdateRequestDto requestDto);

	List<MyConsultationResponseDto> getMyConsultations(String userId);

	void cancelMyConsultation(String userId, String consultId); // 🔥 수정

	List<MyInquiryResponseDto> getMyInquiries(String userId);

	MyInquiryResponseDto getMyInquiryDetail(String userId, String inquiryId); // 🔥 추가

	List<MyWishlistResponseDto> getMyWishlist(String userId); // 🔥 추가

	void deleteWishlist(String userId, String wishlistId); // 🔥 추가

	MyPageSummaryResponseDto getMyPageSummary(String userId); // 🔥 추가

	void withdraw(String userId, WithdrawRequestDto withdrawRequestDto);
}
