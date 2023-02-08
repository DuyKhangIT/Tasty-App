package com.example.doanandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.Model.SlideImage;

import com.example.doanandroid.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.PhotoViewHolder> {
    List<SlideImage> slideImageList;

    public SlideAdapter(List<SlideImage> sliderImageList) {
        this.slideImageList = sliderImageList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        SlideImage photo = slideImageList.get(position);
        holder.roundedImageView.setImageResource(photo.getImg());
    }

    @Override
    public int getItemCount() {
        return slideImageList.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView roundedImageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.imgSlide);
        }
    }
}

