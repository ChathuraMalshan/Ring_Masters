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

public class EditPaymentActivity extends AppCompatActivity {
    private TextInputEditText edtPaymentType,edtPaymentOffers,edtPaymentImageView;
    private Button btnPaymentUpdate,btnPaymentDelete;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String paymentId;
    private PaymentRVModel paymentRVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment);

        edtPaymentType = findViewById(R.id.edtPaymentType);
        edtPaymentOffers = findViewById(R.id.edtPaymentOffers);
        edtPaymentImageView = findViewById(R.id.edtPaymentImageView);
        btnPaymentUpdate = findViewById(R.id.btnPaymentUpdate);
        btnPaymentDelete = findViewById(R.id.btnPaymentDelete);
        progressBar = findViewById(R.id.progressBar);

        paymentRVModel = getIntent().getParcelableExtra("payment");
        if(paymentRVModel != null){
            edtPaymentType.setText(paymentRVModel.getPaymentType());
            edtPaymentOffers.setText(paymentRVModel.getPaymentOffers());
            edtPaymentImageView.setText(paymentRVModel.getPaymentImage());
            paymentId = paymentRVModel.getPaymentId();

        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments").child(paymentId);

        btnPaymentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String paymentType = String.valueOf(edtPaymentType.getText());
                String paymentOffers = String.valueOf(edtPaymentOffers.getText());
                String paymentImageView = String.valueOf(edtPaymentImageView.getText());

                Map<String,Object> map = new HashMap<>();
                map.put("paymentType",paymentType);
                map.put("paymentOffers",paymentOffers);
                map.put("paymentImageView",paymentImageView);
                map.put("paymentId",paymentId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditPaymentActivity.this,"Payment Updated..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPaymentActivity.this, PaymentActivity.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPaymentActivity.this,"Failed to Updated Payment..",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnPaymentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }

    private void deleteItem(){
        databaseReference.setValue(null);
        databaseReference.removeValue();
        Toast.makeText(this,"Payment Deleted...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPaymentActivity.this,PaymentActivity.class));
    }
}