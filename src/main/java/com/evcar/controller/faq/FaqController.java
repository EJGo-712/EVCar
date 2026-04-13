package com.evcar.controller.faq;

import com.evcar.service.faq.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @GetMapping
    public String faqList(@RequestParam(value = "keyword", required = false) String keyword,
                          Model model) {
        model.addAttribute("faqList", faqService.getFaqList(keyword));
        model.addAttribute("keyword", keyword);
        return "faq/list";
    }

    @GetMapping("/search")
    public String faqSearch(@RequestParam(value = "keyword", required = false) String keyword,
                            Model model) {
        model.addAttribute("faqList", faqService.getFaqList(keyword));
        model.addAttribute("keyword", keyword);
        return "faq/list";
    }
}