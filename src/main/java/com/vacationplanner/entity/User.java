package com.vacationplanner.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User extends BasicEntity {
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

		return this.id.equals(((User) obj).getId());
	}
}