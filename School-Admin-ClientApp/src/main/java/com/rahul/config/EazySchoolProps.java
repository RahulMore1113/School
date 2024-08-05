package com.rahul.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "eazyschool")
@Validated
public class EazySchoolProps {

	@Min(value = 5, message = "Page Size must be between 5 and 25")
	@Max(value = 25, message = "Page Size must be between 5 and 25")
	private Integer pageSize;

	private Map<String, String> contact;

}
