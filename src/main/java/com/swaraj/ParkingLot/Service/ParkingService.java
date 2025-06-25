package com.swaraj.ParkingLot.Service;

import com.swaraj.ParkingLot.Entity.ParkingSpot;
import com.swaraj.ParkingLot.Entity.ParkingTicket;
import com.swaraj.ParkingLot.Entity.Vehicle;
import com.swaraj.ParkingLot.Enums.SpotType;
import com.swaraj.ParkingLot.Enums.VehicleType;
import com.swaraj.ParkingLot.Repository.ParkingSpotRepository;
import com.swaraj.ParkingLot.Repository.ParkingTicketRepository;
import com.swaraj.ParkingLot.Repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private ParkingSpotRepository pSpotRepo;
    @Autowired
    private ParkingTicketRepository pTicketRepo;

    private Map<VehicleType, SpotType> mapTypeToSpot = Map.of(
            VehicleType.MOTORCYCLE, SpotType.SMALL,
            VehicleType.CAR,        SpotType.MEDIUM,
            VehicleType.BUS,        SpotType.LARGE
    );

    private Map<VehicleType, BigDecimal> mapRate = Map.of(
            VehicleType.MOTORCYCLE, BigDecimal.valueOf(10),
            VehicleType.CAR,        BigDecimal.valueOf(20),
            VehicleType.BUS,        BigDecimal.valueOf(50)
    );

    // Service for ADDING Single Parking Spot
    @Transactional
    public ParkingSpot addSpot(ParkingSpot spot){
        return pSpotRepo.save(spot);
    }

    // Service for ADDING Bulk Parking Spot
    @Transactional
    public List<ParkingSpot> addSpotsBulk(List<ParkingSpot> spots){
        return pSpotRepo.saveAll(spots);
    }

    // Service to GET all Available Parking Spots
    public Map<SpotType, Long> getAvailability() {

        return pSpotRepo.findAll().stream()
                .filter(s-> !s.isOccupied())
                .collect(Collectors.groupingBy(ParkingSpot::getSpotType, Collectors.counting()));
    }

    // Service for Vehicle Check In
    @Transactional
    public ParkingTicket checkIn(Vehicle vehicle) {

        vehicleRepo.save(vehicle);
            // To find the required spot type for the particular Vehicle Type
        SpotType required = mapTypeToSpot.get(vehicle.getType());

            //To find the available spots for the particular Vehicle Type
        List<ParkingSpot> spots = pSpotRepo.findAvailableSpots(required);
            // exception handling for no empty spots
        if (spots.isEmpty()) throw new RuntimeException("NO SPOTS AVAILABLE CURRENTLY");


        ParkingSpot spot = spots.get(0);//selecting the first spot from the List of available spots
        spot.setOccupied(true);         //setting the "occupied" field as true
        pSpotRepo.save(spot);           //saving the spot to repo

            // Assigning Parking Ticket
        ParkingTicket ticket = new ParkingTicket();
        ticket.setTicketId(UUID.randomUUID().toString());   //assigning Random UID
        ticket.setEntryTime(LocalDateTime.now());           //setting entry time
        ticket.setVehicle(vehicle);
        ticket.setSpot(spot);

        return pTicketRepo.save(ticket);

    }


    // Service for Vehicle Check Out
    @Transactional
    public ParkingTicket checkOut(String ticketId) {

        ParkingTicket ticket = pTicketRepo.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));  //Exception Handling
        ticket.setExitTime(LocalDateTime.now());            //setting exit time for vehicle

        long hours = Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours();
        hours = (hours ==0) ? 1 : hours;                    //logic for calculating time, assigning at least 1 Hour

        BigDecimal rate = mapRate.get(ticket.getVehicle().getType());

        ticket.setFee(rate.multiply(BigDecimal.valueOf(hours)));

        ParkingSpot spot = ticket.getSpot();
        spot.setOccupied(false);                            //setting the "occupied" field as false
        pSpotRepo.save(spot);

        return pTicketRepo.save(ticket);

    }



}
