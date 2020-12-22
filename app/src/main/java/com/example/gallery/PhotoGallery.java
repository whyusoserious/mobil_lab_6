package com.example.gallery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.api.FlickrAPI;
import com.example.gallery.api.ServiceAPI;
import com.example.gallery.db.PhotosDAO;
import com.example.gallery.db.PhotosDB;
import com.example.gallery.model.Photo;
import com.example.gallery.model.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class PhotoGallery extends AppCompatActivity {
    private final List<Photo> photos = new ArrayList<>();
    private final PhotoAdapter adapter = new PhotoAdapter(photos);
    private PhotosDAO dao;
    private final FlickrAPI flickrAPI = ServiceAPI.getRetrofit().create(FlickrAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        dao = PhotosDB.getDatabase(getApplicationContext()).photosDAO();
        final RecyclerView rv = findViewById(R.id.recView);
        rv.setLayoutManager(new GridLayoutManager(this, 3));

        flickrAPI.getRecent().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                photos.addAll(response.body().getPhotos().getPhoto());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(PhotoGallery.this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Something went wrong!");
                alertDialog.show();
            }
        });
        rv.setAdapter(adapter);
    }
}