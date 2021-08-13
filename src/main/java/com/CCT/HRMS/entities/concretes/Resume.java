package com.CCT.HRMS.entities.concretes;

import java.util.List;

import javax.persistence.*;

import com.CCT.HRMS.entities.concretes.ResumeInfos.About;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Education;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Experience;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Language;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Skill;
import com.CCT.HRMS.entities.concretes.Users.Jobseeker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // The javax.persistence annotation represents a table stored in a database
@Data // Lombok annotation to generate @Getter and @Setter methods
@Table(name = "resumes") // The javax.persistence annotation the name of the table in the database
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
public class Resume {
    //properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "jobseeker_id")
    private Jobseeker jobseeker;

    @OneToMany(mappedBy ="resume")
    private List<About> abouts;

    @OneToMany(mappedBy ="resume")
    private List<Experience> experiences;

    @OneToMany(mappedBy ="resume")
    private List<Language> languages;

    @OneToMany(mappedBy ="resume")
    private List<Education> educations;

    @OneToMany(mappedBy ="resume")
    private List<Skill> skills;

    @OneToMany(mappedBy ="resume")
    private List<Image> images;
}
