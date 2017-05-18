package com.test.demo.volleydemo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author allen .
 * @version v1.0 .
 * @date 2017-2-24.
 * @file MyHttpManager1.java .
 * @brief 请求工具类 .
 */
public class MyHttpManager2 {

    private final String URL_SERVICE = "http://sjj.idcool.com.cn/filter";

    private static MyHttpManager2 mpServerHttpManager = new MyHttpManager2();
    private RequestQueue queue = MyApplication.getInstance().queue;

    private MyHttpManager2() {
    }

    public static MyHttpManager2 getInstance() {

        return mpServerHttpManager;
    }

    /**
     * 设计家用法
     *
     * @param jsonObject
     * @param callback
     */
    public void userLogin(JSONObject jsonObject,
                          OkJsonRequest.OKResponseCallback callback) {
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.POST, URL_SERVICE, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(okRequest);
    }

}
