package com.simplon.labxpert.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;
import com.simplon.labxpert.model.dtoViews.Views;
import com.simplon.labxpert.model.enums.EUserRole;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.*;
/**
 * DTO for the User entity.
 * It contains all the attributes that a user can have.
 */
@Getter
@Setter
public class UserDTO {

    @JsonView({Views.CreateUser.class, Views.UpdateUser.class})
    private long userID;

    @JsonView({Views.CreateUser.class, Views.UpdateUser.class})
    @Email
    @NotEmpty(message = "Email is mandatory")
    private String email;

    @JsonIgnore
    private Boolean isEmailVerified;

    @JsonView({Views.CreateUser.class, Views.UpdateUser.class})
    @NotEmpty(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    @JsonView({Views.CreateUser.class})
    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one digit, one lowercase, one uppercase and one special character")
    private String password;
    @NotNull(message = "User role is mandatory")
    @JsonView({Views.CreateUser.class, Views.UpdateUser.class})
    private EUserRole userRole;

    @JsonView({Views.CreateUser.class, Views.UpdateUser.class})
    @NotEmpty(message = "Personal info is mandatory")
    private String personalInfo;
}