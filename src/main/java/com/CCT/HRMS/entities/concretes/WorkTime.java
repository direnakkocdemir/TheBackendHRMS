package com.CCT.HRMS.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity // The javax.persistence annotation represents a table stored in a database
@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
@Table(name = "work_times") // The javax.persistence annotation the name of the table in the database
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","advertisements"})  //annotation to ignore fields specified by name at the class level
public class WorkTime {
    //properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "workTime",cascade = CascadeType.ALL)
    private List<Advertisement> advertisements;
}