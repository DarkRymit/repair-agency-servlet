package com.epam.finalproject.dto;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private Set<RoleDTO> roles = new HashSet<>();

    private Set<WalletDTO> wallets = new HashSet<>();

    private ZonedDateTime creationDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    public UserDTO(Long id, String username, String email, String firstName, String lastName, String phone,
            Set<RoleDTO> roles, Set<WalletDTO> wallets, ZonedDateTime creationDate, String lastModifiedBy,
            ZonedDateTime lastModifiedDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.roles = roles;
        this.wallets = wallets;
        this.creationDate = creationDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
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

    public Set<RoleDTO> getRoles() {
        return this.roles;
    }

    public Set<WalletDTO> getWallets() {
        return this.wallets;
    }

    public ZonedDateTime getCreationDate() {
        return this.creationDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public void setWallets(Set<WalletDTO> wallets) {
        this.wallets = wallets;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(username,
                userDTO.username) && Objects.equals(email, userDTO.email) && Objects.equals(firstName,
                userDTO.firstName) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(
                phone, userDTO.phone) && Objects.equals(roles, userDTO.roles) && Objects.equals(wallets,
                userDTO.wallets) && Objects.equals(creationDate,
                userDTO.creationDate) && Objects.equals(lastModifiedBy,
                userDTO.lastModifiedBy) && Objects.equals(lastModifiedDate, userDTO.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, firstName, lastName, phone, roles, wallets, creationDate,
                lastModifiedBy,
                lastModifiedDate);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", roles=" + roles +
                ", wallets=" + wallets +
                ", creationDate=" + creationDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
