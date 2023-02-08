package com.example.doanandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanandroid.DiaDiemActivity;
import com.example.doanandroid.Model.HinhAnhDiaDiem;
import com.example.doanandroid.R;

import java.util.List;

public class HinhAnhDiaDiemAdapter extends RecyclerView.Adapter<HinhAnhDiaDiemAdapter.HinhAnhDiaDiemViewHolder>{

    private List<HinhAnhDiaDiem> dsHinh;

    DiaDiemActivity context;
    //Hàm loading dữ liệu lên Adapter
    public void setData(DiaDiemActivity context,List<HinhAnhDiaDiem> list){
        this.context=context;
        this.dsHinh = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HinhAnhDiaDiemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diadiem,parent,false);
        return new HinhAnhDiaDiemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(dsHinh != null){
            return dsHinh.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull HinhAnhDiaDiemViewHolder holder, int position) {
        HinhAnhDiaDiem hinhAnhDiaDiem = dsHinh.get(position);
        if(hinhAnhDiaDiem == null){
            return;
        }

        //gile

        Glide.with(context)
                .load(hinhAnhDiaDiem.getResourceID())
                .into(holder.imgHinhChiTietQuan);
    }

    public class HinhAnhDiaDiemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgHinhChiTietQuan;

        public HinhAnhDiaDiemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhChiTietQuan = itemView.findViewById(R.id.img_HinhChiTietQuan);
        }
    }
}
