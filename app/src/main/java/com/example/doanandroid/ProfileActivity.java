package com.example.doanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doanandroid.Intro_Login.Login;
import com.example.doanandroid.Intro_Login.LoginWithPhoneNumber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomHome;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView HoTen_profile,TenDangNhap_profile;

    TextInputLayout tvHoVaTen_profile,tvEmail_profile,tvSoDienThoai_profile,tvPassword_profile;
    CircleImageView img_profile;
    ImageView Logout_profile;
    Button btnUpdate;
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;
    private void initPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnUpdate=findViewById(R.id.btnCapNhat);
        HoTen_profile=findViewById(R.id.HoTen_profile);
        TenDangNhap_profile=findViewById(R.id.TenDangNhap_profile);
        tvHoVaTen_profile=findViewById(R.id.tvHoVaTen_profile);
        tvEmail_profile=findViewById(R.id.tvEmail_profile);
        tvSoDienThoai_profile=findViewById(R.id.tvSoDienThoai_profile);
        tvPassword_profile=findViewById(R.id.tvPassword_profile);
        img_profile=findViewById(R.id.img_profile);
        initPreferences();


        Logout_profile = findViewById(R.id.Logout_profile);

        //Logout
        Logout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.commit();
                Intent t = new Intent(ProfileActivity.this, Login.class);
                startActivity(t);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String data = bundle.get("username").toString();
//        TenDangNhap_profile.setText(data);

        DocumentReference userRef = db.collection("Users").document(data);
        userRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    List<String> list;

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            HoTen_profile.setText(documentSnapshot.getString("name"));
                            TenDangNhap_profile.setText("@"+data);
                            tvHoVaTen_profile.getEditText().setText(documentSnapshot.getString("name"));
                            tvPassword_profile.getEditText().setText(documentSnapshot.getString("password"));
                            tvEmail_profile.getEditText().setText(documentSnapshot.getString("email"));
                            tvSoDienThoai_profile.getEditText().setText(documentSnapshot.getString("phone"));

                            String img=documentSnapshot.getString("img");

                            Glide.with(ProfileActivity.this)
                                    .load(img)
                                    .into(img_profile);

                        }
                    }
                });


        //cap nhat len firebase
        btnUpdate.setOnClickListener(v -> {
            String newName=tvHoVaTen_profile.getEditText().getText().toString();
            String newPassword=tvPassword_profile.getEditText().getText().toString();
            String newEmail=tvEmail_profile.getEditText().getText().toString();
            String newPhone=tvSoDienThoai_profile.getEditText().getText().toString();
            userRef
                    .update("name", newName,
                            "password",newPassword,
                            "email",newEmail,
                            "phone",newPhone
                    )
                    .addOnSuccessListener(aVoid ->{

                        Toast.makeText(ProfileActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();

                        //lay ve app hien thi len
                        userRef.get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    List<String> list;

                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            HoTen_profile.setText(documentSnapshot.getString("name"));
                                            TenDangNhap_profile.setText("@"+data);
                                            tvHoVaTen_profile.getEditText().setText(documentSnapshot.getString("name"));
                                            tvPassword_profile.getEditText().setText(documentSnapshot.getString("password"));
                                            tvEmail_profile.getEditText().setText(documentSnapshot.getString("email"));
                                            tvSoDienThoai_profile.getEditText().setText(documentSnapshot.getString("phone"));
                                            String img=documentSnapshot.getString("img");

                                            Glide.with(ProfileActivity.this)
                                                    .load(img)
                                                    .into(img_profile);

                                        }
                                    }
                                });
                    })
                    .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Update fail", Toast.LENGTH_SHORT).show());

        });



        setBottomNav(data);




    }
    @Override
    protected void onStart() {
        super.onStart();
        CollectionReference userRef = db.collection("Users");
        userRef.addSnapshotListener(this, (value, error) -> {
            if (error != null) {
                Toast.makeText(ProfileActivity.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                return;
            }

        });
    }

    private void setBottomNav(String username){
        bottomHome=findViewById(R.id.bottom_profile);
        bottomHome.setSelectedItemId(R.id.action_profile);
        bottomHome.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_add:
                        Intent addIntent=new Intent(getApplicationContext(),AddActivity.class);
                        Bundle dataAdd=new Bundle();
                        dataAdd.putString("username",username);
                        addIntent.putExtras(dataAdd);
                        startActivity(addIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_home:
                        Intent homeIntent=new Intent(getApplicationContext(),MainActivity.class);
                        Bundle dataHome=new Bundle();
                        dataHome.putString("username",username);
                        homeIntent.putExtras(dataHome);
                        startActivity(homeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_profile:
                        return true;
                }
                return false;
            }
        });
    }

}