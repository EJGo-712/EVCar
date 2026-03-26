package com.evcar.controller.vehicle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.evcar.dto.vehicle.VehicleDetailDto;
import com.evcar.dto.vehicle.VehicleListDto;
import com.evcar.service.vehicle.VehicleService;
import com.evcar.service.vehicle.WishlistService;

import java.util.List;

@Controller
public class VehicleController {

	private final VehicleService vehicleService;
	private final WishlistService wishlistService;

	public VehicleController(VehicleService vehicleService, WishlistService wishlistService) {
		this.vehicleService = vehicleService;
		this.wishlistService = wishlistService;
	}

	// GET /vehicle → 전체 목록
	@GetMapping("/vehicle")
	public String vehicleHome(Model model) {
		List<VehicleListDto> vehicleList = vehicleService.getVehicleList(null, null);

		model.addAttribute("vehicleList", vehicleList);
		model.addAttribute("selectedBrand", "전체");
		model.addAttribute("selectedClass", "전체");
		model.addAttribute("totalCount", vehicleList.size());

		return "vehicle/list";
	}

	// GET /vehicle/list → 필터 목록
	@GetMapping("/vehicle/list")
	public String vehicleList(@RequestParam(name = "brand", defaultValue = "전체") String brand,
			@RequestParam(name = "vehicleClass", defaultValue = "전체") String vehicleClass, Model model) {

		// 🔥 핵심: "전체" → null 처리
		if ("전체".equals(brand)) {
			brand = null;
		}
		if ("전체".equals(vehicleClass)) {
			vehicleClass = null;
		}

		List<VehicleListDto> vehicleList = vehicleService.getVehicleList(brand, vehicleClass);

		model.addAttribute("vehicleList", vehicleList);
		model.addAttribute("selectedBrand", brand == null ? "전체" : brand);
		model.addAttribute("selectedClass", vehicleClass == null ? "전체" : vehicleClass);
		model.addAttribute("totalCount", vehicleList.size());

		return "vehicle/list";
	}

	// 상세 페이지
	@GetMapping("/vehicle/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {

		VehicleDetailDto dto = vehicleService.getDetail(id);
		dto.setWished(wishlistService.isWished(id));

		model.addAttribute("vehicle", dto);

		return "vehicle/detail";
	}
}