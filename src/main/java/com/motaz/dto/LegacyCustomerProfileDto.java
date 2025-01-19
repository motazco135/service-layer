package com.motaz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegacyCustomerProfileDto {

    private Long customerId;
    private String fullName;
    private String email;
    private String phoneNumber;

}
