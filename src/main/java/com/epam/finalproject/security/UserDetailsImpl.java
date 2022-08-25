package com.epam.finalproject.security;

import com.epam.finalproject.framework.security.GrantedAuthority;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.support.SimpleGrantedAuthority;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    public static final String SPRING_SECURITY_ROLE_MARKER = "ROLE_";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserDetailsImpl.class);

    String username;

    String password;

    Set<GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String password, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public UserDetailsImpl() {
    }

    public static UserDetailsImpl of(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getUsername(), user.getPassword(), constructSetAuthoritiesFrom(user));
        log.trace("Construct user details {}",userDetails);
        return userDetails;
    }

    private static Set<GrantedAuthority> constructSetAuthoritiesFrom(User user) {
        return constructSetAuthoritiesFrom(user::getRoles);
    }

    private static Set<GrantedAuthority> constructSetAuthoritiesFrom(Supplier<Collection<Role>> roleCollectionSupplier) {
        return roleCollectionSupplier.get()
                .stream()
                .map(role -> new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_MARKER + role.getName().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return authorities.stream()
                .noneMatch(a -> a.getAuthority().equals(SPRING_SECURITY_ROLE_MARKER + RoleEnum.BLOCKED));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return authorities.stream()
                .noneMatch(a -> a.getAuthority().equals(SPRING_SECURITY_ROLE_MARKER + RoleEnum.UNVERIFIED));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(username, that.username) && Objects.equals(password,
                that.password) && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, authorities);
    }

    @Override
    public String toString() {
        return "UserDetailsImpl{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
