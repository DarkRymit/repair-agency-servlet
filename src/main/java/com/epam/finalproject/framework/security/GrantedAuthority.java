package com.epam.finalproject.framework.security;

import java.io.Serializable;

public interface GrantedAuthority extends Serializable {
    String getAuthority();
}
