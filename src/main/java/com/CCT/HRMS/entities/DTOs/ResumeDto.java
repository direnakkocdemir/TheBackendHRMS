package com.CCT.HRMS.entities.DTOs;
import java.util.List;

import com.CCT.HRMS.entities.concretes.Image;
import com.CCT.HRMS.entities.concretes.ResumeInfos.About;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Education;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Experience;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Language;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
public class ResumeDto {
    //properties
    
    private int id;
    private int jobseekerId;
    private List<About> abouts;
    private List<Experience> experiences;
    private List<Language> languages;
    private List<Education> educations;
    private List<Skill> skills;
    private List<Image> images;
}
