package com.CCT.HRMS.entities.concretes.Users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import java.time.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // The javax.persistence annotation represents a table stored in a database
@Inheritance(strategy = InheritanceType.JOINED)
@Data // Lombok annotation to generate @Getter and @Setter methods
@Table(name = "users") // The javax.persistence annotation the name of the table in the database
@NoArgsConstructor // Lombok annottaion to generate constructors that take no arguments
@AllArgsConstructor // Lombok annottaion to generate constructors that take all arguments
public class User {
	// properties

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "created_at")
	private LocalDate created_at = LocalDate.now();

	@Column(name = "is_active")
	private boolean is_active;

	@Column(name = "is_deleted")
	private boolean is_deleted;

}
