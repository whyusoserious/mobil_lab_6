package com.example.gallery;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.*;

import com.example.gallery.model.*;

import java.util.List;

import retrofit2.Callback;
import com.squareup.picasso.*;

public class PhotoAdapter extends RecyclerView.Adapter <PhotoAdapter.ViewHolder> {
    private final Callback<Example> photoGallery;
    private OnInsertListener onInsertListener;
    private final List<Photo> photos;

    public PhotoAdapter(Callback<Example> parent, List<Photo> values ) {
        photoGallery = parent;
        photos = values;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView picture;

        ViewHolder(View view){
            super(view);
            picture = view.findViewById(R.id.img);
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onInsertListener.OnInsert(photos.get(ViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String stringconnetcion;
        stringconnetcion ="https://farm"+ Integer.toString(photos.get(position).getFarm()) +
                ".staticflickr.com/" + photos.get(position).getSecret() + "_q.jpg";
        Picasso.get().load(stringconnetcion).into(holder.picture);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showing, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public interface OnInsertListener {
        void OnInsert(Photo photo);
    }

    public void setOnInsertListener(OnInsertListener onInsertListener) {
        this.onInsertListener = onInsertListener;
    }
}
