package com.lgl.qidian.entity.tobe_writer_contoller;

/**
 * @auther 刘广林
 */
public class AdjudicationWriterDo {

    private Long userId;

    private Long adminId;

    private String isAck;

    public AdjudicationWriterDo() {
    }

    public AdjudicationWriterDo(long userId, long adminId, String isAck) {
        this.userId = userId;
        this.adminId = adminId;
        this.isAck = isAck;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getIsAck() {
        return isAck;
    }

    public void setIsAck(String isAck) {
        this.isAck = isAck;
    }
}
