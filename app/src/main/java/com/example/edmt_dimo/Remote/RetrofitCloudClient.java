package com.example.edmt_dimo.Remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCloudClient {
    private static Retrofit instance;
    public static Retrofit getInstance(){
        if (instance==null)
            instance=new Retrofit.Builder()
                    .baseUrl("https://us-centrall-eatitv2-e8a23.cloudfunctions.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return instance;

    }
}
