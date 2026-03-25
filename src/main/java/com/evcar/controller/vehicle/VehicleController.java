package com.evcar.controller.vehicle;

import com.evcar.dto.vehicle.VehicleListDto;
import com.evcar.service.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/vehicle/list")
    public String vehicleList(
            @RequestParam(name = "brand", defaultValue = "전체") String brand,
            @RequestParam(name = "vehicleClass", defaultValue = "전체") String vehicleClass,
            Model model) {

        List<VehicleListDto> vehicleList = vehicleService.getVehicleList(brand, vehicleClass);

        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("selectedBrand", brand);
        model.addAttribute("selectedClass", vehicleClass);

        return "vehicle/list";
    }
    
}