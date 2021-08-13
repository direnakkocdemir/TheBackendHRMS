package com.CCT.HRMS.entities.concretes;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // The javax.persistence annotation represents a table stored in a database
@Data // Lombok annotation to generate @Getter and @Setter methods
@Table(name = "images") // The javax.persistence annotation the name of the table in the database
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","resume"})  //annotation to ignore fields specified by name at the class level
public class Image {
    //properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_type")
    private int imageType;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
