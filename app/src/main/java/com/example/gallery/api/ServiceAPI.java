package com.example.gallery.api;

import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import com.google.gson.Gson;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceAPI {
    private static final String BASE_URL="https://api.flickr.com/";
    private static Retrofit retrofit = null;
    private static FlickrAPI flickrAPI;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return retrofit;
        }
        return retrofit;
    }

}
