package com.evcar.dto.inquiry;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryCreateRequestDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}