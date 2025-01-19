package com.motaz.service;

import com.motaz.dto.LegacyCustomerProfileDto;
import com.motaz.dto.ModernCustomerProfileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerProfileAdapter {

    // Map modern to legacy (for get)
    public LegacyCustomerProfileDto toLegacy(ModernCustomerProfileDto modernProfileDto) {
        if(modernProfileDto == null){
            log.error("Transformation failed: modernProfileDto is null");
            throw new IllegalArgumentException("modernProfileDto cannot be null");
        }
        String firstName = modernProfileDto.getFirstName() != null ? modernProfileDto.getFirstName() : "";
        String lastName = modernProfileDto.getLastName() != null ? modernProfileDto.getLastName() : "";

        log.info("Transforming ModernCustomerProfileDto to LegacyCustomerProfileDto: {}", modernProfileDto);
        return  LegacyCustomerProfileDto.builder()
                .customerId(modernProfileDto.getId())
                .fullName(firstName + " " + lastName)
                .email(modernProfileDto.getEmailAddress())
                .phoneNumber(modernProfileDto.getContactNumber()).build();
    }

    // Map legacy to modern (for create and update)
    public ModernCustomerProfileDto toModern(LegacyCustomerProfileDto legacyProfileDto) {
        if(legacyProfileDto == null){
            log.error("Transformation failed: legacyProfileDto is null");
            throw new IllegalArgumentException("LegacyCustomerProfileDto cannot be null");
        }
        String[] names = legacyProfileDto.getFullName().split(" ",2);
        String firstName = names.length > 0 ? names[0].trim() : "";
        String lastName = names.length > 1 ? names[1].trim() : "";

        log.info("Transforming LegacyCustomerProfileDto to ModernCustomerProfileDto: {}", legacyProfileDto);
        return ModernCustomerProfileDto.builder()
                .id(legacyProfileDto.getCustomerId())
                .firstName(firstName)
                .lastName(lastName)
                .emailAddress(legacyProfileDto.getEmail())
                .contactNumber(legacyProfileDto.getPhoneNumber()).build();
    }

}
