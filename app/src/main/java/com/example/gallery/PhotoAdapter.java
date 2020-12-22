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
    private OnLongClickListener onLongClickListener;
    private final List<Photo> photos;

    public PhotoAdapter(List<Photo> values ) {
        photos = values;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView picture;

        ViewHolder(View view){
            super(view);
            picture = view.findViewById(R.id.img);
            picture.setOnLongClickListener(v -> {
                onLongClickListener.onLongClickListener(photos.get(this.getAdapterPosition()));
                return true;
            });
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        Picasso.get().load(photo.getUrlS()).into(holder.picture);
        holder.itemView.setTag(photo);

    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showing, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }
}
