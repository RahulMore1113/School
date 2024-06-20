package com.rahul.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Holiday {

	private final String day;
	private final String reason;
	private final Type type;

	public enum Type {
		FESTIVAL, FEDERAL
	}

}
