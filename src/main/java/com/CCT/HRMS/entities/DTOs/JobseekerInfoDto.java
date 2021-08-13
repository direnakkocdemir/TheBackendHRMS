package com.CCT.HRMS.entities.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor // Lombok annottaion to generate constructors that take all arguments
public class JobseekerInfoDto {
    // properties

    private int id;

    private String firstName;

    private String lastName;

    private boolean is_verified;

    private LocalDate dateOfBirth;

    private String jobTitle;

    private String email;

    private LocalDate created_at = LocalDate.now();

    private boolean is_active;

    private boolean is_deleted;

}
