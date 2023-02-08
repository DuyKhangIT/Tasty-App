package com.example.doanandroid.Intro_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.doanandroid.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("Users");
    TextInputLayout edtName, edtNewUsername,edtEmail ,editNewPassword, edtEnterPassword;
    Button btnResgister,txtLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtNewUsername = findViewById(R.id.edtNewUsername);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        editNewPassword = findViewById(R.id.editNewPassword);
        edtEnterPassword = findViewById(R.id.edtEnterPassword);
        btnResgister = findViewById(R.id.btnResgister);
        txtLogin = findViewById(R.id.txtLogin);



        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, Login.class);
                startActivity(i);
            }
        });

        btnResgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getEditText().getText().toString();
                String username = edtNewUsername.getEditText().getText().toString();
//                String email = edtEmail.getEditText().getText().toString();
                String password = editNewPassword.getEditText().getText().toString();
                String enterPassword = edtEnterPassword.getEditText().getText().toString();

                if(!XacThucTenProfile() | !XacThucTen() | !XacThucEmail() | !XacThucPassword()){
                    return;
                }

                else{ ValidateRegister(name, username,password, enterPassword);}
            }
        });

    }

    private Boolean XacThucTenProfile(){
        String XacThuc = edtName.getEditText().getText().toString();
        String khongkhoangtrang = "\\A\\w{4,20}\\z";

        if(XacThuc.isEmpty()){
            edtName.setError("Không được để trống Họ và Tên");
            return false;
        } else if (!XacThuc.matches(khongkhoangtrang)){
            edtName.setError("Có ít nhất 4 ký tự và Không được khoảng trắng");
            return false;
        }
        else {
            edtName.setError(null);
            edtName.setErrorEnabled(false);
            return true;
        }
    }
    //Xác Thực Tên, Hiện thông báo không được để trống
    private Boolean XacThucTen(){
        String XacThuc = edtNewUsername.getEditText().getText().toString();
        String khongkhoangtrang = "\\A\\w{4,20}\\z";

        if(XacThuc.isEmpty()){
            edtNewUsername.setError("Không được để trống Tên đăng nhập");
            return false;
        } else if (XacThuc.length()>=15){
            edtNewUsername.setError("Tên đăng nhập không được vượt quá 15 ký tụ");
            return false;
        } else if (!XacThuc.matches(khongkhoangtrang)){
            edtNewUsername.setError("Có ít nhất 4 ký tự và Không được khoảng trắng");
            return false;
        }
        else {
            edtNewUsername.setError(null);
            edtNewUsername.setErrorEnabled(false);
            return true;
        }
    }

    //Xác thực Mật khẩu, Hiện thông báo không được để trống
    private Boolean XacThucPassword(){
        String XacThuc = editNewPassword.getEditText().getText().toString();
//        String passwordVal =
//                "(?=.*[0-9])"+
//                "(?=.*[a-z])"+
//                "(?=.*[A-Z])"+
//                "(?=.*[a-zA-Z])";  //Bất kỳ chữ cái nào
//                "(?=.*[@#$%^&+=])" + //Có ít nhất 1 ký tự đặc biệt
//                "(?=\\s+$)" +        //Không có khoảng trắng
//                ".{2,}" +            //Có ít nhất 4 ký tự
//                "$";

        if(XacThuc.isEmpty()) {
            editNewPassword.setError("Không được để trống Mật khẩu");
            return false;

//       } else if (editNewPassword.setCounterMaxLength()<=6)
//       {
//            editNewPassword.setError("Mật khẩu quá yếu");
//            return false;
        }
        else {
            editNewPassword.setError(null);
            editNewPassword.setErrorEnabled(false);
            return true;
        }
    }

    //Xác thực Email, Hiện thông báo không được để trống
    private Boolean XacThucEmail(){
        String XacThuc = edtEmail.getEditText().getText().toString();
        String emilPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(XacThuc.isEmpty()){
            edtEmail.setError("Không được để trống Email");
            return false;
        } else if (!XacThuc.matches(emilPattern)){
            edtEmail.setError("Email không hợp lệ");
            return false;
        }
        else {
            edtEmail.setError(null);
            edtEmail.setErrorEnabled(false);
            return true;
        }
    }

    private void ValidateRegister(String name, String username, String password, String enterPassword) {
        if (!password.equals(enterPassword))
            editNewPassword.setError("Mật khẩu không trùng khớp");
        else {
            DocumentReference users = db.collection("Users").document(username);
            users.get().addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    DocumentSnapshot document = task1.getResult();
                    if (document.exists()) {
                        edtNewUsername.setError("Tài khoản đã tồn tài");
                    }
                    else {
                        //xac thuc email
                        String email = edtEmail.getEditText().getText().toString();
                        Map<String, Object> data1 = new HashMap<>();
                        data1.put("name", name);
                        data1.put("password", password);
                        data1.put("phone", "chưa cập nhật");
                        data1.put("mail", email);
                        data1.put("img", "https://i.pinimg.com/564x/63/33/34/633334f8dcbd77626a74ed32547f7271.jpg");
                        db.collection("Users").document(username).set(data1);
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, Login.class);
                        startActivity(i);

                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}