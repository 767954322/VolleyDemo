package com.test.demo.volleydemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/***
 * 用到过的两种请求模式(目前设计家的借口未通，换个适用的接口即可)
 * 1:后台返回数据如果为String的话使用设计家
 * 2:后台返回数据如果为JSONObject的话使用设计家
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String userInfo;
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

                getDataFromSJJ();

                break;
        }
    }


    private void getDataFromJT() {

        OkStringRequest.OKResponseCallback callback = new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "家图请求失败");
            }

            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.arg1 = 0;
                message.obj = response;
                handler.sendMessage(message);
            }
        };

        MyHttpManager1.getInstance().userLogin("citylist", callback);

    }


    private void getDataFromSJJ() {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "设计家请求失败");
            }

            @Override
            public void onResponse(JSONObject response) {
                Message message = new Message();
                message.arg1 = 1;
                message.obj = jsonToString(response);
                handler.sendMessage(message);
            }
        };
        //构建参数JSONObject
        String tabMd5String = Md5Util.getMD5twoTimes("test" + "e6fd0592c&j2p*&y?@+i#=%m203029ce");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("types", "citylist");
            jsonObject.put("sign", "test," + tabMd5String);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpManager2.getInstance().userLogin(jsonObject, callback);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content = (String) msg.obj;
            String tag = (msg.arg1 == 0 ? " 家图" : " 设计家");
            tv_content.setText(tag + " ：" + content);
        }
    };


    public static String jsonToString(org.json.JSONObject jsonObject) {
        try {
            if (null != jsonObject) {
                userInfo = new String(jsonObject.toString().getBytes("ISO-8859-1"), "UTF-8");
            } else {
                userInfo = "";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
