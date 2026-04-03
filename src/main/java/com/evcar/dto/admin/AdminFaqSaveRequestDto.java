package com.evcar.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminFaqSaveRequestDto {

    private String faqId;
    private String question;
    private String answer;
}