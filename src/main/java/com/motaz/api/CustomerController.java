package com.motaz.api;

import com.motaz.dto.LegacyCustomerProfileDto;
import com.motaz.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<LegacyCustomerProfileDto> getCustomer(@PathVariable Long id) {
        LegacyCustomerProfileDto legacyCustomerProfileDto = customerService.getCustomer(id);
        return ResponseEntity.ok(legacyCustomerProfileDto);
    }

    @PostMapping
    public ResponseEntity<LegacyCustomerProfileDto> createCustomer(@RequestBody LegacyCustomerProfileDto legacyCustomerProfileDto) {
        LegacyCustomerProfileDto newCustomerDto = customerService.createCustomer(legacyCustomerProfileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LegacyCustomerProfileDto> updateCustomerProfile(@PathVariable Long id, @RequestBody LegacyCustomerProfileDto legacyCustomerProfileDto) {
        LegacyCustomerProfileDto updatedCustomerProfile = customerService.updateCustomerProfile(id,legacyCustomerProfileDto);
        return ResponseEntity.ok(updatedCustomerProfile);
    }
}
