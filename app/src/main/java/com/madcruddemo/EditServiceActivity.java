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

import java.util.HashMap;
import java.util.Map;

public class EditServiceActivity extends AppCompatActivity {
    private TextInputEditText edtServiceName,edtServiceDes,edtServicePrice,edtServiceImage;
    private Button btnUpdateService,btnDeleteService;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String serviceId;
    private ServiceRVModel serviceRVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        edtServiceName = findViewById(R.id.idEdtServiceName);
        edtServiceDes = findViewById(R.id.idEdtServiceDes);
        edtServicePrice = findViewById(R.id.idEdtServicePrice);
        edtServiceImage = findViewById(R.id.idEdtServiceImage);
        btnUpdateService = findViewById(R.id.idBtnUpdateService);
        btnDeleteService = findViewById(R.id.idBtnDeleteService);
        progressBar = findViewById(R.id.idProgressBar);

        serviceRVModel = getIntent().getParcelableExtra("service");
        if(serviceRVModel != null){
            edtServiceName.setText(serviceRVModel.getServiceName());
            edtServiceDes.setText(serviceRVModel.getServiceDes());
            edtServicePrice.setText(serviceRVModel.getServicePrice());
            edtServiceImage.setText(serviceRVModel.getServiceImage());
            serviceId = serviceRVModel.getServiceId();

        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Services").child(serviceId);

        btnUpdateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String serviceName = String.valueOf(edtServiceName.getText());
                String serviceDes = String.valueOf(edtServiceDes.getText());
                String servicePrice = String.valueOf(edtServicePrice.getText());
                String serviceImage = String.valueOf(edtServiceImage.getText());

                Map<String,Object> map = new HashMap<>();
                map.put("serviceName",serviceName);
                map.put("serviceDes",serviceDes);
                map.put("servicePrice",servicePrice);
                map.put("serviceImage",serviceImage);
                map.put("serviceId",serviceId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditServiceActivity.this,"Service Updated..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditServiceActivity.this, MainActivity.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditServiceActivity.this,"Failed to Updated Service..",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService();
            }
        });
    }

    private void deleteService(){
        databaseReference.setValue(null);
        databaseReference.removeValue();
        Toast.makeText(this,"Service Deleted...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditServiceActivity.this,MainActivity.class));
    }
}