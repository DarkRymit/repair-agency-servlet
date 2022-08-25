package com.epam.finalproject.request;


import java.util.Objects;

public class SignUpRequest {

    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    public SignUpRequest(String username, String email, String password, String firstName, String lastName,
            String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public SignUpRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SignUpRequest that = (SignUpRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(email,
                that.email) && Objects.equals(password, that.password) && Objects.equals(firstName,
                that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phone,
                that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, firstName, lastName, phone);
    }

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
