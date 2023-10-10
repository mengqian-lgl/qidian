package com.lgl.qidian;

import org.springframework.security.core.GrantedAuthority;

import java.util.*;

/**
 * @auther 刘广林
 */
class User {

    public String userId = "123";
    public String password = "123";
    public Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();


}
