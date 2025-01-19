package com.motaz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModernCustomerProfileDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String contactNumber;

}
