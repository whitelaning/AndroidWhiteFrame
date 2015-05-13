package com.whitelaning.Util;

import android.content.Context;
import android.content.Intent;

import com.whitelaning.bean.DownloadThreadInfoBean;
import com.whitelaning.bean.FileInfoBean;
import com.whitelaning.db.DownloadThreadDAO;
import com.whitelaning.services.DownloadService;
import com.whitelaning.whiteframe.control.PathAddressCollector;
import com.whitelaning.whiteframe.tool.LogTool;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadTask {
    public static final String TAG = "DownloadTask";
    private Context context;
    private FileInfoBean fileInfo;
    private DownloadThreadDAO dao;
    //    private int finished;
    private boolean isPause;
    private int threadCount = 1;//线程数量

    private List<DownloadThread> downloadThreadList = new ArrayList();

    public DownloadTask(Context context, FileInfoBean fileInfo) {
        new DownloadTask(context, fileInfo, 1);
    }

    public DownloadTask(Context context, FileInfoBean fileInfo, int threadCount) {
        this.context = context;
        this.fileInfo = fileInfo;
        this.threadCount = threadCount;
        dao = new DownloadThreadDAO(context);
        isPause = false;
    }

    public void download() {
        LogTool.i(TAG, "download()");
        //读取数据库线程信息
        List<DownloadThreadInfoBean> threadInfos = dao.getThreads(fileInfo.getUrl());

        int threadInfosSize = threadInfos.size();
        LogTool.i(TAG, "threadInfosSize = " + threadInfosSize);
        if (threadInfosSize <= 0) {
            int length = fileInfo.getLength() / threadCount;
            for (int i = 0; i < threadCount; i++) {
                DownloadThreadInfoBean threadInfo = new DownloadThreadInfoBean(i, fileInfo.getUrl(), length * i, (i + 1) * length - 1, 0);
                if (i == threadCount - 1) {
                    threadInfo.setEnd(fileInfo.getLength());
                }

                DownloadThread downloadThread = new DownloadThread(threadInfo);
                downloadThread.start();
                downloadThreadList.add(downloadThread);
                dao.insertThread(threadInfo);
            }
        } else {

            for (DownloadThreadInfoBean threadInfo : threadInfos) {
                DownloadThread downloadThread = new DownloadThread(threadInfo);
                downloadThread.start();
                downloadThreadList.add(downloadThread);
            }
        }
    }

    /**
     * 判断所有线程是否下载完毕
     */
    private synchronized void checkAllThreadsFinished() {
        boolean allFinished = true;
        for (DownloadThread thread : downloadThreadList) {
            if (!thread.isFinished) {
                allFinished = false;
                break;
            }
        }

        if (allFinished) {

            dao.deleteThread(fileInfo.getUrl());//删除线程信息

            Intent intent = new Intent(DownloadService.ACTION_FINISH);
            intent.putExtra("id", fileInfo.getId());
            context.sendBroadcast(intent);
        }
    }

    public void isPause(boolean isPause) {
        this.isPause = isPause;
    }

    class DownloadThread extends Thread {
        private DownloadThreadInfoBean threadInfo;

        public boolean isFinished = false;//标识线程是否下载结束

        public DownloadThread(DownloadThreadInfoBean threadInfo) {
            this.threadInfo = threadInfo;
        }

        public void run() {
            /**
             * 1.数据库插入线程信息
             * 2.设置下载位置
             * 3.设置文件写入位置
             * 4.开始下载
             */
            LogTool.i(TAG, "DownloadThread.run()");
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream inputStream = null;

            Intent intent = new Intent(DownloadService.ACTION_UPDATE);
            intent.putExtra("finished", threadInfo.getFinished());
            intent.putExtra("id", fileInfo.getId());
            context.sendBroadcast(intent);

            try {
                URL url = new URL(threadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");//从服务器下载文件用GET方法，其他用POST方法。

                int start = threadInfo.getStart() + threadInfo.getFinished();
                int end = threadInfo.getEnd();
                LogTool.i(TAG, "DownloadThread.run().start = " + start);
                LogTool.i(TAG, "DownloadThread.run().end = " + end);

                conn.setRequestProperty("Range", "bytes=" + start + "-" + end);//设置文件标记下载位置

                File file = new File(PathAddressCollector.DOWNLOAD_PATH, fileInfo.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);//跳过多少个字节数开始读写，不包括当前数，例：seek(250)，即跳过250个字节，从251个字节位置开始读写

                /**
                 * 1.开始下载
                 * 2.读取数据
                 * 3.写入文件
                 * 4.在下载暂停时，保持下载进度
                 * 5.把下载进度通过广播发送到Activity
                 */

                if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                    LogTool.i(TAG, "conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT");
                    intent = new Intent(DownloadService.ACTION_UPDATE);
                    inputStream = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = -1;
                    long time = System.currentTimeMillis();
                    int changelength = 0;
                    while ((length = inputStream.read(buffer)) != -1) {
                        raf.write(buffer, 0, length);//写入文件
                        threadInfo.setFinished(threadInfo.getFinished() + length);
                        changelength += length;
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            intent.putExtra("changelength", changelength);
                            intent.putExtra("id", fileInfo.getId());
                            intent.putExtra("length", fileInfo.getLength());
                            context.sendBroadcast(intent);
                            changelength = 0;
                        }

                        if (isPause) {
                            dao.updateThread(threadInfo.getUrl(), threadInfo.getId(), threadInfo.getFinished());
                            return;
                        }
                    }
                    isFinished = true;
                    checkAllThreadsFinished();
                    LogTool.i(TAG, "thread Download finish!");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (conn != null) {
                    conn.disconnect();
                }

                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
