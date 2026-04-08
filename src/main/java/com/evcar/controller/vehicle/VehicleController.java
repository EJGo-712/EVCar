package com.evcar.controller.vehicle;

import com.evcar.dto.vehicle.VehicleDetailDto;
import com.evcar.dto.vehicle.VehicleImageResponseDto;
import com.evcar.dto.vehicle.VehicleListDto;
import com.evcar.service.vehicle.VehicleService;
import com.evcar.service.wishlist.WishlistService;
import com.evcar.service.vehicle.VehicleImageService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final WishlistService wishlistService;
    private final VehicleImageService vehicleImageService;

    @GetMapping
    public String vehicleHome(Model model) {
        List<VehicleListDto> vehicleList = vehicleService.getVehicleList(null, null);

        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("selectedBrand", "전체");
        model.addAttribute("selectedClass", "전체");
        model.addAttribute("totalCount", vehicleList.size());

        return "vehicle/list";
    }

    @GetMapping("/list")
    public String vehicleList(
            @RequestParam(name = "brand", defaultValue = "전체") String brand,
            @RequestParam(name = "vehicleClass", defaultValue = "전체") String vehicleClass,
            Model model) {

        String originalBrand = brand;

        if ("전체".equals(brand)) brand = null;
        if ("전체".equals(vehicleClass)) vehicleClass = null;

        if ("현대".equals(brand)) brand = "HYUNDAI";
        if ("기아".equals(brand)) brand = "KIA";

        List<VehicleListDto> vehicleList = vehicleService.getVehicleList(brand, vehicleClass);

        model.addAttribute("vehicleList", vehicleList);
        model.addAttribute("selectedBrand", originalBrand == null ? "전체" : originalBrand);
        model.addAttribute("selectedClass", vehicleClass == null ? "전체" : vehicleClass);
        model.addAttribute("totalCount", vehicleList.size());

        return "vehicle/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") String id, Model model) {

        VehicleDetailDto dto = vehicleService.getDetail(id);
        dto.setWished(wishlistService.isWished(id));

        List<VehicleImageResponseDto> images = vehicleImageService.getImages(id);

        model.addAttribute("vehicle", dto);
        model.addAttribute("images", images);

        return "vehicle/detail";
    }
}