package com.whitelaning.Http;

import com.google.gson.Gson;
import com.whitelaning.bean.ChatMessageBean;
import com.whitelaning.bean.ChatMessageResultBean;
import com.whitelaning.consts.KeyConst;
import com.whitelaning.consts.UrlConst;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Date;

public class TuringRobotHttpUtils {

    /**
     * 发送一个消息，得到返回的消息
     *
     * @param msg 消息
     * @return ChatMessageBean
     */
    public static ChatMessageBean sendMessage(String msg) {
        ChatMessageBean chatMessage = new ChatMessageBean(); // 新建一个消息类来存储获取到的消息
        String jsonRes = doGet(msg);//访问接口地址获取返回数据
        Gson gson = new Gson();//新建Gson
        ChatMessageResultBean result;//新建返回数据类
        try {
            //调用Gson的fromJson方法,根据写好的返回数据bean类去解析返回的Json数据
            result = gson.fromJson(jsonRes, ChatMessageResultBean.class);
            chatMessage.setMsg(result.getText());//将返回数据的内容写入消息类中
        } catch (Exception e) {
            chatMessage.setMsg("服务器繁忙，请稍候再试");
        }
        chatMessage.setDate(new Date());//以接收到返回数据的时间作为当前消息的时间
        chatMessage.setType(ChatMessageBean.Type.INCOMING);//设置消息类型为返回的数据
        return chatMessage;//返回这个类型
    }

    public static String doGet(String msg) {
        String result = "";
        String url = setParams(msg); //获取url接口地址
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        //下面就是正常的HttpURLConnection写法，这里不累赘了
        try {
            java.net.URL urlNet = new java.net.URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            is = conn.getInputStream();
            int len;
            byte[] buf = new byte[128];
            baos = new ByteArrayOutputStream();

            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 返回接口Url完整地址
     *
     * @param msg 要发送出去的消息
     * @return url地址
     */
    private static String setParams(String msg) {
        String url = "";
        try {
            url = UrlConst.TURING_ROBOT_URL + "?key=" + KeyConst.TURING_ROBOT_KEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
