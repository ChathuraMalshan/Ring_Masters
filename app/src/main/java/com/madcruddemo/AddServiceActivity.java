package com.madcruddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddServiceActivity extends AppCompatActivity {

    private TextInputEditText edtServiceName,edtServiceDes,edtServicePrice,edtServiceImage;
    private Button btnAddService;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        edtServiceName = findViewById(R.id.idEdtServiceName);
        edtServiceDes = findViewById(R.id.idEdtServiceDes);
        edtServicePrice = findViewById(R.id.idEdtServicePrice);
        edtServiceImage = findViewById(R.id.idEdtServiceImage);
        btnAddService = findViewById(R.id.idBtnAddService);
        progressBar = findViewById(R.id.progressBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Services");

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE );
                String serviceName = edtServiceName.getText().toString();
                String serviceDes = edtServiceDes.getText().toString();
                String servicePrice = edtServicePrice.getText().toString();
                String serviceImage = edtServiceImage.getText().toString();
                DatabaseReference ref = databaseReference.push();
                String serviceId = ref.getKey();
                ServiceRVModel serviceRVModel = new ServiceRVModel(serviceName,serviceDes,servicePrice,serviceImage,serviceId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        databaseReference.child(serviceId).setValue(serviceRVModel);
                        Toast.makeText(AddServiceActivity.this,"Service Added..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddServiceActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddServiceActivity.this,"Error is.."+ error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}