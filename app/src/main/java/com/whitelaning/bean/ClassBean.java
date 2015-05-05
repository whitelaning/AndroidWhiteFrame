package com.whitelaning.bean;

public class ClassBean {
    private String title;
    private Class mClass;

    public ClassBean(String title, Class mClass) {
        this.title = title;
        this.mClass = mClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getmClass() {
        return mClass;
    }

    public void setmClass(Class mClass) {
        this.mClass = mClass;
    }
}
