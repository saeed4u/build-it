package com.esi.project.buildit.planthire.common.domain.model;

import com.esi.project.buildit.planthire.common.domain.enums.Role;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Enumerated(EnumType.STRING)
	Role role;

	String firstName;

	String lastName;

	@Column(unique = true)
	String username;

	String password;

	public String getName(){
		return firstName +" "+lastName;
	}

	public void setName(String name){
		String [] names = name.split(" ");
		firstName = names[0];
		if (names.length > 1){
			lastName = names[1];
		}
	}

	public Employee(Role role, String firstName, String lastName, String username, String password) {
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	public Employee() {
	}
}
