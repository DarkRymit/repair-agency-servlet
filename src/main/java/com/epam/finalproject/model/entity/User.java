package com.epam.finalproject.model.entity;


import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@SqlTable("users")
public class User implements Serializable {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlColumn("username")
    private String username;

    @SqlColumn("email")
    private String email;

    @SqlColumn("password")
    private String password;

    @SqlColumn("first_name")
    private String firstName;

    @SqlColumn("last_name")
    private String lastName;

    @SqlColumn("phone")
    private String phone;
    private Set<Role> roles = new HashSet<>();

    private Set<Wallet> wallets = new HashSet<>();

    @SqlColumn("creation_date")
    private Instant creationDate;

    @SqlColumn("last_modified_by")
    private String lastModifiedBy;

    @SqlColumn("last_modified_date")
    private Instant lastModifiedDate;

    public User(Long id, String username, String email, String password, String firstName, String lastName,
            String phone, Set<Role> roles, Set<Wallet> wallets, Instant creationDate, String lastModifiedBy,
            Instant lastModifiedDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.roles = roles;
        this.wallets = wallets;
        this.creationDate = creationDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public User() {
    }

    private static Set<Role> $default$roles() {
        return new HashSet<>();
    }

    private static Set<Wallet> $default$wallets() {
        return new HashSet<>();
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void deleteRole(Role role) {
        this.roles.remove(role);
    }

    public void addWallet(Wallet wallet) {
        this.wallets.add(wallet);
    }

    public void deleteWallet(Wallet wallet) {
        this.wallets.remove(wallet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username,
                user.username) && Objects.equals(email, user.email) && Objects.equals(password,
                user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName,
                user.lastName) && Objects.equals(phone, user.phone) && Objects.equals(roles,
                user.roles) && Objects.equals(wallets, user.wallets) && Objects.equals(creationDate,
                user.creationDate) && Objects.equals(lastModifiedBy,
                user.lastModifiedBy) && Objects.equals(lastModifiedDate, user.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, firstName, lastName, phone, roles, wallets, creationDate,
                lastModifiedBy, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
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

    public Long getId() {
        return this.id;
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

    public Set<Role> getRoles() {
        return this.roles;
    }

    public Set<Wallet> getWallets() {
        return this.wallets;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setWallets(Set<Wallet> wallets) {
        this.wallets = wallets;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserBuilder toBuilder() {
        return new UserBuilder().id(this.id)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .phone(this.phone)
                .roles(this.roles)
                .wallets(this.wallets)
                .creationDate(this.creationDate)
                .lastModifiedBy(this.lastModifiedBy)
                .lastModifiedDate(this.lastModifiedDate);
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String phone;
        private Set<Role> roles$value;
        private boolean roles$set;
        private Set<Wallet> wallets$value;
        private boolean wallets$set;
        private Instant creationDate;
        private String lastModifiedBy;
        private Instant lastModifiedDate;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder roles(Set<Role> roles) {
            this.roles$value = roles;
            this.roles$set = true;
            return this;
        }

        public UserBuilder wallets(Set<Wallet> wallets) {
            this.wallets$value = wallets;
            this.wallets$set = true;
            return this;
        }

        public UserBuilder creationDate(Instant creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public UserBuilder lastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
            return this;
        }

        public UserBuilder lastModifiedDate(Instant lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public User build() {
            Set<Role> roles$value = this.roles$value;
            if (!this.roles$set) {
                roles$value = User.$default$roles();
            }
            Set<Wallet> wallets$value = this.wallets$value;
            if (!this.wallets$set) {
                wallets$value = User.$default$wallets();
            }
            return new User(id, username, email, password, firstName, lastName, phone, roles$value, wallets$value,
                    creationDate, lastModifiedBy, lastModifiedDate);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", username=" + this.username + ", email=" + this.email + ", password=" + this.password + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", phone=" + this.phone + ", roles$value=" + this.roles$value + ", wallets$value=" + this.wallets$value + ", creationDate=" + this.creationDate + ", lastModifiedBy=" + this.lastModifiedBy + ", lastModifiedDate=" + this.lastModifiedDate + ")";
        }
    }
}