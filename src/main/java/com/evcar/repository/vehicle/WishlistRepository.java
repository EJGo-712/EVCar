package com.evcar.repository.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import com.evcar.domain.vehicle.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, String> {

    boolean existsByVehicleId(String vehicleId);

    void deleteByVehicleId(String vehicleId);
   

    
}