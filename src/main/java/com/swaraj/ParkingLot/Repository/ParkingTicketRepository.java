package com.swaraj.ParkingLot.Repository;

import com.swaraj.ParkingLot.Entity.ParkingSpot;
import com.swaraj.ParkingLot.Entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, String> {


}
