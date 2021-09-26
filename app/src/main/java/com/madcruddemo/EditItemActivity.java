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

public class EditItemActivity extends AppCompatActivity {
    private TextInputEditText edtItemName,edtItemDescription,edtItemPrice,edtItemImage;
    private Button btnItemUpdate,btnItemDelete;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String itemId;
    private ItemRVModel itemRVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        edtItemName = findViewById(R.id.edtItemName);
        edtItemDescription = findViewById(R.id.edtItemDescription);
        edtItemPrice = findViewById(R.id.edtItemPrice);
        edtItemImage = findViewById(R.id.edtItemImage);
        btnItemUpdate = findViewById(R.id.btnItemUpdate);
        btnItemDelete = findViewById(R.id.btnItemDelete);
        progressBar = findViewById(R.id.idProgressBar);

        itemRVModel = getIntent().getParcelableExtra("item");
        if(itemRVModel != null){
            edtItemName.setText(itemRVModel.getItemName());
            edtItemDescription.setText(itemRVModel.getItemDes());
            edtItemPrice.setText(itemRVModel.getItemPrice());
            edtItemImage.setText(itemRVModel.getItemImage());
            itemId = itemRVModel.getItemId();

        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Items").child(itemId);

        btnItemUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String itemName = String.valueOf(edtItemName.getText());
                String itemDes = String.valueOf(edtItemDescription.getText());
                String itemPrice = String.valueOf(edtItemPrice.getText());
                String itemImage = String.valueOf(edtItemImage.getText());

                Map<String,Object> map = new HashMap<>();
                map.put("itemName",itemName);
                map.put("itemDes",itemDes);
                map.put("itemPrice",itemPrice);
                map.put("itemImage",itemImage);
                map.put("itemId",itemId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditItemActivity.this,"Item Updated..",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditItemActivity.this, ItemActivity.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditItemActivity.this,"Failed to Updated Item..",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }

    private void deleteItem(){
        databaseReference.setValue(null);
        databaseReference.removeValue();
        Toast.makeText(this,"Item Deleted...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditItemActivity.this,ItemActivity.class));
    }
}