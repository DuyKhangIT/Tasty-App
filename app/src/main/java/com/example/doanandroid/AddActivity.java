package com.example.doanandroid;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class AddActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView rate1, rate2, rate3, rate4, rate5;
    EditText edtContent, edtTittle, edtAddress;
    String rateCount;
    ProgressDialog mProgressDialog;
    Button btnPost;
    TextView txtErrPost;
    Uri imageURI;
    ImageView imgShow;
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    //checkbutton
    CheckBox checksang,checkchieu,checktoi;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtContent = findViewById(R.id.edtContent);
        edtTittle = findViewById(R.id.edtTittle);
        edtAddress = findViewById(R.id.edtAddress);
        txtErrPost = findViewById(R.id.txtErrPost);
        btnPost = findViewById(R.id.btnPost);

        //anh xạ checkbox
         init();


        rate1 = findViewById(R.id.rate1);
        rate2 = findViewById(R.id.rate2);
        rate3 = findViewById(R.id.rate3);
        rate4 = findViewById(R.id.rate4);
        rate5 = findViewById(R.id.rate5);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        ImageView imageView5 = findViewById(R.id.imageView5);
        ImageView imageView6 = findViewById(R.id.imageView6);
        ImageView btnAddImg = findViewById(R.id.btnAddImg);
        imgShow=findViewById(R.id.imgShow);

        imgShow.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String data = bundle.get("username").toString();


        rate1.setVisibility(View.INVISIBLE);
        rate2.setVisibility(View.INVISIBLE);
        rate3.setVisibility(View.INVISIBLE);
        rate4.setVisibility(View.INVISIBLE);
        rate5.setVisibility(View.INVISIBLE);
        edtContent.setOnTouchListener((view, motionEvent) -> {

            view.getParent().requestDisallowInterceptTouchEvent(true);
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }

            return false;
        });
        rateCount = "";
        ImageView imgBack = findViewById(R.id.imgBackDetail);
        imgBack.setOnClickListener(v -> onBackPressed());
        imageView2.setOnClickListener(v -> {
            imageView2.setVisibility(View.INVISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView6.setVisibility(View.VISIBLE);
            rate1.setVisibility(View.VISIBLE);
            rate2.setVisibility(View.INVISIBLE);
            rate3.setVisibility(View.INVISIBLE);
            rate4.setVisibility(View.INVISIBLE);
            rate5.setVisibility(View.INVISIBLE);
            rateCount = "1";
        });
        imageView3.setOnClickListener(v -> {
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.INVISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView6.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);

            rate1.setVisibility(View.VISIBLE);
            rate2.setVisibility(View.VISIBLE);
            rate3.setVisibility(View.INVISIBLE);
            rate4.setVisibility(View.INVISIBLE);
            rate5.setVisibility(View.INVISIBLE);
            rateCount = "2";
        });
        imageView4.setOnClickListener(v -> {
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.INVISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView6.setVisibility(View.VISIBLE);


            rate1.setVisibility(View.VISIBLE);
            rate2.setVisibility(View.VISIBLE);
            rate3.setVisibility(View.VISIBLE);
            rate4.setVisibility(View.INVISIBLE);
            rate5.setVisibility(View.INVISIBLE);
            rateCount = "3";
        });
        imageView5.setOnClickListener(v -> {
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.INVISIBLE);
            imageView6.setVisibility(View.VISIBLE);


            rate1.setVisibility(View.VISIBLE);
            rate2.setVisibility(View.VISIBLE);
            rate3.setVisibility(View.VISIBLE);
            rate4.setVisibility(View.VISIBLE);
            rate5.setVisibility(View.INVISIBLE);
            rateCount = "4";
        });
        imageView6.setOnClickListener(v -> {
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView6.setVisibility(View.INVISIBLE);

            rate1.setVisibility(View.VISIBLE);
            rate2.setVisibility(View.VISIBLE);
            rate3.setVisibility(View.VISIBLE);
            rate4.setVisibility(View.VISIBLE);
            rate5.setVisibility(View.VISIBLE);
            rateCount = "5";
        });

        //lấy username
        String username = data;


        //nút thêm ảnh
        btnAddImg.setOnClickListener(v -> {
            Intent galleryIntent=new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,2); });


        //nút đăng bài
        btnPost.setOnClickListener(v -> {
            String getContent = edtContent.getText().toString();
            String getTittle = edtTittle.getText().toString();
            String getAddress = edtAddress.getText().toString();

            if (getContent.equals("") || getTittle.equals("") || getAddress.equals("") || checksang.equals("")  || rateCount.equals(""))
                txtErrPost.setText("Bạn cần nhập đủ thông tin để đăng bài");
            else if(imageURI==null){
                txtErrPost.setText("Bạn cần phải chọn hình ảnh");
            }
            else {
                txtErrPost.setText("");
                //upload
                uploadImg(imageURI,username,getContent,getTittle,getAddress);
            }
        });
    }

    //ánh xạ checkbox
    void init(){
        checksang = findViewById(R.id.check_sang);
        checkchieu = findViewById(R.id.check_chieu);
        checktoi = findViewById(R.id.check_toi);

        checksang.setOnCheckedChangeListener(mcheck);
        checkchieu.setOnCheckedChangeListener(mcheck);
        checktoi.setOnCheckedChangeListener(mcheck);
    }

    // Trạng thái  checkbox
    String status;
    CompoundButton.OnCheckedChangeListener mcheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.getId()==R.id.check_sang)
            {
                if(isChecked){
                    checkchieu.setChecked(false);
                    checktoi.setChecked(false);

                    checkchieu.setEnabled(false);
                    checktoi.setEnabled(false);
                }
                else {
                    checkchieu.setEnabled(true);
                    checktoi.setEnabled(true);
                }
            }
            if(buttonView.getId()==R.id.check_chieu)
            {
                if(isChecked){
                    checksang.setChecked(false);
                    checktoi.setChecked(false);

                    checksang.setEnabled(false);
                    checktoi.setEnabled(false);
                }
                else {
                    checksang.setEnabled(true);
                    checktoi.setEnabled(true);
                }
            }
            if(buttonView.getId()==R.id.check_toi)
            {
                if(isChecked){
                    checksang.setChecked(false);
                    checkchieu.setChecked(false);

                    checksang.setEnabled(false);
                    checkchieu.setEnabled(false);
                }
                else {
                    checksang.setEnabled(true);
                    checkchieu.setEnabled(true);
                }
            }
            if(isChecked){
              status= buttonView.getText().toString();


            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2&&resultCode==RESULT_OK&&data!=null){
            imageURI=data.getData();
            imgShow.setVisibility(View.VISIBLE);
            imgShow.setImageURI(imageURI);
        }
    }

    //upload
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadImg(Uri uri, String username, String getContent, String getTittle, String getAddress ){

        //đặt tên file
        StorageReference fileRef=reference.child(System.currentTimeMillis()+"."+getFileExtesion(uri));
        //upload ảnh
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri1 -> {

            //lấy uri hinh về
            String mUri= uri1.toString();
            //thêm bài viết
            DocumentReference user = db.collection("Users").document(username);
            user.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Date date = new Date();

                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> post = new HashMap<>();
                    String name = document.getString("name");
                    String imgUser = document.getString("img");
                    String[] listImages = {};
                    post.put("name", name);
                    post.put("imgUser", imgUser);
                    post.put("content", getContent);
                    post.put("tittle", getTittle);
                    post.put("address", getAddress);
                    post.put("rate", rateCount);
                    //lấy uri ảnh
                    post.put("listImages", Arrays.asList(mUri,"https://static.riviu.co/960/image/2020/11/28/393a04c533eb8d84fd795a2999a34839_output.jpeg","https://static.riviu.co/960/image/2020/11/28/4aeac2ca2781400546b48d973aee699c_output.jpeg","https://static.riviu.co/960/image/2020/11/28/1d694d9dd27164e46999247c9ca2605f_output.jpeg"));
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    String time=(dtf.format(now));

                    post.put("time", time);
//                    post.put("mili",time);
                    if(status.trim().equals("Buổi sáng"))
                        post.put("timeInDay", "morning");
                    else if(status.trim().equals("Buổi chiều"))
                        post.put("timeInDay", "evening");
                    else
                        post.put("timeInDay", "noon");


                    //lưu lên firestore
                    db.collection("posts")
                            .add(post)
                            .addOnSuccessListener(documentReference -> {
                                mProgressDialog = new ProgressDialog(AddActivity.this);
                                mProgressDialog.setMessage("Đang thêm bài viết");
                                mProgressDialog.show();

                                Intent intent=new Intent(AddActivity.this,MainActivity.class);
                                Bundle x=new Bundle();
                                x.putString("username",username);
                                intent.putExtras(x);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> mProgressDialog.setMessage("fail"));

                }
            });
        })).addOnFailureListener(e -> Toast.makeText(AddActivity.this, "fail", Toast.LENGTH_SHORT).show());
    }
    private String getFileExtesion(Uri mUri) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
}