package com.whitelaning.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;
import com.whitelaning.whiteframe.tool.LogTool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "AsyncTaskActivity";

    private Button execute;
    private Button cancel;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private TextView textView;

    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTool.i(TAG, "onCreate()");
        setContentView(R.layout.activity_async_task);
        initView();
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        LogTool.i(TAG, "initListener()");
        execute.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    /**
     * 初始化View控件
     */
    private void initView() {
        LogTool.i(TAG, "initView()");
        execute = (Button) findViewById(R.id.execute);
        cancel = (Button) findViewById(R.id.cancel);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        LogTool.i(TAG, "onClick(View v)");
        switch (v.getId()) {
            case R.id.execute:
                //更改按钮状态
                execute.setEnabled(false);
                cancel.setEnabled(true);

                myAsyncTask = new MyAsyncTask();//每次都需要新实例化，任务只能执行一次，否则会抛出异常
                myAsyncTask.execute("http://www.baidu.com");

                break;
            case R.id.cancel:
                myAsyncTask.cancel(true); //取消一个正在执行的任务,onCancelled方法将会被调用
                break;
            default:
                break;
        }
    }

    /**
     * 异步任务内部类
     */
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            LogTool.i(TAG, "MyAsyncTask.onPreExecute()");
            //执行后台任务前做的一些操作
            textView.setText("loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            LogTool.i(TAG, "MyAsyncTask.doInBackground()");
            //执行后台任务
            //注意：此处运行于后台线程，不可以进行主线程的操作，例如UI修改

            HttpURLConnection httpURLConnection = null; //连接对象
            InputStream inputStream = null;//输入流
            String resultData = "";//返回数据
            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);//允许输入流，即允许下载,默认为真
                httpURLConnection.setDoOutput(true);//允许输出流，即允许上传，没有默认值
                httpURLConnection.setUseCaches(false);//不使用緩存
                httpURLConnection.setRequestMethod("GET");//使用GET請求
                httpURLConnection.setRequestProperty("Accept-Encoding", "identity"); //设置请求头信息，不设置那么getContentLength()可能为-1，但不影响下载

                inputStream = httpURLConnection.getInputStream(); //獲取输入流
                long cntentLength = httpURLConnection.getContentLength();//获取全文长度

                //新建一个byte型别数组的缓冲区，存储内存缓冲区的数据，便于一次性转换出去
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];//一次读取1024个字节
                int count = 0;//当前精度记数
                int length;//读取到的长度，-1为结尾标识

                //遍历读取数据
                while ((length = inputStream.read(buf)) != -1) {
                    baos.write(buf, 0, length);//缓存数据
                    count += length; //累计下载的长度
                    //调用publishProgress公布进度,onProgressUpdate方法将被执行
                    LogTool.i(TAG,"count = " + count);
                    LogTool.i(TAG,"cntentLength = " + cntentLength);
                    LogTool.i(TAG,"count / (float) cntentLength = " + count / (float) cntentLength);
                    publishProgress((int) ((count / (float) cntentLength) * 100)); // 用当前进度和总长度计算百分比
                    Thread.sleep(500); //休眠500毫秒,显示进度
                }

                return new String(baos.toByteArray(), "UTF-8"); //返回UTF-8的字符串

            } catch (Exception e) {
                LogTool.e(TAG, "MyAsyncTask.doInBackground().catch = " + e.getMessage());
            } finally { //在finally里进行流的关闭

                if (inputStream != null) {
                    try {
                        inputStream.close(); //关闭输入流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (httpURLConnection != null) {
                    httpURLConnection.disconnect(); //关闭连接
                }
            }

            return null; //失败返回空
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //更新进度信息
            LogTool.i(TAG, "MyAsyncTask.onProgressUpdate()");

            int value = values[0];
            if(value < 0) {
                LogTool.e(TAG, "MyAsyncTask.onProgressUpdate() Error!");
                LogTool.e(TAG, "Becase by : value[0] < 0 ");
            } else {
                progressBar.setProgress(value); //更新进度条
                textView.setText("loading...  " + value + "%");
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //执行完后调用,运行在主线程
            LogTool.i(TAG, "MyAsyncTask.onPostExecute()");

            textView.setText("Finished\n\n" + result); //最终显示的内容

            //初始化按钮状态
            execute.setEnabled(true);
            cancel.setEnabled(false);
        }

        @Override
        protected void onCancelled() {
            //执行cancel(true)（取消任务）后调用
            LogTool.i(TAG, "MyAsyncTask.onCancelled()");

            textView.setText("Cancelled");

            //初始化View和Data
            progressBar.setProgress(0);
            execute.setEnabled(true);
            cancel.setEnabled(false);
        }
    }
}
