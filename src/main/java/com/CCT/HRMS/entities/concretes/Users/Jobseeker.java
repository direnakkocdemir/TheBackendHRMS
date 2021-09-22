package com.CCT.HRMS.entities.concretes.Users;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // The javax.persistence annotation represents a table stored in a database
@Data // Lombok annotation to generate @Getter and @Setter methods
@Table(name = "jobseekers") // The javax.persistence annotation the name of the table in the database
@PrimaryKeyJoinColumn(name = "id") 
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","applications"})
public class Jobseeker extends User {
    //propertiess

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_verified")
    private boolean is_verified;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "job_title")
    private String jobTitle;


}
