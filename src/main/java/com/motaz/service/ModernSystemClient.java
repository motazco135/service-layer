package com.motaz.service;

import com.motaz.dto.ModernCustomerProfileDto;
import org.springframework.stereotype.Component;

@Component
public class ModernSystemClient {

    // Simulate calling the modern system's create API
    public ModernCustomerProfileDto createModernCustomerProfile(ModernCustomerProfileDto modernProfile) {
        // Simulate creating a customer in the modern system
        return modernProfile; // In a real scenario, this would call the modern system's API
    }

    // Simulate calling the modern system's update API
    public ModernCustomerProfileDto updateModernCustomerProfile(Long customerId, ModernCustomerProfileDto modernProfile) {
        // Simulate updating a customer in the modern system
        return modernProfile; // In a real scenario, this would call the modern system's API
    }

    // Simulate calling the modern system's get API
    public ModernCustomerProfileDto getModernCustomerProfile(Long customerId) {
        // Simulate fetching a customer from the modern system
        ModernCustomerProfileDto modernProfile = new ModernCustomerProfileDto();
        modernProfile.setId(customerId);
        modernProfile.setFirstName("Motaz");
        modernProfile.setLastName("Ahmed");
        modernProfile.setEmailAddress("new@new.com");
        modernProfile.setContactNumber("00000000000");
        return modernProfile;
    }

}
