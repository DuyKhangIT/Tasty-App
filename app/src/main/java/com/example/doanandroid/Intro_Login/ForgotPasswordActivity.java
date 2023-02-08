package com.example.doanandroid.Intro_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doanandroid.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout edtUsernameRS, edtEmailRS;
    Button btnXacThucEmail;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtUsernameRS = findViewById(R.id.edtUsernameRS);
        edtEmailRS = findViewById(R.id.edtEmailRS);
        btnXacThucEmail = findViewById(R.id.btnXacThucEmail);

        btnXacThucEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsernameRS.getEditText().getText().toString();
                String email = edtEmailRS.getEditText().getText().toString();
                ValidateInput(username, email);
                if (ValidateInput(username, email)) {
                    CheckValidUsername(username, email);
                }


            }
        });

    }

    private Boolean ValidateInput(String username, String email) {

        if (username.isEmpty()) {
            edtUsernameRS.setError("Không được để trống Tên đăng nhập");
            return false;
        } else if (email.isEmpty()) {
            edtUsernameRS.setError(null);
            edtUsernameRS.setErrorEnabled(false);
            edtEmailRS.setError("Không được để trống Email");
            return false;
        } else {
            edtEmailRS.setError(null);
            edtEmailRS.setErrorEnabled(false);
            return true;
        }
    }

    private void CheckValidUsername(String username, String email) {
        users.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int dem = 0;
                        int l = task.getResult().size();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String getUsername = documentSnapshot.getId();
                            if (!username.equals(getUsername))
                                dem++;
                            else {
                                DocumentReference users = db.collection("Users").document(username);
                                users.get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        DocumentSnapshot document = task1.getResult();
                                        if (document.getString("email").equals(email)) {
                                            Intent i = new Intent(ForgotPasswordActivity.this, UpdatePassword.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("username", username);
                                            i.putExtras(bundle);
                                            startActivity(i);
                                        } else {
                                            edtUsernameRS.setError(null);
                                            edtUsernameRS.setErrorEnabled(false);
                                            edtEmailRS.setError("Email không đúng");
                                        }

                                    }
                                });

                            }
                        }
                        if (dem == l) {
                            edtUsernameRS.setError("Tài khoản không tồn tại");
                        }


                    }

                });
    }
}