package com.evcar.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardPlaceholderController {

    @GetMapping("/consultation")
    public String consultationPlaceholder() {
        return "board/consultationPlaceholder";
    }

    @GetMapping("/inquiry")
    public String inquiryPlaceholder() {
        return "board/inquiryPlaceholder";
    }
}