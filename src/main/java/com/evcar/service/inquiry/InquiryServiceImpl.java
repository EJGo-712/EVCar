package com.evcar.service.inquiry;

import com.evcar.domain.inquiry.Inquiry;
import com.evcar.domain.user.User;
import com.evcar.dto.inquiry.InquiryCreateRequestDto;
import com.evcar.repository.inquiry.InquiryRepository;
import com.evcar.repository.user.UserRepository;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryServiceImpl implements InquiryService {

    private static final String DEFAULT_REPLY_STATUS = "WAITING";
    private static final String INQUIRY_PREFIX = "IQ";
    private static final int INQUIRY_NUMBER_LENGTH = 4;

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createInquiry(String userId, InquiryCreateRequestDto dto) {
        validateDto(dto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        validatePassword(user, dto.getPassword());

        Inquiry inquiry = Inquiry.builder()
                .inquiryId(generateInquiryId())
                .user(user)
                .title(dto.getTitle().trim())
                .content(dto.getContent().trim())
                .replyContent(null)
                .replyStatus(DEFAULT_REPLY_STATUS)
                .build();

        inquiryRepository.save(inquiry);
    }

    @Override
    @Transactional
    public void cancelInquiry(String userId, String inquiryId) {
        Inquiry inquiry = inquiryRepository.findByInquiryIdAndUserUserId(inquiryId, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문의입니다."));

        if (!DEFAULT_REPLY_STATUS.equals(inquiry.getReplyStatus())) {
            throw new IllegalArgumentException("답변 대기 상태의 문의만 취소할 수 있습니다.");
        }

        inquiryRepository.delete(inquiry);
    }

    private void validateDto(InquiryCreateRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("문의 제목을 입력해주세요.");
        }

        if (dto.getTitle().trim().length() > 200) {
            throw new IllegalArgumentException("문의 제목은 200자 이하로 입력해주세요.");
        }

        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("문의 내용을 입력해주세요.");
        }

        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }

    private void validatePassword(User user, String inputPassword) {
        if (!passwordEncoder.matches(inputPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private String generateInquiryId() {
        int nextNumber = inquiryRepository.findAll().stream()
                .map(Inquiry::getInquiryId)
                .filter(this::isInquiryIdPattern)
                .map(this::extractNumber)
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;

        return INQUIRY_PREFIX + String.format("%0" + INQUIRY_NUMBER_LENGTH + "d", nextNumber);
    }

    private boolean isInquiryIdPattern(String inquiryId) {
        return inquiryId != null && inquiryId.matches("^[A-Za-z]{2}\\d{4}$");
    }

    private int extractNumber(String inquiryId) {
        return Integer.parseInt(inquiryId.substring(2));
    }
}