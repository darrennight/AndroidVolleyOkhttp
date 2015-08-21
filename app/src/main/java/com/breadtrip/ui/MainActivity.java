package com.breadtrip.ui;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.annotation.JSONField;
import com.breadtrip.Http.RequestParams;
import com.breadtrip.R;
import com.breadtrip.config.PathConfig;
import com.breadtrip.sdk.http.HttpManager;
import com.breadtrip.sdk.http.tool.HttpResponseListener;
import com.breadtrip.sdk.image.ImageResponseListener;
import com.breadtrip.sdk.image.ImageVolleyManager;
import com.breadtrip.sdk.image.LocalImageView;
import com.breadtrip.sdk.image.NetImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testHttp();

        testVolleyImage();
    }


    private void testVolleyImage() {
        ImageVolleyManager imageVolleyManager = ImageVolleyManager.getInstance();
        imageVolleyManager.initiate(this, PathConfig.IMAGE_CACHE);
        String url = "http://att.x2.hiapk.com/forum/month_1008/100804175235a96c931557db2c.png.thumb.jpg";
        String url2 = "http://att.x2.hiapk.com/forum/month_1008/100804175226afc710d3027566.jpg.thumb.jpg";

        NetImageView netImageView = (NetImageView) findViewById(R.id.netimage);
        netImageView.setImageUrl(url);

        final ImageView netImageView2 = (ImageView) findViewById(R.id.netimage2);
        imageVolleyManager.startImageRequest(url2, 0, 0, new ImageResponseListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                netImageView2.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure() {

            }
        });

        String[] photoPath = getPhotoPath();
        LocalImageView localImageView = (LocalImageView) findViewById(R.id.localimage1);
        localImageView.setImagePath(photoPath[0]);
        final ImageView localImageView2 = (ImageView) findViewById(R.id.localimage2);
        imageVolleyManager.startLocalImageRequest(photoPath[1], 0, 0, new ImageResponseListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                localImageView2.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure() {

            }
        });

//        imageVolleyManager.stop();
    }

    private void testHttp() {
        final TextView tv = (TextView) findViewById(R.id.text);
        RequestParams params = new RequestParams.Builder("http://api.breadtrip.com/v2/test/").setParseTag(Demo.class).create();
        HttpManager.getInstance().startRequest(params, new HttpResponseListener<Demo>() {

            @Override
            public void onSuccess(@NonNull Demo result) {
                tv.setText(result.data);
            }

            @Override
            public void onFailure(String errorCode, String errorMsg) {
                tv.setText(errorCode + " " + errorMsg);
            }
        });
    }

    private String[] getPhotoPath() {
        String[] path = new String[2];
        //指定获取的列
        String columns[] = new String[]{
                Media.DATA, Media._ID, Media.TITLE, Media.DISPLAY_NAME
        };
        Cursor cursor = this.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
        int photoIndex = cursor.getColumnIndexOrThrow(Media.DATA);
        int nameIndex = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME);

        if (cursor.moveToFirst()) {
            path[0] = cursor.getString(photoIndex);

            if (cursor.moveToNext()) {
                path[1] = cursor.getString(photoIndex);
            }
        }
        return path;
    }

    static class Demo {
        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @JSONField(name = "default-data")
        private String data;
    }

}
