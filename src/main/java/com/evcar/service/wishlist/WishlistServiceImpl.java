package com.evcar.service.wishlist;

import com.evcar.domain.vehicle.Vehicle;
import com.evcar.domain.wishlist.Wishlist;
import com.evcar.dto.vehicle.VehicleListDto;
import com.evcar.repository.vehicle.VehicleRepository;
import com.evcar.repository.wishlist.WishlistRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistServiceImpl implements WishlistService {

    private static final String DEV_USER_ID = "user0001";

    private final WishlistRepository wishlistRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public boolean isWished(String vehicleId) {
        return wishlistRepository.existsByUserIdAndVehicleId(DEV_USER_ID, vehicleId);
    }

    @Override
    @Transactional
    public void add(String vehicleId) {
        if (wishlistRepository.existsByUserIdAndVehicleId(DEV_USER_ID, vehicleId)) {
            return;
        }

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 차량입니다."));

        Wishlist wishlist = Wishlist.builder()
                .wishlistId(generateWishlistId())
                .userId(DEV_USER_ID)
                .vehicleId(vehicle.getVehicleId())
                .createdAt(LocalDateTime.now())
                .build();

        wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public void remove(String vehicleId) {
        wishlistRepository.findByUserIdAndVehicleId(DEV_USER_ID, vehicleId)
                .ifPresent(wishlistRepository::delete);
    }

    @Override
    public List<VehicleListDto> getWishlistVehicles() {
        return wishlistRepository.findByUserIdOrderByCreatedAtDesc(DEV_USER_ID).stream()
                .map(Wishlist::getVehicleId)
                .map(this::getVehicle)
                .map(this::toVehicleListDto)
                .toList();
    }

    private Vehicle getVehicle(String vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 차량입니다. vehicleId=" + vehicleId));
    }

    private VehicleListDto toVehicleListDto(Vehicle vehicle) {
        return VehicleListDto.builder()
                .vehicleId(vehicle.getVehicleId())
                .brand(vehicle.getBrand())
                .modelName(vehicle.getModelName())
                .vehicleClass(vehicle.getVehicleClass())
                .priceBasic(vehicle.getPriceBasic())
                .imageUrl(vehicle.getImageUrl())
                .build();
    }

    private String generateWishlistId() {
        return wishlistRepository.findTopByOrderByWishlistIdDesc()
                .map(Wishlist::getWishlistId)
                .map(lastId -> {
                    int nextNumber = Integer.parseInt(lastId.substring(4)) + 1;
                    return String.format("wish%04d", nextNumber);
                })
                .orElse("wish0001");
    }
}