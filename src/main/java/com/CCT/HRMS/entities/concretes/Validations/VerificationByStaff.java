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

@Entity // The javax.persistence annotation represents a table stored in a database
@Table(name = "verification_by_staff") // The javax.persistence annotation the name of the table in the database
@Data // Lombok annotation to generate @Getter and @Setter methods
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
public class VerificationByStaff {
    //properties

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "staff_id")
    private int staffId;
    @Column(name = "is_verified")
    private boolean isVerified;
    @Column(name = "created_at")
    private LocalDate createdAt;
    
    public VerificationByStaff(int userId, boolean isVerified,LocalDate createdAt){
        this.userId = userId;
        this.isVerified = isVerified;
        this.createdAt = createdAt;
    }
}
