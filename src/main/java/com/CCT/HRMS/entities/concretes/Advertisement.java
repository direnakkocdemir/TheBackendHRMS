package com.CCT.HRMS.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.CCT.HRMS.entities.concretes.Users.Employer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // The javax.persistence annotation represents a table stored in a database
@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
@Table(name = "advertisements") // The javax.persistence annotation the name of the table in the database
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Advertisement {
    //properties
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "open_positions")
    private int openPositions;

    @Column(name = "active")
    private boolean active;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "work_time_id", nullable = false)
    private WorkTime workTime;

}