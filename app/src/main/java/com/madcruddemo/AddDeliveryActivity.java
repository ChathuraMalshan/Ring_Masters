package com.madcruddemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDeliveryActivity extends AppCompatActivity {

    private TextInputEditText edtDeliveryLocation,edtDeliveryDescription,edtDeliveryPrice,edtDeliveryImage;
    private Button btnAddItem;
    private ProgressBar deliveryProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String deliveryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);
        edtDeliveryLocation = findViewById(R.id.edtDeliveryLocation);
        edtDeliveryDescription = findViewById(R.id.edtDeliveryDescription);
        edtDeliveryPrice = findViewById(R.id.edtDeliveryPrice);
        edtDeliveryImage = findViewById(R.id.edtDeliveryImage);
        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        deliveryProgressBar = findViewById(R.id.deliveryProgressBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Delivery");


        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryProgressBar.setVisibility(View.VISIBLE );
                String deliveryLocation = String.valueOf(edtDeliveryLocation.getText());
                String deliveryDescription = String.valueOf(edtDeliveryDescription.getText());
                String deliveryPrice = String.valueOf(edtDeliveryPrice.getText());
                String deliveryImage = String.valueOf(edtDeliveryImage.getText());
                DatabaseReference ref = databaseReference.push();
                deliveryId = ref.getKey();
                DeliveryRVModel deliveryRVModel = new DeliveryRVModel(deliveryLocation,deliveryDescription,deliveryPrice,deliveryImage,deliveryId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        deliveryProgressBar.setVisibility(View.GONE);
                        databaseReference.child(deliveryId).setValue(deliveryRVModel);
                        Toast.makeText(AddDeliveryActivity.this,"Location Added..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddDeliveryActivity.this, DeliveryActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddDeliveryActivity.this,"Error is.."+ error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}