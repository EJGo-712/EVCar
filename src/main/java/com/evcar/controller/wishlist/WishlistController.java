package com.evcar.controller.wishlist;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.evcar.dto.vehicle.VehicleListDto;
import com.evcar.service.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/add")
    @ResponseBody
    public String add(@RequestParam String vehicleId) {
        wishlistService.add(vehicleId);
        return "ok";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam String vehicleId) {
        wishlistService.remove(vehicleId);
        return "ok";
    }

    @GetMapping("/list")
    public String wishlistPage(Model model) {
        List<VehicleListDto> list = wishlistService.getWishlistVehicles();
        model.addAttribute("vehicleList", list);
        return "wishlist/list";
    }
}