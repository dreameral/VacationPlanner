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

	@OneToOne
	private User admin;

	@OneToOne
	private User teamLeader;

	@Enumerated
	private Role role;

	private boolean enabled;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String resetToken;

	public User() {
		super();
		this.enabled = false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;

		return this.id == ((User) obj).getId();
	}
}