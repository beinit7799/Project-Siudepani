package com.bway.springdemo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="User",uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	private String fname;
	private String lname;
	private String number;
	private int amount;
	private String username;
	private String password;
	private String role;
	private String email;
}
