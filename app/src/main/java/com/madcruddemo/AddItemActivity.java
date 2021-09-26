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

public class AddItemActivity extends AppCompatActivity {

    private TextInputEditText edtItemName,edtItemDescription,edtItemPrice,edtItemImage;
    private Button btnAddItem;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        edtItemName = findViewById(R.id.edtItemName);
        edtItemDescription = findViewById(R.id.edtItemDescription);
        edtItemPrice = findViewById(R.id.edtItemPrice);
        edtItemImage = findViewById(R.id.edtItemImage);
        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        progressBar = findViewById(R.id.itemProgressBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Items");


        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE );
                String itemName = edtItemName.getText().toString();
                String itemDes = edtItemDescription.getText().toString();
                String itemPrice = edtItemPrice.getText().toString();
                String itemImage = edtItemImage.getText().toString();
                DatabaseReference ref = databaseReference.push();
                itemId = ref.getKey();
                ItemRVModel itemRVModel = new ItemRVModel(itemName,itemDes,itemPrice,itemImage,itemId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        databaseReference.child(itemId).setValue(itemRVModel);
                        Toast.makeText(AddItemActivity.this,"Item Added..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddItemActivity.this, ItemActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddItemActivity.this,"Error is.."+ error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}