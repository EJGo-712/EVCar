package com.evcar.controller.inquiry;

import com.evcar.dto.inquiry.InquiryCreateRequestDto;
import com.evcar.service.inquiry.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping
    public String inquiryList(@RequestParam(value = "keyword", required = false) String keyword,
                              Model model) {
        model.addAttribute("inquiryList", inquiryService.getInquiryList(keyword));
        model.addAttribute("keyword", keyword);
        return "inquiry/list";
    }

    @GetMapping("/form")
    public String inquiryForm(Model model) {
        model.addAttribute("inquiryCreateRequestDto", new InquiryCreateRequestDto());
        return "inquiry/form";
    }

    @PostMapping("/form")
    public String createInquiry(@Valid @ModelAttribute InquiryCreateRequestDto inquiryCreateRequestDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "inquiry/form";
        }

        inquiryService.createInquiry(inquiryCreateRequestDto);
        return "redirect:/inquiry";
    }

    @GetMapping("/{id}")
    public String inquiryDetail(@PathVariable("id") String inquiryId,
                                Model model) {
        model.addAttribute("inquiry", inquiryService.getInquiryDetail(inquiryId));
        return "inquiry/detail";
    }
}