package com.madcruddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText userName,userPassword,userPhone;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView txtReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        userPhone = findViewById(R.id.userPhone);
        progressBar = findViewById(R.id.progressBar);
        txtReg = findViewById(R.id.txtReg);
        mAuth = FirebaseAuth.getInstance();
        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email = userName.getText().toString();
                String pass = userPassword.getText().toString();
                String phone = userPhone.getText().toString();
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(pass) && TextUtils.isEmpty(phone)){
                    Toast.makeText(RegistrationActivity.this,"Fill all the fields !",Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility((View.GONE));
                                Toast.makeText(RegistrationActivity.this,"User Registered !",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this,"Failed to Register User !",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}