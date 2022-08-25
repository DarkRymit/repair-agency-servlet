package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.time.Instant;
import java.util.Objects;


@SqlTable("password_reset_tokens")
public class PasswordResetToken {

    @SqlId
    @SqlColumn
    private Long id;

    @SqlColumn("token")
    private String token;

    @SqlReferenceId
    @SqlColumn("user_id")
    private User user;

    @SqlColumn("expiry_date")
    private Instant expiryDate;


    public PasswordResetToken(Long id, String token, User user, Instant expiryDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public PasswordResetToken() {
    }

    public static PasswordResetTokenBuilder builder() {
        return new PasswordResetTokenBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getToken() {
        return this.token;
    }

    public User getUser() {
        return this.user;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PasswordResetToken that = (PasswordResetToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token,
                that.token) && Objects.equals(user, that.user) && Objects.equals(expiryDate,
                that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, user, expiryDate);
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public static class PasswordResetTokenBuilder {
        private Long id;
        private String token;
        private User user;
        private Instant expiryDate;

        PasswordResetTokenBuilder() {
        }

        public PasswordResetTokenBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PasswordResetTokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        public PasswordResetTokenBuilder user(User user) {
            this.user = user;
            return this;
        }

        public PasswordResetTokenBuilder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public PasswordResetToken build() {
            return new PasswordResetToken(id, token, user, expiryDate);
        }

        public String toString() {
            return "PasswordResetToken.PasswordResetTokenBuilder(id=" + this.id + ", token=" + this.token + ", user=" + this.user + ", expiryDate=" + this.expiryDate + ")";
        }
    }
}
