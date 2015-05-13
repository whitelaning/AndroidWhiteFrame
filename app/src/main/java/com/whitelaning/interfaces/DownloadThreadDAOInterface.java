package com.whitelaning.interfaces;

import com.whitelaning.bean.DownloadThreadInfoBean;

import java.util.List;

/**
 * Download线程数据访问接口
 */
public interface DownloadThreadDAOInterface {
    public void insertThread(DownloadThreadInfoBean threadInfo);
    public void deleteThread(String url);
    public void updateThread(String url, int thread_id,int finished);
    public List<DownloadThreadInfoBean> getThreads(String url);
    public boolean IsExistsThread(String url, int thread_id);
}

