package com.rahul.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rahul.annotation.FieldsValueMatch;
import com.rahul.annotation.PasswordValidator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Password do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email address do not match!"
        )
})
public class Person extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer personId;

	@NotBlank(message = "Name must not be blank")
	@Size(min = 3, message = "Name must be at least 3 characters long")
	private String name;

	@NotBlank(message = "Mobile number must not be blank")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	private String mobileNumber;

	@NotBlank(message = "Email must not be blank")
	@Email(message = "Please provide a valid email address")
	private String email;

	@NotBlank(message = "Confirm Email must not be blank")
	@Email(message = "Please provide a valid confirm email address")
	@Transient
	@JsonIgnore
	private String confirmEmail;

	@NotBlank(message = "Password must not be blank")
	@Size(min = 5, message = "Password must be at least 5 characters long")
	@PasswordValidator
	@JsonIgnore
	private String pwd;

	@NotBlank(message = "Confirm Password must not be blank")
	@Size(min = 5, message = "Confirm Password must be at least 5 characters long")
	@Transient
	@JsonIgnore
	private String confirmPwd;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "addressId")
	private Address address;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
	private Roles role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", referencedColumnName = "classId")
	private EazyClass eazyClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
            joinColumns = {
                    @JoinColumn(name = "person_id", referencedColumnName = "personId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
            })
    private Set<Courses> courses = new HashSet<>();

}
