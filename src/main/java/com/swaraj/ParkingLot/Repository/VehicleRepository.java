package com.swaraj.ParkingLot.Repository;

import com.swaraj.ParkingLot.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
