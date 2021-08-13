package com.CCT.HRMS.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor // Lombok annottaion to generate constructors that take all arguments
public class AdvertisementDto {
    //properties 

    private int id;
    private int employerId;
    private String jobTitle;
    private String description;
    private int locationId;
    private int openPosition;
    private int workTimeId;
}
