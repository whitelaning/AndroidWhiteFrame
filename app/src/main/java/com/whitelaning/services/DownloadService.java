package com.whitelaning.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.whitelaning.Util.DownloadTask;
import com.whitelaning.bean.DownloadThreadInfoBean;
import com.whitelaning.bean.FileInfoBean;
import com.whitelaning.whiteframe.control.PathAddressCollector;
import com.whitelaning.whiteframe.tool.LogTool;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DownloadService extends Service {
    public static final String TAG = "DownloadService";//该类标识
    public static final String ACTION_START = "action_start";//停止下载服务标识
    public static final String ACTION_STOP = "action_stop";//启动下载服务标识
    public static final String ACTION_UPDATE = "action_update";//启动下载服务标识
    public static final String ACTION_FINISH = "action_finish";//完成下载服务标识
    public static final String ACTION_INIT = "action_init";//初始化文件星系标识
    public static final int MSG_INIT = 0;//初始化信息标记

    private Map<Integer, DownloadTask> downloadTaskMap = new LinkedHashMap();
    private int downloadThreadCount = 3;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionString = intent.getAction();
        if (actionString.equals(DownloadService.ACTION_START)) {
            FileInfoBean fileInfo = (FileInfoBean) intent.getSerializableExtra(FileInfoBean.TAG);
            if (!downloadTaskMap.containsKey(fileInfo.getId())) {
                new InitThread(fileInfo).start();//启动初始化线程
            }
        } else if (actionString.equals(DownloadService.ACTION_STOP)) {
            FileInfoBean fileInfo = (FileInfoBean) intent.getSerializableExtra(FileInfoBean.TAG);
            DownloadTask downloadTask = downloadTaskMap.get(fileInfo.getId());
            if (downloadTask != null) {
                downloadTask.isPause(true);
                downloadTaskMap.remove(fileInfo.getId());
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public DownloadService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfoBean fileInfo = (FileInfoBean) msg.obj;
                    LogTool.i(TAG, "handleMessage(),MSG_INIT,fileInfo.toString = " + fileInfo.toString());
                    DownloadTask downloadTask = new DownloadTask(DownloadService.this, fileInfo, downloadThreadCount);
                    downloadTask.download();
                    downloadTaskMap.put(fileInfo.getId(), downloadTask);
                    break;
                default:
            }
        }
    };

    class InitThread extends Thread {
        private FileInfoBean fileInfo = null;

        public InitThread(FileInfoBean fileInfo) {
            this.fileInfo = fileInfo;
        }

        /**
         * 1.连接网络文件
         * 2.在本地创建文件
         * 3.设置文件长度
         */
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(fileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");//从服务器下载文件用GET方法，其他用POST方法。
                int length = -1;
                if (conn.getResponseCode() == HttpStatus.SC_OK) {
                    length = conn.getContentLength();

                    if (length <= 0) {
                        return;
                    } else {
                        File dir = new File(PathAddressCollector.DOWNLOAD_PATH);

                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        File file = new File(dir, fileInfo.getName());
                        raf = new RandomAccessFile(file, "rwd");//RandomAccessFile，特殊的输出流，能在文件的任意位置写入
                        raf.setLength(length);

                        fileInfo.setLength(length);
                        handler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (raf != null) {
                        raf.close();
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
