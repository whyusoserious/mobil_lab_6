package com.example.gallery.api;


import com.example.gallery.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface FlickrAPI {
    @GET("services/rest/?method=flickr.photos.getRecent&api_key=05b2b5ee78a71c712cbd1a79d34dac6f&extras=url_s&format=json&nojsoncallback=1")
    Call<Example> getRecent();
    @GET("services/rest/?method=flickr.photos.search&api_key=05b2b5ee78a71c712cbd1a79d34dac6f&extras=url_s&format=json&nojsoncallback=1")
    Call<Example> getSearchPhotos(@Query("text") String keyWord);

}
