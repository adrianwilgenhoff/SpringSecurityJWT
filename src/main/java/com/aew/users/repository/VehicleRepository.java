package com.aew.users.repository;

import java.util.Optional;

import com.aew.users.domain.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findById(Long id);
}