package com.example.doanandroid;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.HinhChiTietQuanAdapter;
import com.example.doanandroid.Model.HinhAnhDiaDiem;
import com.example.doanandroid.Model.HinhChiTietQuan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DiaDiemActivity extends AppCompatActivity {

    private RecyclerView rcv_DSHinh;
    private HinhChiTietQuanAdapter hinhChiTietQuanAdapter;
    ImageView img_HinhQuan;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView Tv_TenQuan, TV_DiaChi, TV_ThoiGian, TV_LienLac;
    ImageView imgBackDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_diem);
        img_HinhQuan = findViewById(R.id.img_HinhQuan);
        rcv_DSHinh = findViewById(R.id.rcv_DSHinh);
        Tv_TenQuan = findViewById(R.id.Tv_TenQuan);
        TV_DiaChi = findViewById(R.id.TV_DiaChi);
        TV_ThoiGian = findViewById(R.id.TV_ThoiGian);
        TV_LienLac = findViewById(R.id.TV_LienLac);
        imgBackDetail = findViewById(R.id.imgBackDetail);

        imgBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        hinhChiTietQuanAdapter = new HinhChiTietQuanAdapter(this);


        //chọn địa điểm
        //intent
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String locationID=bundle.get("locationID").toString();

        DocumentReference location = db.collection("Location").document(locationID);
        location.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    List<String> listPost;

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            //get data
                            String img = documentSnapshot.getString("img");
                            String name = documentSnapshot.getString("name");
                            String address = documentSnapshot.getString("address");
                            String phone = documentSnapshot.getString("phone");
                            String time = documentSnapshot.getString("time");


                            Glide.with(DiaDiemActivity.this)
                                    .load(img)
                                    .into(img_HinhQuan);
                            Tv_TenQuan.setText(name);
                            TV_DiaChi.setText(address);
                            TV_ThoiGian.setText(time);
                            TV_LienLac.setText(phone);

                            //list ảnh
                            List<HinhChiTietQuan> listhinhchitietquan = new ArrayList<>();
                            List<HinhAnhDiaDiem> listHinhAnhDiaDiem = new ArrayList<>();
                            listPost = (List<String>) documentSnapshot.get("posts");



                            String postID =listPost.get(1);//???


                            DocumentReference post = db.collection("posts").document(postID);
                            post.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        List<String> listImages;

                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                listImages= (List<String>) documentSnapshot.get("listImages");


                                                for (int i = 0; i < listImages.size(); i++)
                                                    listHinhAnhDiaDiem.add(new HinhAnhDiaDiem(listImages.get(i)));



                                                listhinhchitietquan.add(new HinhChiTietQuan(listHinhAnhDiaDiem));
                                                hinhChiTietQuanAdapter.setData(listhinhchitietquan);
                                            }
                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DiaDiemActivity.this, RecyclerView.VERTICAL, false);
                                            rcv_DSHinh.setLayoutManager(linearLayoutManager);
                                            rcv_DSHinh.setAdapter(hinhChiTietQuanAdapter);
                                        }
                                    });

                        }
                    }
                });


    }


}