package com.chat.hechat.chat;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chat.hechat.R;
import com.chat.hechat.login.MainActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatFragment extends Fragment {
    private Button sendButton;
    private EditText editText;
    private ListView messageList;
    private ChatMsgViewAdapter msgAdapter;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<>();
    OkHttpClient okHttpClient = new OkHttpClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        View view =inflater.inflate(R.layout.activity_chat,container,false);
        initView(view);
        initData();
        messageList.setSelection(msgAdapter.getCount() - 1);
        return view;
    }


    public void initView(View v) {
        messageList = v.findViewById(R.id.list_chatmessage);
        sendButton = v.findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        editText = v.findViewById(R.id.text_message);
    }

    private String[] msgArray = new String[] { "你有钱买服务器吗", "没有", "那和机器人交流吧", "好吧", };

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

        msgAdapter = new ChatMsgViewAdapter(this.getContext(), mDataArrays);
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


            Request request = new Request.Builder()
                    .url("http://sandbox.api.simsimi.com/request.p?key=927101ec-1cef-4584-b084-604d76be8cba&lc=ch&ft=1.0&text="+editText.getText().toString())
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("triggered");
                    System.out.println(e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("triggered");
                    final String res = response.body().string();
                    final  BotResponse br = JSON.parseObject(res,BotResponse.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(br!=null && br.result.equals("100")){
                                ChatMsgEntity entity = new ChatMsgEntity();
                                entity.setName("巫炎静");
                                entity.setDate(getDate());
                                entity.setMessage(br.response);
                                entity.setMsgType(true);
                                mDataArrays.add(entity);
                                msgAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变
                            }
                            System.out.println(br);
                        }
                    });


                }
            });



            editText.setText("");// 清空编辑框数据

            messageList.setSelection(messageList.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
        }

    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

}
