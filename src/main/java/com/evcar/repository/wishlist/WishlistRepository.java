package com.evcar.repository.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evcar.domain.wishlist.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    boolean existsByVehicleId(Long vehicleId);

    void deleteByVehicleId(Long vehicleId);
   

    
}