package com.motaz.service;

import com.motaz.dto.LegacyCustomerProfileDto;
import com.motaz.dto.ModernCustomerProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerProfileAdapter adapter;
    private final ModernSystemClient modernSystemClient;

    //Get Customer Profile
    public LegacyCustomerProfileDto getCustomer(Long customerId) {
        // Simulate fetching data from the modern system
        ModernCustomerProfileDto modernProfile = modernSystemClient.getModernCustomerProfile(customerId);
        // Map the modern response to legacy format
        return adapter.toLegacy(modernProfile);
    }

    // Create A customer Profile
    public LegacyCustomerProfileDto createCustomer(LegacyCustomerProfileDto legacyCustomerProfileDto) {
        // Map the legacy request to modern format
        ModernCustomerProfileDto modernProfile = adapter.toModern(legacyCustomerProfileDto);
        // Call the modern system's create API
        ModernCustomerProfileDto createdProfile = modernSystemClient.createModernCustomerProfile(modernProfile);
        // Map the modern response to legacy format
        return adapter.toLegacy(createdProfile);

    }

    // Update a customer profile
    public LegacyCustomerProfileDto updateCustomerProfile(Long customerId, LegacyCustomerProfileDto legacyCustomerProfileDto) {
        // Map the legacy request to modern format
        ModernCustomerProfileDto modernProfile = adapter.toModern(legacyCustomerProfileDto);
        // Call the modern system's update API
        ModernCustomerProfileDto updatedProfile = modernSystemClient.updateModernCustomerProfile(customerId, modernProfile);
        // Map the modern response to legacy format
        return adapter.toLegacy(updatedProfile);
    }
}
