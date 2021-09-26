package com.madcruddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText userName,userPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView txtSig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.EditUserName);
        userPassword = findViewById(R.id.EditUserPassword);
        progressBar = findViewById(R.id.progressBar);
        txtSig = findViewById(R.id.txtSig);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();

        txtSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userName.getText().toString();
                String pass = userPassword.getText().toString();

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this,"Fill all the fields !",Toast.LENGTH_SHORT).show();
                    return;
                } else if(email.contains("admin") && pass.contains("admin")){
                    startActivity(new Intent(LoginActivity.this,AdminHomeActivity.class));
                } else {
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this,"User Logged In !",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,UserActivity.class));
                                finish();
                            } else {
                                //progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this,"Failed to Logged In User !",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

        @Override
        protected void onStart() {
            super.onStart();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user!=null) {
                Log.i("info","user"+ user.getEmail().toString());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                this.finish();
            }
        }
}