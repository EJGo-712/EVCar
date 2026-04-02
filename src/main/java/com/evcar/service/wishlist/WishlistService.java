package com.evcar.service.wishlist;

import com.evcar.dto.vehicle.VehicleListDto;
import java.util.List;

public interface WishlistService {

    boolean isWished(String vehicleId);

    void add(String vehicleId);

    void remove(String vehicleId);

    List<VehicleListDto> getWishlistVehicles();
}