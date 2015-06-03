package com.whitelaning.bean;

import java.util.Date;

public class ChatMessageBean {
    private String msg;//消息内容
    private Type type;
    private Date date;

    /**
     * 消息类别
     * 1.INCOMING 返回的消息
     * 2.OUTCOMING 发送出去的消息
     */
    public enum Type {
        INCOMING, OUTCOMING
    }

    public ChatMessageBean() {
    }

    public ChatMessageBean(String msg, Type type, Date date) {
        super();
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
