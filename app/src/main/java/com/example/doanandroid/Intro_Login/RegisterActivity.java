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
            edtName.setError("Kh??ng ???????c ????? tr???ng H??? v?? T??n");
            return false;
        } else if (!XacThuc.matches(khongkhoangtrang)){
            edtName.setError("C?? ??t nh???t 4 k?? t??? v?? Kh??ng ???????c kho???ng tr???ng");
            return false;
        }
        else {
            edtName.setError(null);
            edtName.setErrorEnabled(false);
            return true;
        }
    }
    //X??c Th???c T??n, Hi???n th??ng b??o kh??ng ???????c ????? tr???ng
    private Boolean XacThucTen(){
        String XacThuc = edtNewUsername.getEditText().getText().toString();
        String khongkhoangtrang = "\\A\\w{4,20}\\z";

        if(XacThuc.isEmpty()){
            edtNewUsername.setError("Kh??ng ???????c ????? tr???ng T??n ????ng nh???p");
            return false;
        } else if (XacThuc.length()>=15){
            edtNewUsername.setError("T??n ????ng nh???p kh??ng ???????c v?????t qu?? 15 k?? t???");
            return false;
        } else if (!XacThuc.matches(khongkhoangtrang)){
            edtNewUsername.setError("C?? ??t nh???t 4 k?? t??? v?? Kh??ng ???????c kho???ng tr???ng");
            return false;
        }
        else {
            edtNewUsername.setError(null);
            edtNewUsername.setErrorEnabled(false);
            return true;
        }
    }

    //X??c th???c M???t kh???u, Hi???n th??ng b??o kh??ng ???????c ????? tr???ng
    private Boolean XacThucPassword(){
        String XacThuc = editNewPassword.getEditText().getText().toString();
//        String passwordVal =
//                "(?=.*[0-9])"+
//                "(?=.*[a-z])"+
//                "(?=.*[A-Z])"+
//                "(?=.*[a-zA-Z])";  //B???t k??? ch??? c??i n??o
//                "(?=.*[@#$%^&+=])" + //C?? ??t nh???t 1 k?? t??? ?????c bi???t
//                "(?=\\s+$)" +        //Kh??ng c?? kho???ng tr???ng
//                ".{2,}" +            //C?? ??t nh???t 4 k?? t???
//                "$";

        if(XacThuc.isEmpty()) {
            editNewPassword.setError("Kh??ng ???????c ????? tr???ng M???t kh???u");
            return false;

//       } else if (editNewPassword.setCounterMaxLength()<=6)
//       {
//            editNewPassword.setError("M???t kh???u qu?? y???u");
//            return false;
        }
        else {
            editNewPassword.setError(null);
            editNewPassword.setErrorEnabled(false);
            return true;
        }
    }

    //X??c th???c Email, Hi???n th??ng b??o kh??ng ???????c ????? tr???ng
    private Boolean XacThucEmail(){
        String XacThuc = edtEmail.getEditText().getText().toString();
        String emilPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(XacThuc.isEmpty()){
            edtEmail.setError("Kh??ng ???????c ????? tr???ng Email");
            return false;
        } else if (!XacThuc.matches(emilPattern)){
            edtEmail.setError("Email kh??ng h???p l???");
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
            editNewPassword.setError("M???t kh???u kh??ng tr??ng kh???p");
        else {
            DocumentReference users = db.collection("Users").document(username);
            users.get().addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    DocumentSnapshot document = task1.getResult();
                    if (document.exists()) {
                        edtNewUsername.setError("T??i kho???n ???? t???n t??i");
                    }
                    else {
                        //xac thuc email
                        String email = edtEmail.getEditText().getText().toString();
                        Map<String, Object> data1 = new HashMap<>();
                        data1.put("name", name);
                        data1.put("password", password);
                        data1.put("phone", "ch??a c???p nh???t");
                        data1.put("mail", email);
                        data1.put("img", "https://i.pinimg.com/564x/63/33/34/633334f8dcbd77626a74ed32547f7271.jpg");
                        db.collection("Users").document(username).set(data1);
                        Toast.makeText(RegisterActivity.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, Login.class);
                        startActivity(i);

                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "????ng k?? th???t b???i", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}