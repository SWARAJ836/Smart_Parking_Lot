package com.swaraj.ParkingLot.Entity;

import com.swaraj.ParkingLot.Enums.VehicleType;
import jakarta.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    private String licensePlate;

    @Enumerated(EnumType.STRING)
    private VehicleType type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

}
