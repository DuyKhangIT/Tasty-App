package com.example.doanandroid.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanandroid.DetailActivity;
import com.example.doanandroid.Model.Location;
import com.example.doanandroid.R;
import com.example.doanandroid.SearchActivity;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHoler> {

    SearchActivity context;
    int layout;
    ArrayList<Location> arrayList;

    public SearchAdapter(SearchActivity context, int layout, ArrayList<Location> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHoler holder, int position) {
        Location listData = arrayList.get(position);
        holder.txtName.setText(String.valueOf(listData.getTxtName()));
        Glide.with(context)
                .load(listData.getImg())
                .into(holder.img);
        holder.txtUser.setText(String.valueOf(listData.getTxtUser()));
        Glide.with(context)
                .load(listData.getImgUser())
                .into(holder.imgUser);
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetail(listData);
            }
        });

    }

    private void onClickGoToDetail(Location listData) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object", listData);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHoler extends RecyclerView.ViewHolder {

        CardView layout_item;
        private TextView txtName, txtUser;
        private ImageView img, imgUser;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            txtName = itemView.findViewById(R.id.txtName);
            img = itemView.findViewById(R.id.imgLocation);
            txtUser = itemView.findViewById(R.id.txtUser);
            imgUser = itemView.findViewById(R.id.imgUser);

        }
    }
}
