package com.evcar.service.vehicle;

import java.util.List;
import com.evcar.dto.vehicle.VehicleListDto;

public interface WishlistService {
	boolean isWished(String vehicleId);
    void add(String vehicleId);
    void remove(String vehicleId);
    List<VehicleListDto> getWishlistVehicles();
    
}