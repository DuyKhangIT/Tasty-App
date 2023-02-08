package com.example.doanandroid.Intro_Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.doanandroid.MainActivity;
import com.example.doanandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginWithPhoneNumber extends AppCompatActivity {
    TextInputLayout edtPhone,edtOTP;
    Button btnNhapOTP,btnXacThucOTP;
    FirebaseAuth mAuth;
    String verificationID;
    ProgressBar bar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("Users");
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;
    private void initPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone_number);
        initPreferences();

        edtPhone = findViewById(R.id.edtPhone);
        edtOTP = findViewById(R.id.edtOTP);
        btnNhapOTP = findViewById(R.id.btnNhapOTP);
        btnXacThucOTP = findViewById(R.id.btnXacThucOTP);
        mAuth = FirebaseAuth.getInstance();
        bar = findViewById(R.id.bar);

        btnNhapOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edtPhone.getEditText().getText().toString()))
                {
                    Toast.makeText(LoginWithPhoneNumber.this,"Vui Long Nhap So Dien Thoai!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    String number = edtPhone.getEditText().getText().toString();
                    bar.setVisibility(view.VISIBLE);
                    guimaxacthuc(number);
                }

            }
        });

        btnXacThucOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(edtOTP.getEditText().getText().toString()))
                {
                    Toast.makeText(LoginWithPhoneNumber.this,"Nhap ma OTP sai!!",Toast.LENGTH_SHORT).show();
                }
                else
                    maxacthuc(edtOTP.getEditText().getText().toString());
            }
        });
    }

    private void guimaxacthuc(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            final String code = credential.getSmsCode();
            if(code !=null){
                maxacthuc(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(LoginWithPhoneNumber.this,"Xac Thuc That Bai",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

            super.onCodeSent(s,token);
            verificationID = s;
            Toast.makeText(LoginWithPhoneNumber.this,"Code sent",Toast.LENGTH_SHORT).show();
            btnXacThucOTP.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);
        }
    };

    private void maxacthuc(String Code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code);
        siginbtCredential(credential);
    }

    private void siginbtCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginWithPhoneNumber.this,"Login Successful",Toast.LENGTH_SHORT).show();


                    DocumentReference users = db.collection("Users").document((edtPhone.getEditText().getText().toString()));
                    users.get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            DocumentSnapshot document = task1.getResult();
                            if (!document.exists()) {
                                Map<String, Object> data1 = new HashMap<>();
                                data1.put("name", "chưa cập nhật");
                                data1.put("password", "123456");
                                data1.put("phone", (edtPhone.getEditText().getText().toString()));
                                data1.put("mail", "chưa cập nhật");
                                data1.put("img", "https://i.pinimg.com/564x/63/33/34/633334f8dcbd77626a74ed32547f7271.jpg");
                                db.collection("Users").document((edtPhone.getEditText().getText().toString())).set(data1);
                            }

                            editor.putString("username", edtPhone.getEditText().getText().toString());
                            editor.commit();
                            Intent i = new Intent(LoginWithPhoneNumber.this, MainActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("username", (edtPhone.getEditText().getText().toString()));
//                            i.putExtras(bundle);
                            startActivity(i);


                        }

                    });


                }
            }
        });
    }

    // Lưu login
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if(currentUser!=null)
//        {
//            startActivity(new Intent(LoginWithPhoneNumber.this,MainActivity.class));
//            finish();
//        }
//    }
}