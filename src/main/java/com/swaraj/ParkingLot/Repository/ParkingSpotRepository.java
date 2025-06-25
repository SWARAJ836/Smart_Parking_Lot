package com.swaraj.ParkingLot.Repository;

import com.swaraj.ParkingLot.Entity.ParkingSpot;
import com.swaraj.ParkingLot.Entity.Vehicle;
import com.swaraj.ParkingLot.Enums.SpotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    @Query("SELECT s FROM ParkingSpot s WHERE s.isOccupied=false AND s.spotType >= :spotType ORDER BY s.level, s.zone")
    List<ParkingSpot> findAvailableSpots (@Param("spotType") SpotType spotType);
}
