package com.example.lisongyang.handlerthreadpractice;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建对象，并启动该线程
        ChildThread ht = new ChildThread("testHandlerThread");
        ht.start();

        // 获取Looper对象
        MyHandler handler = new MyHandler(ht.getLooper());
        // 创建Bundle对象，传递数据
        Bundle bundle = new Bundle();
        bundle.putString(KEY, "我有点事情！");
        // 构建消息并发送
        Message msg = new Message();
        msg.what = 1;
        msg.setData(bundle);
        msg.setTarget(handler);
        msg.sendToTarget();

        Log.w("TAG","Activity："+Thread.currentThread().getName()+"::"+Thread.currentThread().getId());

    }

    class MyHandler extends Handler {

        public MyHandler() {
        }

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Bundle bundle = msg.getData();
                // 接收消息
                String msgStr = bundle.getString(KEY);
                Log.w("TAG","Handler："+Thread.currentThread().getName()+"::"+Thread.currentThread().getId());

                Log.d("TAG", "我接受任务："+msgStr);
            }
        }
    }

    class ChildThread extends HandlerThread {

        public ChildThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            Log.i("TAG","执行子线程任务！");
            super.run();
        }
    }
}
