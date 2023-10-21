package com.lgl.qidian.entity;


/**
 * @auther 刘广林
 */
public class UserDetailsDo {
    private Long userId;
    private String userPhone;
    private String userAdress;
    private String chineseId;
    private String userSex;
    private Short user_age;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }

    public String getChineseId() {
        return chineseId;
    }

    public void setChineseId(String chineseId) {
        this.chineseId = chineseId;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Short getUser_age() {

        return user_age;
    }

    public void setUser_age(Short user_age) {
        this.user_age = user_age;
    }
}
