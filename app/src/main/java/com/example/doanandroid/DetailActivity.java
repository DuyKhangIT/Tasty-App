package com.example.doanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Adapter.DetailSlideAdapter;
import com.example.doanandroid.Adapter.FlagAdapter;
import com.example.doanandroid.Model.DetailSlide;
import com.example.doanandroid.Model.Flag;
import com.example.doanandroid.Model.Location;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {
    ViewPager2 app_bar_image_vpg2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView txtUserName, txtTitleDetail, txtContentDetail, txtAddressDetail, txtTime,txtNoCmt, txtRate,txtDiaDiem;
    CircleImageView avtUser;
    ListView lvCmt;
    ArrayList<Flag> arrayList;
    FlagAdapter flagAdapter;
    ImageView btnCmt;
    EditText edtCmt;
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;
    private void initPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }


        @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtUserName = findViewById(R.id.txtUserName);
        txtTitleDetail = findViewById(R.id.txtTitleDetail);
        txtContentDetail = findViewById(R.id.txtContentDetail);
        txtAddressDetail = findViewById(R.id.txtAddressDetail);
        txtDiaDiem = findViewById(R.id.txtDiaDiem);
        txtNoCmt = findViewById(R.id.txtNoCmt);
        lvCmt = findViewById(R.id.lvCmt);

            initPreferences();
        btnCmt = findViewById(R.id.btnCmt);
        edtCmt = findViewById(R.id.edtCmt);





        txtTime=findViewById(R.id.txtTime);
        txtRate=findViewById(R.id.txtRate);




        avtUser = findViewById(R.id.avtUser);
        app_bar_image_vpg2 = findViewById(R.id.app_bar_image_vpg2);
        List<DetailSlide> detailSlide = new ArrayList<>();


        //back
        ImageView imgBack = findViewById(R.id.imgBackDetail);
        imgBack.setOnClickListener(v -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Location data = (Location) bundle.get("Object");
        String postID = data.getId();


        //vô POST lấy ra locationID
        txtDiaDiem.setOnClickListener(v -> {
            Intent i = new Intent(DetailActivity.this, DiaDiemActivity.class);
            Bundle bb=new Bundle();
            bb.putString("locationID","location1");
            i.putExtras(bb);
            startActivity(i);
        });

        //username

        arrayList=new ArrayList<Flag>();
        //get CMT
        CollectionReference cmts = db.collection("cmts");
        cmts.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //khai báo đếm để đếm tài khoản đã có
                        int dem = 0;
                        int l = task.getResult().size();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String post=documentSnapshot.getString("post");
                            if(post.equals(postID)){
                                String img=documentSnapshot.getString("img");
                                String name=documentSnapshot.getString("name");
                                String content=documentSnapshot.getString("content");
                                arrayList.add(new Flag(img,name,content));
                            }
                        }

                        if(arrayList.size()>0)
                            txtNoCmt.setVisibility(View.GONE);

                        flagAdapter = new FlagAdapter(this, R.layout.layout_row, arrayList);
                        lvCmt.setAdapter(flagAdapter);

                    }


                });

        //cmt
        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmt=edtCmt.getText().toString();

                if(cmt.equals("")) Toast.makeText(DetailActivity.this, "Bạn chưa nhập bình luận", Toast.LENGTH_SHORT).show();
                else{

                    Map<String, Object> data = new HashMap<>();


                    //lưu cục bộ - để cmt hiện username
                    String getUsername = sharedPreferences.getString("username","");



                    DocumentReference userRef = db.collection("Users").document(getUsername);
                    userRef.get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                List<String> list;

                                //đẩy vô hàm Map
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                         String name=  documentSnapshot.getString("name");
                                        String img=documentSnapshot.getString("img");
                                        data.put("username", getUsername);
                                        data.put("img", img);
                                        data.put("name",name);
                                        data.put("content", cmt);
                                        data.put("post", postID);

                                        db.collection("cmts")
                                                .add(data)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(DetailActivity.this, "Bình luận thành công", Toast.LENGTH_SHORT).show();
                                                        arrayList.clear();

                                                        cmts.get()
                                                                .addOnCompleteListener(task -> {
                                                                    if (task.isSuccessful()) {
                                                                        //khai báo đếm để đếm tài khoản đã có
//                                                                        int dem = 0;
//                                                                        int l = task.getResult().size();
                                                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                                            String post=documentSnapshot.getString("post");
                                                                            if(post.equals(postID)){
                                                                                String img=documentSnapshot.getString("img");
                                                                                String name=documentSnapshot.getString("name");
                                                                                String content=documentSnapshot.getString("content");
                                                                                arrayList.add(new Flag(img,name,content));
                                                                                edtCmt.setText("");
                                                                            }
                                                                        }

                                                                        if(arrayList.size()>0)
                                                                            txtNoCmt.setVisibility(View.GONE);

                                                                        flagAdapter = new FlagAdapter(DetailActivity.this, R.layout.layout_row, arrayList);
                                                                        lvCmt.setAdapter(flagAdapter);

                                                                    }


                                                                });
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(DetailActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                }
                            });


                }
            }
        });

        //chi tiết
        DocumentReference postRef = db.collection("posts").document(postID);
        postRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    List<String> list;

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            //get data
                            String name = documentSnapshot.getString("name");
                            list = (List<String>) documentSnapshot.get("listImages");
//
                            String title = documentSnapshot.getString("tittle");
                            String content = documentSnapshot.getString("content");
                            String address = documentSnapshot.getString("address");
                            String time=documentSnapshot.getString("time");
                            String rate=documentSnapshot.getString("rate");
                            String avt=documentSnapshot.getString("imgUser");



                            //set data
                            txtUserName.setText(name);
                            txtTitleDetail.setText(title);
                            txtContentDetail.setText(content);
                            txtAddressDetail.setText(address);
                            txtTime.setText(time);
                            txtRate.setText(rate+"/5 điểm");
                            Glide.with(DetailActivity.this)
                                    .load(avt)
                                    .into(avtUser);




                            //lấy data
                            for (int i = 0; i < list.size(); i++)
                                detailSlide.add(new DetailSlide(list.get(i)));
                            //bỏ vô adapter
                            DetailSlideAdapter detailSlideAdapter = new DetailSlideAdapter(detailSlide, DetailActivity.this);
                            app_bar_image_vpg2.setAdapter(detailSlideAdapter);
                        }
                    }
                });





    }
}