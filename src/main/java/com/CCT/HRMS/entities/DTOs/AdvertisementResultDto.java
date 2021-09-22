package com.CCT.HRMS.entities.DTOs;


import com.CCT.HRMS.entities.concretes.Location;
import com.CCT.HRMS.entities.concretes.WorkTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor // Lombok annottaion to generate constructors that take all arguments
public class AdvertisementResultDto {
    //properties
    
    private int employerId;
    private String jobTitle;
    private String description;
    private Location location;
    private int openPositions;
    private WorkTime workTime;
}
