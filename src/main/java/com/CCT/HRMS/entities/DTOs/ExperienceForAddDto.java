package com.CCT.HRMS.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor // Lombok annottaion to generate constructors that take all arguments
public class ExperienceForAddDto {
    // properties

    private int jobseekerId;
    private String jobTitle;
    private String description;
    private String company;
    private LocalDate startDate;
    private LocalDate endDate;

}
