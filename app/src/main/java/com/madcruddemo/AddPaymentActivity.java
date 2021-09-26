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

public class AddPaymentActivity extends AppCompatActivity {

    private TextInputEditText edtPaymentType,edtPaymentOffers,edtPaymentImageView;
    private Button btnAddPayment;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String paymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        edtPaymentType = findViewById(R.id.edtPaymentType);
        edtPaymentOffers = findViewById(R.id.edtPaymentOffers);
        edtPaymentImageView = findViewById(R.id.edtPaymentImageView);
        btnAddPayment = (Button) findViewById(R.id.btnAddPayment);
        progressBar = findViewById(R.id.progressBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments");


        btnAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE );
                String paymentType = String.valueOf(edtPaymentType.getText());
                String paymentOffers = String.valueOf(edtPaymentOffers.getText());
                String paymentImageView = String.valueOf(edtPaymentImageView.getText());
                DatabaseReference ref = databaseReference.push();
                paymentId = ref.getKey();
                PaymentRVModel paymentRVModel = new PaymentRVModel(paymentType,paymentOffers,paymentImageView,paymentId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        databaseReference.child(paymentId).setValue(paymentRVModel);
                        Toast.makeText(AddPaymentActivity.this,"Payment Added..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddPaymentActivity.this, PaymentActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddPaymentActivity.this,"Error is.."+ error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}