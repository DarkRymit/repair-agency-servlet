package com.epam.finalproject.model.entity;

import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlColumn;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlReferenceId;
import com.epam.finalproject.framework.data.sql.mapping.annotation.SqlTable;

import java.time.Instant;
import java.util.Objects;


@SqlTable("verification_tokens")
public class VerificationToken {

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

    public VerificationToken(Long id, String token, User user, Instant expiryDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public VerificationToken() {
    }

    public static VerificationTokenBuilder builder() {
        return new VerificationTokenBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VerificationToken that = (VerificationToken) o;
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
        return "VerificationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
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

    public static class VerificationTokenBuilder {
        private Long id;
        private String token;
        private User user;
        private Instant expiryDate;

        VerificationTokenBuilder() {
        }

        public VerificationTokenBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public VerificationTokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        public VerificationTokenBuilder user(User user) {
            this.user = user;
            return this;
        }

        public VerificationTokenBuilder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public VerificationToken build() {
            return new VerificationToken(id, token, user, expiryDate);
        }

        public String toString() {
            return "VerificationToken.VerificationTokenBuilder(id=" + this.id + ", token=" + this.token + ", user=" + this.user + ", expiryDate=" + this.expiryDate + ")";
        }
    }
}
