package com.evcar.controller.consultation;

import com.evcar.dto.consultation.ConsultationCreateRequestDto;
import com.evcar.repository.vehicle.VehicleRepository;
import com.evcar.service.consultation.ConsultationService;
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
@RequestMapping("/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;
    private final VehicleRepository vehicleRepository;

    @GetMapping
    public String consultationList(@RequestParam(value = "keyword", required = false) String keyword,
                                   Model model) {
        model.addAttribute("consultationList", consultationService.getConsultationList(keyword));
        model.addAttribute("keyword", keyword);
        return "consultation/list";
    }

    @GetMapping("/form")
    public String consultationForm(Model model) {
        model.addAttribute("consultationCreateRequestDto", new ConsultationCreateRequestDto());
        model.addAttribute("vehicleList", vehicleRepository.findAllByOrderByBrandAscModelNameAsc());
        return "consultation/form";
    }

    @PostMapping("/form")
    public String createConsultation(@Valid @ModelAttribute ConsultationCreateRequestDto consultationCreateRequestDto,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vehicleList", vehicleRepository.findAllByOrderByBrandAscModelNameAsc());
            return "consultation/form";
        }

        consultationService.createConsultation(consultationCreateRequestDto);
        return "redirect:/consultation";
    }

    @GetMapping("/{id}")
    public String consultationDetail(@PathVariable("id") String consultId,
                                     Model model) {
        model.addAttribute("consultation", consultationService.getConsultationDetail(consultId));
        return "consultation/detail";
    }
}