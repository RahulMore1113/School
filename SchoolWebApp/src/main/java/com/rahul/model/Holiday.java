package com.rahul.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity {

	@Id
	private final String day;

	private final String reason;

	@Enumerated(EnumType.STRING)
	private final Type type;

	public enum Type {
		FESTIVAL, FEDERAL
	}

}
