package com.aew.users.error;

public class VehicleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VehicleNotFoundException() {
    }

    public VehicleNotFoundException(Long vehicleId) {
        super("Vehicle: " + vehicleId + " not found.");
    }
}