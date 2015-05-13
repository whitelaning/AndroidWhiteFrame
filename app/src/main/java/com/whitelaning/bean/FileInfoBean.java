package com.whitelaning.bean;

import java.io.Serializable;

public class FileInfoBean implements Serializable {
    public static final String TAG = "FileInfoBean";
    private int id;
    private String url;
    private String name;
    private int length;
    private int finished;

    public FileInfoBean() {
       new FileInfoBean(0, "", "", 0, 0);
    }

    public FileInfoBean(int id, String url, String name, int length, int finished) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfoBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", finished=" + finished +
                '}';
    }
}
