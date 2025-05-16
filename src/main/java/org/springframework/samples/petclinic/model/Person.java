package org.springframework.samples.petclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public class Person extends BaseEntity {

	@Column(name = "first_name")
	@NotBlank(message = "First name cannot be empty")
	@Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
	@Pattern(regexp = "^[a-zA-Z\\s-']+$",
			message = "First name should contain only letters, spaces, hyphens and apostrophes")
	private String firstName;

	@Column(name = "last_name")
	@NotBlank(message = "Last name cannot be empty")
	@Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
	@Pattern(regexp = "^[a-zA-Z\\s-']+$",
			message = "Last name should contain only letters, spaces, hyphens and apostrophes")
	private String lastName;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
