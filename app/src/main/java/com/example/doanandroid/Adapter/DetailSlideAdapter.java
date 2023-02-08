package com.example.doanandroid.Adapter;

import  android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanandroid.DetailActivity;
import com.example.doanandroid.Model.DetailSlide;
import com.example.doanandroid.R;

import java.util.List;

public class DetailSlideAdapter extends RecyclerView.Adapter<DetailSlideAdapter.DetailViewHoler> {

    public DetailSlideAdapter(List<DetailSlide> list, DetailActivity detailActivity) {
        this.list = list;
        this.detailActivity = detailActivity;
    }

    List<DetailSlide> list;
    DetailActivity detailActivity;


    @NonNull
    @Override
    public DetailViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
        return new DetailViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHoler holder, int position) {
        DetailSlide detailSlider = list.get(position);

        Glide.with(detailActivity)
                .load(detailSlider.getImgID())
                .into(holder.detail_photo);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DetailViewHoler extends RecyclerView.ViewHolder {
        ImageView detail_photo;

        public DetailViewHoler(@NonNull View itemView) {
            super(itemView);
            detail_photo = itemView.findViewById(R.id.detail_photo);
        }
    }

}
