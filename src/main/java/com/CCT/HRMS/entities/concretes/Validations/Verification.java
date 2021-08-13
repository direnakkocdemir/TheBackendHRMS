package com.CCT.HRMS.entities.concretes.Validations;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate @Getter and @Setter methods
@Entity // The javax.persistence annotation represents a table stored in a database
@Table(name = "verifications") // The javax.persistence annotation the name of the table in the database
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
public class Verification {
    //properties

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "code")
    private String code;
    @Column(name = "is_verified")
    private boolean isVerified;
    @Column(name = "created_at")
    private LocalDate createdAt;

    public Verification(int userId,String code,boolean isVerified){
        this.userId= userId;
        this.code= code;
        this.isVerified = isVerified;
        this.createdAt = LocalDate.now();
    }
}
