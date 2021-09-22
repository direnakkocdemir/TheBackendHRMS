package com.CCT.HRMS.entities.concretes.Users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // The javax.persistence annotation represents a table stored in a database
@Table(name="staffs") // The javax.persistence annotation the name of the table in the database
@Data // Lombok annotation to generate @Getter and @Setter methods
@PrimaryKeyJoinColumn(name = "user_id",referencedColumnName = "id")
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor  // Lombok annottaion to generate constructors that take all arguments
@EqualsAndHashCode(callSuper = true)
public class Staff extends User{
    //properties

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
}
