package com.CCT.HRMS.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.CCT.HRMS.entities.concretes.Users.Jobseeker;

@Entity // The javax.persistence annotation represents a table stored in a database
@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
@Table(name = "applications") // The javax.persistence annotation the name of the table in the database
public class Application {
    //properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="jobseeker_id")
    Jobseeker jobseeker;

    @ManyToOne
    @JoinColumn(name="advertisement_id")
    Advertisement advertisement;

    
}
