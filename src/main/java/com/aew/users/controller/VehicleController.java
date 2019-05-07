package com.aew.users.controller;

import javax.servlet.http.HttpServletRequest;

import com.aew.users.domain.Vehicle;
import com.aew.users.error.VehicleNotFoundException;
import com.aew.users.messages.request.VehicleForm;
import com.aew.users.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicles;

    @GetMapping("")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(this.vehicles.findAll());
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody VehicleForm form, HttpServletRequest request) {
        Vehicle saved = this.vehicles.save(Vehicle.builder().name(form.getName()).build());
        return ResponseEntity.created(ServletUriComponentsBuilder.fromContextPath(request).path("/v1/vehicles/{id}")
                .buildAndExpand(saved.getId()).toUri()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.vehicles.findById(id).orElseThrow(() -> new VehicleNotFoundException()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody VehicleForm form) {
        Vehicle existed = this.vehicles.findById(id).orElseThrow(() -> new VehicleNotFoundException());
        existed.setName(form.getName());

        this.vehicles.save(existed);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Vehicle existed = this.vehicles.findById(id).orElseThrow(() -> new VehicleNotFoundException());
        this.vehicles.delete(existed);
        return ResponseEntity.noContent().build();
    }

}
