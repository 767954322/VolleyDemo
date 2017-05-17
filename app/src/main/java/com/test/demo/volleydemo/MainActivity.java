package com.test.demo.volleydemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_jiatu;
    private Button bt_shejijia;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        initData();

    }


    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        bt_jiatu = (Button) findViewById(R.id.bt_jiatu);
        bt_shejijia = (Button) findViewById(R.id.bt_shejijia);
    }

    private void initListener() {
        bt_jiatu.setOnClickListener(this);
        bt_shejijia.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_jiatu:

                getDataFromJT();

                break;
            case R.id.bt_shejijia:

                break;
        }
    }

    private void getDataFromJT() {

        OkStringRequest.OKResponseCallback callback = new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.obj = response;
                handler.sendMessage(message);
            }
        };

        MyHttpManager1.getInstance().userLogin("citylist", callback);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content = (String) msg.obj;
            tv_content.setText(content);
        }
    };

}
