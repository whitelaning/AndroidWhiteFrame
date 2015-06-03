package com.whitelaning.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.whitelaning.bean.ChatMessageBean;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.adapter.WhiteAdapter;
import com.whitelaning.whiteframe.tool.DateFormatTool;
import com.whitelaning.whiteframe.util.WhiteViewHolder;

import java.util.List;

public class TuringRobotChatMessageAdapter extends WhiteAdapter {
    private static final String TAG = "TuringRobotChatMessageAdapter";

    public TuringRobotChatMessageAdapter(Context context, List<ChatMessageBean> datas) {
        super(context,datas);
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageBean chatMessage = (ChatMessageBean) list.get(position);
        if (chatMessage.getType() == ChatMessageBean.Type.INCOMING) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public void initialize(WhiteViewHolder holder, Object item, int position) {
    }

    private void initializeFormMsg(WhiteViewHolder holder, Object item, int position) {
        ChatMessageBean chatMessageBean = (ChatMessageBean) item;
        holder.setText(R.id.from_msg_info, chatMessageBean.getMsg());
        holder.setText(R.id.form_msg_date, DateFormatTool.getFormatDate(chatMessageBean.getDate()));
    }

    private void initializeTomMsg(WhiteViewHolder holder, Object item, int position) {
        ChatMessageBean chatMessageBean = (ChatMessageBean) item;
        holder.setText(R.id.to_msg_info, chatMessageBean.getMsg());
        holder.setText(R.id.to_msg_date, DateFormatTool.getFormatDate(chatMessageBean.getDate()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessageBean chatMessageBean = (ChatMessageBean) list.get(position);

        WhiteViewHolder whiteViewHolder;

        // 通过ItemType设置不同的布局
        if (chatMessageBean.getType() == ChatMessageBean.Type.INCOMING) {
            whiteViewHolder = WhiteViewHolder.getInstance(context, convertView, parent, R.layout.adapter_turing_robot_chat_message_item_from_msg, position);
            initialize(whiteViewHolder, getItem(position), position);
            initializeFormMsg(whiteViewHolder, getItem(position), position);
        } else {
            whiteViewHolder = WhiteViewHolder.getInstance(context, convertView, parent, R.layout.adapter_turing_robot_chat_message_item_to_msg, position);
            initialize(whiteViewHolder, getItem(position), position);
            initializeTomMsg(whiteViewHolder, getItem(position), position);
        }

        return whiteViewHolder.getConvertView();
    }
}
