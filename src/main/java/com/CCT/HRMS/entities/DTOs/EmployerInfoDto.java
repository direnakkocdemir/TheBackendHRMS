package com.CCT.HRMS.entities.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor // Lombok annottaion to generate constructors that take all arguments
public class EmployerInfoDto {
    // properties

    private int id;

    private String companyName;

    private String website;

    private String phone;

    private boolean is_verified;

    private boolean is_verified_by_staff;

    private String email;

    private int publishedYear;

    private String industry;

    private LocalDate created_at = LocalDate.now();

    private boolean is_active;

    private boolean is_deleted;

}
