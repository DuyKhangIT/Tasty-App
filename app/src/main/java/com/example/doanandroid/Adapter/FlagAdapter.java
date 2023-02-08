package com.example.doanandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Model.Flag;
import com.example.doanandroid.R;

import java.util.ArrayList;

public class FlagAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Flag> arrayList;
    int layout;

    public FlagAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Flag> object ) {
        super(context, resource,object);
        this.context = context;
        this.arrayList=object;
        this.layout=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Flag flag=arrayList.get(position);
        if(convertView==null)
            convertView= LayoutInflater.from(context).inflate(layout,null);
        ImageView img=convertView.findViewById(R.id.imgAvt);
//        img.setImageResource(flag.getIdFlag());
        Glide.with(context)
                .load(flag.getImgAvt())
                .into(img);
        TextView txtNameCMT=convertView.findViewById(R.id.txtNameCMT);
        txtNameCMT.setText(flag.getTxtNameCMT());

        TextView txtContent=convertView.findViewById(R.id.txtContent);
        txtContent.setText(flag.getTxtContent());
        return convertView;
    }
}
