package com.CCT.HRMS.entities.concretes.Users;

import java.util.List;

import javax.persistence.*;

import com.CCT.HRMS.entities.concretes.EmployerAbout;
import com.CCT.HRMS.entities.concretes.Advertisement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // The javax.persistence annotation represents a table stored in a database
@Data // Lombok annotation to generate @Getter and @Setter methods
@Table(name = "employers") // The javax.persistence annotation the name of the table in the database
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","advertisements"}) //annotation to ignore fields specified by name at the class level
@EqualsAndHashCode(callSuper = true)
public class Employer extends User{
    //proporties

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "website")
    private String website;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_verified")
    private boolean is_verified;
    
    @Column(name = "is_verified_by_staff")
    private boolean is_verified_by_staff;

    @Column(name = "published_year")
    private int publishedYear;

    @Column(name = "industry")
    private String industry;

    @OneToMany(mappedBy = "employer")
    private List<Advertisement> advertisements;

    @OneToOne(mappedBy = "employer")
    private EmployerAbout employerAbout;

}
