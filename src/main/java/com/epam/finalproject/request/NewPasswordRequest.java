package com.epam.finalproject.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class NewPasswordRequest {

    @NotBlank
    @Size(min = 8,max = 14)
    String password;

    public NewPasswordRequest(String password) {
        this.password = password;
    }

    public NewPasswordRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewPasswordRequest that = (NewPasswordRequest) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        return "NewPasswordRequest{" +
                "password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
