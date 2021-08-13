package com.CCT.HRMS.entities.DTOs;

import com.CCT.HRMS.entities.concretes.ResumeInfos.About;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Education;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Experience;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Language;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
@ToString //bu data annotation ile geliyor olmai ?
public class UpdateResumeDto {
    //properties
    
    private int userId;
    private About about;
    private Education education;
    private Skill skill;
    private Experience experience;
    private Language language;
}
