package com.lgl.qidian.entity.tobe_writer_contoller;

/**
 * @auther 刘广林
 */
public class ToBeWriterBody {

    long userId;
    String userPhone;
    String userAddress;
    String chineseId;
    String userSex;
    int userAge;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
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

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

}
