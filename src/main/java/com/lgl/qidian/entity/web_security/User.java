package com.lgl.qidian.entity.web_security;

import org.springframework.security.core.GrantedAuthority;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


/**
 * @auther 刘广林
 */
public class User {

    public long userId;
    public String userPassword;
    public String userName;
    public Timestamp createTime;
    public Timestamp durationLock;
    public Timestamp lockTime;
    public boolean isLock;
    public List<? extends GrantedAuthority> grantedAuthorities;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getDurationLock() {
        return durationLock;
    }

    public void setDurationLock(Timestamp durationLock) {
        this.durationLock = durationLock;
    }

    public Timestamp getLockTime() {
        return lockTime;
    }

    public void setLockTime(Timestamp lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(List<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", drutionLock=" + durationLock +
                ", lockTime=" + lockTime +
                ", isNLock=" + isLock +
                ", grantedAuthorities=" + grantedAuthorities +
                '}';
    }
}
