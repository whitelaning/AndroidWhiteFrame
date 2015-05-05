package com.whitelaning.whiteframe.bean;

/**
 * Created by Administrator on 2015/5/4.
 */
public class Bean {
    private String title;
    private String content;
    private String time;

    private Bean() {
        //私有化空构造函数
    }

    public Bean(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
