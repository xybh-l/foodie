package com.xybh.pojo.vo;

import java.util.Date;

/**
 * @Author: xybh
 * @Description: 用来展示商品评价的VO
 * @Date: Created in 13:54 2021/1/22
 * @Modified:
 */
public class ItemsCommentsVO {
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createTime;
    private String nickname;
    private String userFace;
    private Integer anonymous;

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public Integer getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }
}
