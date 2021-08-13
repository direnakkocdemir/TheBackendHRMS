package com.CCT.HRMS.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor // Lombok annottaion to generate constructors that take all arguments
public class AdvertisementFilter {
    // properties

    private List<String> jobTitle;
    private List<Integer> locationId;
    private List<Integer> workTimeId;
}