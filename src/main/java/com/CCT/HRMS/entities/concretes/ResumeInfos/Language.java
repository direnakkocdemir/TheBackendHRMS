package com.CCT.HRMS.entities.concretes.ResumeInfos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.CCT.HRMS.entities.concretes.Resume;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // The javax.persistence annotation represents a table stored in a database
@Data // Lombok annotation to generate @Getter and @Setter methods
@Table(name = "languages") // The javax.persistence annotation the name of the table in the database
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","resume"})  //annotation to ignore fields specified by name at the class level
public class Language {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "language")
    private String language;

    @Column(name = "level")
    private int level;

    @ManyToOne
	@JoinColumn(name = "resume_id")
    private Resume resume;
}
