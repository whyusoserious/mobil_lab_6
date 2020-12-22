package com.example.gallery;

import android.app.SearchManager;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.app.SearchManager;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
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
        adapter.setOnLongClickListener(photo -> {
            dao.insertPhoto(photo);

            AlertDialog alertDialog = new AlertDialog.Builder(PhotoGallery.this).create(); //Read Update
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Pictures saved!");
            alertDialog.show();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();

        searchView.setOnQueryTextListener(onQueryTextListener);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return true;
    }

    public void onLoadRecentClick(MenuItem item) {
        flickrAPI.getRecent().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                photos.clear();
                photos.addAll(response.body().getPhotos().getPhoto());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(PhotoGallery.this).create(); //Read Update
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Something went wrong!");
                alertDialog.show();
            }
        });
    }

    public void onLoadGalleryClick(MenuItem item) {
        photos.clear();
        photos.addAll(dao.LoadAll());
        adapter.notifyDataSetChanged();
    }

    protected SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            flickrAPI.getSearchPhotos(query).enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    photos.clear();
                    photos.addAll(response.body().getPhotos().getPhoto());
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    AlertDialog alertDialog = new AlertDialog.Builder(PhotoGallery.this).create(); //Read Update
                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("Something went wrong!");
                    alertDialog.show();
                }
            });
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }
    };
    public void onLoadNewClick(MenuItem item) {}
}


        /*Intent intent = getIntent();
        intent.getAction();
        String c = intent.getStringExtra(SearchManager.QUERY);
        boolean x = Intent.ACTION_SEARCH.equals(intent.getAction());
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        SearchView searchView = (SearchView) .findItem(R.id.search_bar)
            String query = SearchManager.QUERY;
            earchView sv = findViewById(R.id.search_bar);
            String Query = sv.getQuery().toString();

       }*/