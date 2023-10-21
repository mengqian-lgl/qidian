package com.lgl.qidian.entity.web_security;

/**
 * @auther 刘广林
 */
public class User_Role {
    public long user_id;
    public short role_id;

    public User_Role() {
    }

    public User_Role(long user_id, short role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public short getRole_id() {
        return role_id;
    }

    public void setRole_id(short role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "User_Role{" +
                "user_id=" + user_id +
                ", role_id=" + role_id +
                '}';
    }
}
