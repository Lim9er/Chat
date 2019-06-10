package com.chat.hechat.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.chat.hechat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private Button sendButton;
    private EditText editText;
    private ListView messageList;
    private ChatMsgViewAdapter msgAdapter;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
        messageList.setSelection(msgAdapter.getCount() - 1);
    }

    public void initView() {
        messageList = findViewById(R.id.list_chatmessage);
        sendButton = findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        editText = findViewById(R.id.text_message);
    }

    private String[] msgArray = new String[] { "你好吗", "我很好", "那就好", "好吧", };

    private String[] dataArray = new String[] { "2012-09-22 18:00:02",
            "2012-09-22 18:10:22", "2012-09-22 18:11:24",
            "2012-09-22 18:20:23",  };
    private final static int COUNT = 4;


    public void initData() {
        for (int i = 0; i < COUNT; i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0) {
                entity.setName("巫炎静");
                entity.setMsgType(true);
            } else {
                entity.setName("柳博文");
                entity.setMsgType(false);
            }
            entity.setMessage(msgArray[i]);
            mDataArrays.add(entity);
        }

        msgAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        messageList.setAdapter(msgAdapter);
    }

    private void send() {
        String contString = editText.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName("柳博文");
            entity.setDate(getDate());
            entity.setMessage(contString);
            entity.setMsgType(false);

            mDataArrays.add(entity);
            msgAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

            editText.setText("");// 清空编辑框数据

            messageList.setSelection(messageList.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
        }
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

}
