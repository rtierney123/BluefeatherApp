package com.adafruit.bluefruit.le.connect.http;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpPostAsyncTask extends AsyncTask<String, Void, Void> {

    public static CookieJar cookies;
    private static String mMainURL;
    protected String mResponse;
    public RequestBody mUploadFile;

    // This is a constructor that allows you to pass in the JSON body
    public HttpPostAsyncTask(Map<String, String> postData) {

    }
    @Override
    protected Void doInBackground(String... params) {
        int count = params.length;

        CookieJar cookieJar = new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
            @Override public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            } @Override public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>(); }
        };
        cookies = cookieJar;
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(mMainURL+params[0]).newBuilder();
        RequestBody formBody = null;
        for (int i = 0; i < count; i++){
            switch(params[i]){
                //change to real end url
                case "/csv" : formBody =  new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("csv", "filename", mUploadFile)
                        .build();
                    break;


            }
            if (formBody != null){
                Request request = new Request.Builder()
                        .url( mMainURL+params[0])
                        .post(formBody)
                        .build();
                // Response response = client.newCall(request).execute();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //some sort of Toast here
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mResponse=response.body().string();
                        Log.d("ok", mResponse);
                        //toast here to say response was sent
                    }
                });

            }
        }



        return null;
    }



    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    String getResponse(){
        return mResponse;
    }
    public void setmUploadFile(RequestBody file){
        mUploadFile = file;
    }
}
