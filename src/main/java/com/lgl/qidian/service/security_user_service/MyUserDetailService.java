package com.lgl.qidian.service.security_user_service;

import com.lgl.qidian.entity.web_security.MyUserDetail;
import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.entity.web_security.User_Role;
import com.lgl.qidian.mapper.UserMapper;
import com.lgl.qidian.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther 刘广林
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUserId(Long.valueOf(username));
        if (user == null){
            return null;
        }
        List<User_Role> user_roles = userRoleMapper.selectRoleByUserId(user.getUserId());
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = user_roles.stream().map(
                user_role -> {
                    String role = "";
                    switch(user_role.getRole_id()){
                        case 0: role = "admin"; break;
                        case 1: role = "user"; break;
                    }
                    return new SimpleGrantedAuthority(role);
                }
        ).collect(Collectors.toList());
        user.setGrantedAuthorities(simpleGrantedAuthorities);
        MyUserDetail myUserDetail = new MyUserDetail();
        myUserDetail.setUser(user);
        return myUserDetail;
    }
}
