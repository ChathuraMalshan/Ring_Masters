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

import java.util.HashMap;
import java.util.Map;

public class EditDeliveryActivity extends AppCompatActivity {
    private TextInputEditText edtDeliveryLocation,edtDeliveryDescription,edtDeliveryPrice,edtDeliveryImage;
    private Button btnDeliveryUpdate,btnDeliveryDelete;
    private ProgressBar deliveryProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String deliveryId;
    private DeliveryRVModel deliveryRVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivery);

        edtDeliveryLocation = findViewById(R.id.edtDeliveryLocation);
        edtDeliveryDescription = findViewById(R.id.edtDeliveryDescription);
        edtDeliveryPrice = findViewById(R.id.edtDeliveryPrice);
        edtDeliveryImage = findViewById(R.id.edtDeliveryImage);
        btnDeliveryUpdate = findViewById(R.id.btnDeliveryUpdate);
        btnDeliveryDelete = findViewById(R.id.btnDeliveryDelete);
        deliveryProgressBar = findViewById(R.id.deliveryProgressBar);

        deliveryRVModel = getIntent().getParcelableExtra("delivery");
        if(deliveryRVModel != null){
            edtDeliveryLocation.setText(deliveryRVModel.getDeliveryLocation());
            edtDeliveryDescription.setText(deliveryRVModel.getDeliveryDes());
            edtDeliveryPrice.setText(deliveryRVModel.getDeliveryPrice());
            edtDeliveryImage.setText(deliveryRVModel.getDeliveryImage());
            deliveryId = deliveryRVModel.getDeliveryId();

        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Delivery").child(deliveryId);

        btnDeliveryUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryProgressBar.setVisibility(View.VISIBLE);
                String deliveryLocation = String.valueOf(edtDeliveryLocation.getText());
                String deliveryDescription = String.valueOf(edtDeliveryDescription.getText());
                String deliveryPrice = String.valueOf(edtDeliveryPrice.getText());
                String deliveryImage = String.valueOf(edtDeliveryImage.getText());

                Map<String,Object> map = new HashMap<>();
                map.put("deliveryLocation",deliveryLocation);
                map.put("deliveryDescription",deliveryDescription);
                map.put("deliveryPrice",deliveryPrice);
                map.put("deliveryImage",deliveryImage);
                map.put("deliveryId",deliveryId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        deliveryProgressBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditDeliveryActivity.this,"Location Updated..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditDeliveryActivity.this, DeliveryActivity.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditDeliveryActivity.this,"Failed to Updated Location..",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnDeliveryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }

    private void deleteItem(){
        databaseReference.setValue(null);
        databaseReference.removeValue();
        Toast.makeText(this,"Location Deleted...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditDeliveryActivity.this,DeliveryActivity.class));
    }
}