package com.vacationplanner.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Enumerated
	private Role role;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String resetToken;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;

		return this.id == ((User) obj).getId();
	}
}