package com.example.doanandroid.Intro_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.doanandroid.MainActivity;
import com.example.doanandroid.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Login extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("Users");
    Button btnLogin, btnQuenMatKhau,btnLoginWithPhoneNumber;
    TextView txtCreateAcc;
    TextInputLayout edtUsername, edtPassword;
    ProgressDialog mProgressDialog;

    // khai báo hàm lưu đăng nhập
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private void initPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mProgressDialog = new ProgressDialog(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnQuenMatKhau = findViewById(R.id.btnQuenMatKhau);
        btnLoginWithPhoneNumber = findViewById(R.id.btnLoginWithPhoneNumber);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.ediPassword);
        txtCreateAcc = findViewById(R.id.txtCreateAcc);
        initPreferences();


        //đăng nhập
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getEditText().getText().toString();
            String password = edtPassword.getEditText().getText().toString();
            edtUsername.setError(null);
            edtUsername.setErrorEnabled(false);
            //Xác Thực thông tin đăng nhập
            ValidateInput(username,password);
            if(ValidateInput(username,password))
                Login(username,password);

        });

        //đăng ký
        txtCreateAcc.setOnClickListener(v -> {
            Intent i = new Intent(Login.this, RegisterActivity.class);
            startActivity(i);
        });

        //quên mật khẩu
        btnQuenMatKhau.setOnClickListener(v -> {
            Intent i=new Intent(Login.this, ForgotPasswordActivity.class);
            startActivity(i);
        });

        btnLoginWithPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, LoginWithPhoneNumber.class);
                startActivity(intent);
            }
        });



    }

    //Kiểm tra username và password có hợp lệ không
    private Boolean ValidateInput(String username, String password) {

        if (username.isEmpty()) {
            edtUsername.setError("Không được để trống Tên đăng nhập");
            return false;
        } else if (password.isEmpty()) {
            edtUsername.setError(null);
            edtUsername.setErrorEnabled(false);
            edtPassword.setError("Không được để trống Mật khẩu");
            return false;
        } else {
            edtPassword.setError(null);
            edtPassword.setErrorEnabled(false);
            return true;
        }
    }

    //đăng nhập
    private void Login(String username, String password){
        users .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //khai báo đếm để đếm tài khoản đã có
                        int dem = 0;
                        int l = task.getResult().size();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String getUsername=documentSnapshot.getId();
                            if(!username.equals(getUsername)){
                                dem++;
                            }
                            else {
                                DocumentReference users = db.collection("Users").document(username);
                                users.get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        DocumentSnapshot document = task1.getResult();
                                        if (document.getString("password").equals(password)) {
                                            mProgressDialog.setMessage("Đang đăng nhập...");
                                            mProgressDialog.show();

                                            //lưu username pass ->bộ nhớ đt

                                            editor.putString("username", username);
                                            editor.commit();

                                            Intent i = new Intent(Login.this, MainActivity.class);
                                            Bundle bundle=new Bundle();
                                            bundle.putString("username",username);
                                            i.putExtras(bundle);
                                            startActivity(i);
                                        }
                                        else{
                                            edtPassword.setError("Sai mật khẩu");
                                        }

                                    }
                                });

                            }
                        }
                        if(dem == l)
                            edtUsername.setError("Tài khoản không tồn tại");


                    }

                });
    }

}