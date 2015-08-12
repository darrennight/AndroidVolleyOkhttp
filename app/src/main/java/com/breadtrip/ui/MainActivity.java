package com.breadtrip.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.breadtrio.sdk.common.utils.ToastUtils;
import com.breadtrip.Http.RequestParams;
import com.breadtrip.R;
import com.breadtrip.config.PathConfig;
import com.breadtrip.sdk.http.HttpManager;
import com.breadtrip.sdk.http.tool.HttpResponseListener;
import com.google.gson.annotations.SerializedName;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testHttp();
    }

    private void testHttp() {
        HttpManager.getInstance().initiate(getApplicationContext(), PathConfig.HTTP_CACHE);
        RequestParams params = new RequestParams.Builder("http://api.breadtrip.com/v2/test/", Demo.class).create();

        HttpManager.getInstance().startRequest(params, new HttpResponseListener<Demo>() {

            @Override
            public void onSuccess(@NonNull Demo result) {
                ToastUtils.ToastShort(MainActivity.this, result.data);
            }

            @Override
            public void onFailure(String errorCode, String errorMsg) {
                ToastUtils.ToastShort(MainActivity.this, errorCode + " " + errorMsg);
            }
        });
    }

    class Demo{
        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @SerializedName("default-data")
        private String data;
    }

}
