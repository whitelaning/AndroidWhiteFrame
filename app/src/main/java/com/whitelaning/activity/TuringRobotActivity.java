package com.whitelaning.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.whitelaning.Http.TuringRobotHttpUtils;
import com.whitelaning.adapter.TuringRobotChatMessageAdapter;
import com.whitelaning.bean.ChatMessageBean;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;
import com.whitelaning.whiteframe.tool.InputMethodTool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TuringRobotActivity extends BaseActivity {
    private static final String TAG = "TuringRobotActivity";

    private ListView list_msg;
    private Button send_msg;
    private EditText edit_msg;

    private List<ChatMessageBean> datas;
    private TuringRobotChatMessageAdapter turingRobotChatMessageAdapter;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            ChatMessageBean fromMessge = (ChatMessageBean) msg.obj;
            datas.add(fromMessge);
            turingRobotChatMessageAdapter.notifyDataSetChanged();
            list_msg.setSelection(datas.size() - 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turing_robot);
        initView();
        initDatas();
        initListener();
    }

    private void initListener() {
        edit_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    send_msg.setEnabled(false);
                } else {
                    send_msg.setEnabled(true);
                }
            }
        });

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg = edit_msg.getText().toString();
                ChatMessageBean toMessage = new ChatMessageBean();
                toMessage.setDate(new Date());
                toMessage.setMsg(edit_msg.getText().toString());
                toMessage.setType(ChatMessageBean.Type.OUTCOMING);

                datas.add(toMessage);
                turingRobotChatMessageAdapter.notifyDataSetChanged();

                list_msg.setSelection(datas.size() - 1);

                edit_msg.setText("");

                new Thread() {
                    public void run() {
                        ChatMessageBean fromMessage = TuringRobotHttpUtils.sendMessage(toMsg);
                        Message msg = Message.obtain();
                        msg.obj = fromMessage;
                        mHandler.sendMessage(msg);
                    }
                }.start();
                InputMethodTool.closeInputMethod(TuringRobotActivity.this);
            }
        });
    }

    private void initDatas() {
        datas = new ArrayList();
        datas.add(new ChatMessageBean("大人！您总算来了！", ChatMessageBean.Type.INCOMING, new Date()));
        turingRobotChatMessageAdapter = new TuringRobotChatMessageAdapter(this, datas);
        list_msg.setAdapter(turingRobotChatMessageAdapter);
    }

    private void initView() {
        list_msg = (ListView) findViewById(R.id.list_msg);
        edit_msg = (EditText) findViewById(R.id.edit_msg);
        send_msg = (Button) findViewById(R.id.send_msg);
    }
}
