package com.swaraj.ParkingLot.controller;


import com.swaraj.ParkingLot.DTO.CheckoutRequest;
import com.swaraj.ParkingLot.Entity.ParkingSpot;
import com.swaraj.ParkingLot.Entity.ParkingTicket;
import com.swaraj.ParkingLot.Entity.Vehicle;
import com.swaraj.ParkingLot.Enums.SpotType;
import com.swaraj.ParkingLot.Service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")

public class ParkingLotController {

    @Autowired
    private ParkingService parkingService;

    // Controller for ADDING Single Parking Spot
    @PostMapping("/addSpot")
    public ResponseEntity<ParkingSpot> addSpot(@RequestBody ParkingSpot spot){
        return ResponseEntity.ok(parkingService.addSpot(spot));
    }

    // Controller for ADDING Bulk Parking Spots
    @PostMapping("/addSpots")
    public ResponseEntity<List<ParkingSpot>> addSpots(@RequestBody List<ParkingSpot> spots){
        return ResponseEntity.ok(parkingService.addSpotsBulk(spots));
    }

    // Controller for Fetching all Available Parking Spots
    @GetMapping("/availableSpots")
    public ResponseEntity<Map<SpotType,Long>> availability(){
        return ResponseEntity.ok(parkingService.getAvailability());
    }

    // Controller for Vehicle Check In
    @PostMapping("/checkIn")
    public ResponseEntity<ParkingTicket> checkIn(@RequestBody Vehicle vehicle){
        System.out.println("Received API request for Check In : " );
        return ResponseEntity.ok(parkingService.checkIn(vehicle));
    }

    //  Controller for Vehicle Check Out using Path Variable
    @PostMapping("/checkOut/{ticketId}")
    public ResponseEntity<ParkingTicket> checkOut(@PathVariable String ticketId){
        System.out.println("Received API request for Check Out : " );
        return ResponseEntity.ok(parkingService.checkOut(ticketId));
    }

    //  Controller for Vehicle Check Out using Request Body
    @PostMapping("/checkOut2")
    public ResponseEntity<ParkingTicket> checkOut(@RequestBody CheckoutRequest request) {
        System.out.println("Received API request for Check Out -> ticketId: " + request.getTicketId());
        return ResponseEntity.ok(parkingService.checkOut(request.getTicketId()));
    }
}
