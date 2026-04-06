package com.evcar.service.inquiry;

import com.evcar.dto.inquiry.InquiryCreateRequestDto;

public interface InquiryService {

    void createInquiry(String userId, InquiryCreateRequestDto dto);

    void cancelInquiry(String userId, String inquiryId);
}