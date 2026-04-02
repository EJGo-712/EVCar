package com.evcar.controller.wishlist;

import com.evcar.service.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/add/{vehicleId}")
    public String add(@PathVariable("vehicleId") String vehicleId) {
        wishlistService.add(vehicleId);
        return "redirect:/vehicle/" + vehicleId;
    }

    @PostMapping("/remove/{vehicleId}")
    public String remove(@PathVariable("vehicleId") String vehicleId) {
        wishlistService.remove(vehicleId);
        return "redirect:/vehicle/" + vehicleId;
    }
}