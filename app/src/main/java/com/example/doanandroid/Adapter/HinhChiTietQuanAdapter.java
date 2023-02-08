package com.example.doanandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid.DiaDiemActivity;
import com.example.doanandroid.Model.HinhChiTietQuan;
import com.example.doanandroid.R;

import java.util.List;

public class HinhChiTietQuanAdapter extends RecyclerView.Adapter<HinhChiTietQuanAdapter.HinhChiTietQuanViewHolder> {

    private Context mcontext;
    private List<HinhChiTietQuan> dsHinhChiTietQuan;

    public HinhChiTietQuanAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }
    public void setData(List<HinhChiTietQuan> list){
        this.dsHinhChiTietQuan = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HinhChiTietQuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hinhchitietquan,parent,false);
        return new HinhChiTietQuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HinhChiTietQuanViewHolder holder, int position) {
        HinhChiTietQuan hinhChiTietQuan = dsHinhChiTietQuan.get(position);
        if(hinhChiTietQuan == null)
        {
            return;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext,RecyclerView.HORIZONTAL,false);
        holder.rcvHinhChiTietQuan.setLayoutManager(linearLayoutManager);

        HinhAnhDiaDiemAdapter hinhAnhDiaDiemAdapter = new HinhAnhDiaDiemAdapter();
        hinhAnhDiaDiemAdapter.setData((DiaDiemActivity) mcontext, hinhChiTietQuan.getHinhAnhDiaDiems());
        holder.rcvHinhChiTietQuan.setAdapter(hinhAnhDiaDiemAdapter);
    }

    @Override
    public int getItemCount() {
        if(dsHinhChiTietQuan !=null)
        {
            return dsHinhChiTietQuan.size();
        }
        return 0;
    }

    public class HinhChiTietQuanViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rcvHinhChiTietQuan;

        public HinhChiTietQuanViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvHinhChiTietQuan = itemView.findViewById(R.id.rcv_HinhChiTIetQuan);
        }
    }
}
