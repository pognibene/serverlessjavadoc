package com.agileandmore.examples.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonClassDescription("A user in the system.")
public class User {

    /**
     * You need a no parameter constructor for proper schema generation
     */
    public User() {
    }

    @JsonPropertyDescription("The login for the User. This is the email address.")
    @Email
    private String login;

    @JsonPropertyDescription("User password.")
    private String password;

    @JsonPropertyDescription("User first name")
    @Size(min = 3, max = 50)
    private String firstName;

    @JsonPropertyDescription("User last name")
    private String lastName;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
