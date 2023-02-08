package com.example.doanandroid.Intro_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.doanandroid.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdatePassword extends AppCompatActivity {
    TextInputLayout edtNewPassword, edtConfirmNewPassword;
    Button btnConfirm;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword);
        btnConfirm = findViewById(R.id.btnConfirm);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String data = bundle.get("username").toString();
        String username = data;

        btnConfirm.setOnClickListener(v -> {
            String password = edtNewPassword.getEditText().getText().toString();
            String confirmPassword = edtConfirmNewPassword.getEditText().getText().toString();
            ValidateInput(password, confirmPassword);

            if (ValidateInput(password, confirmPassword))
                UpdatePassword(username, password);

        });


    }

    private Boolean ValidateInput(String password, String confirmPasword) {
        if (password.isEmpty()) {
            edtNewPassword.setError("Bạn cần nhập mật khẩu mới");
            return false;
        } else if (confirmPasword.isEmpty()) {
            edtNewPassword.setError(null);
            edtNewPassword.setErrorEnabled(false);
            edtConfirmNewPassword.setError("Bạn cần nhập lại mật khẩu");
            return false;
        } else if (!password.equals(confirmPasword)) {
            edtNewPassword.setError(null);
            edtNewPassword.setErrorEnabled(false);
            edtConfirmNewPassword.setError("Mật khẩu không khớp");
            return false;
        } else {
            edtConfirmNewPassword.setError(null);
            edtConfirmNewPassword.setErrorEnabled(false);
            return true;
        }
    }


    private void UpdatePassword(String username, String password) {
        DocumentReference userRef = db.collection("Users").document(username);
        userRef
                .update("password", password)
                .addOnSuccessListener(aVoid ->
                        {
                            Toast.makeText(UpdatePassword.this, "Update thành công", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdatePassword.this, Login.class);
                            startActivity(i);
                        }

                )
                .addOnFailureListener(e -> Toast.makeText(UpdatePassword.this, "Update fail", Toast.LENGTH_SHORT).show());
    }
}