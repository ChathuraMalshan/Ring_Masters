package com.madcruddemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserItemActivity extends AppCompatActivity implements UserItemRVAdapter.UserItemClickInterface {
    private RecyclerView itemRecycleView;
    private ProgressBar ProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<UserItemRVModel> userItemRVModelArrayList;
    private RelativeLayout bottomSheetUserI;
    private UserItemRVAdapter userItemRVAdapter ;
    private FirebaseAuth mAuth;
    private ServiceRVModel serviceRVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item);

        bottomSheetUserI = findViewById(R.id.bottomSheetUserI);
        itemRecycleView = findViewById(R.id.itemRecycleView);
        ProgressBar = findViewById(R.id.ProgressBar);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Items");
        userItemRVModelArrayList = new ArrayList<>();
        userItemRVAdapter = new UserItemRVAdapter(userItemRVModelArrayList,this,this);
        itemRecycleView.setLayoutManager(new LinearLayoutManager(this));
        itemRecycleView.setAdapter(userItemRVAdapter);


        getAllItems();
    }

    private void getAllItems() {
        userItemRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                userItemRVModelArrayList.add(snapshot.getValue(UserItemRVModel.class));
                userItemRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                userItemRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ProgressBar.setVisibility(View.GONE);
                userItemRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                userItemRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onItemClick(int position) {
        serviceRVModel = getIntent().getParcelableExtra("service");
        if(serviceRVModel != null) {
            String ServiceName = serviceRVModel.getServiceName();
            String ServiceDes = serviceRVModel.getServiceDes();
            String ServicePrice = serviceRVModel.getServicePrice();
            String ServiceImage = serviceRVModel.getServiceImage();
            String serviceId = serviceRVModel.getServiceId();
            ServiceRVModel serviceRVModel = new ServiceRVModel(ServiceName, ServiceDes, ServicePrice, ServiceImage, serviceId);
            displayBottomSheet(userItemRVModelArrayList.get(position), serviceRVModel);
        }
    }
    private void displayBottomSheet(UserItemRVModel userItemRVModel, ServiceRVModel serviceRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_user_item,bottomSheetUserI);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView itemName = layout.findViewById(R.id.itemName);
        TextView itemDescription = layout.findViewById(R.id.itemDescription);
        TextView itemPrice = layout.findViewById(R.id.itemPrice);
        Button btnItemSelect = layout.findViewById(R.id.btnItemSelect);
        ImageView itemImage = layout.findViewById(R.id.itemImageView);

        itemName.setText(userItemRVModel.getItemName());
        itemDescription.setText(userItemRVModel.getItemDes());
        itemPrice.setText("Rs. " + userItemRVModel.getItemPrice());
        Picasso.get().load(userItemRVModel.getItemImage()).into(itemImage);


        btnItemSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(UserItemActivity.this, UserDeliveryActivity.class);
                    i.putExtra("item",userItemRVModel);
                    i.putExtra("service",serviceRVModel);
                    startActivity(i);
                }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idLogOut) {
            Toast.makeText(this, "User logged out..", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent i = new Intent(UserItemActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}