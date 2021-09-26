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

public class UserDeliveryActivity extends AppCompatActivity implements UserDeliveryRVAdapter.UserDeliveryClickInterface{

    private RecyclerView deliveryRv;
    private ProgressBar idProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<UserDeliveryRVModel> userDeliveryRVModelArrayList;
    private RelativeLayout bottomSheetUserD;
    private UserDeliveryRVAdapter userDeliveryRVAdapter;
    private FirebaseAuth mAuth;
    private ServiceRVModel serviceRVModel;
    private UserItemRVModel itemRVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delivery);

        bottomSheetUserD = findViewById(R.id.bottomSheetUserD);
        deliveryRv = findViewById(R.id.deliveryRv);
        idProgressBar = findViewById(R.id.idProgressBar);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Delivery");
        userDeliveryRVModelArrayList = new ArrayList<>();
        userDeliveryRVAdapter = new UserDeliveryRVAdapter(userDeliveryRVModelArrayList,this,this);
        deliveryRv.setLayoutManager(new LinearLayoutManager(this));
        deliveryRv.setAdapter(userDeliveryRVAdapter);

        getAllDeliveries();
    }

    private void getAllDeliveries(){
        userDeliveryRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                userDeliveryRVModelArrayList.add(snapshot.getValue(UserDeliveryRVModel.class));
                userDeliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                userDeliveryRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                idProgressBar.setVisibility(View.GONE);
                userDeliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                userDeliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayBottomSheet(UserDeliveryRVModel userDeliveryRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_user_delivery,bottomSheetUserD);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView deliveryLocation = layout.findViewById(R.id.deliveryLocation);
        TextView deliveryDes = layout.findViewById(R.id.deliveryDes);
        TextView deliveryPrice = layout.findViewById(R.id.deliveryPrice);
        Button btnConfirm = layout.findViewById(R.id.btnEditDelivery);
        Button btnClear = bottomSheetDialog.findViewById(R.id.btnViewDelivery);
        ImageView deliveryImageView = layout.findViewById(R.id.deliveryImageView);

        deliveryLocation.setText(userDeliveryRVModel.getDeliveryLocation());
        deliveryDes.setText(userDeliveryRVModel.getDeliveryDes());
        deliveryPrice.setText("Rs. " + userDeliveryRVModel.getDeliveryPrice());
        Picasso.get().load(userDeliveryRVModel.getDeliveryImage()).into(deliveryImageView);

        assert btnConfirm != null;
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceRVModel = getIntent().getParcelableExtra("service");
                itemRVModel = getIntent().getParcelableExtra("item");

                if (serviceRVModel != null && itemRVModel != null) {
                    String ServiceName = serviceRVModel.getServiceName();
                    String ServiceDes = serviceRVModel.getServiceDes();
                    String ServicePrice = serviceRVModel.getServicePrice();
                    String ServiceImage = serviceRVModel.getServiceImage();
                    String serviceId = serviceRVModel.getServiceId();
                    ServiceRVModel serviceRVModel = new ServiceRVModel(ServiceName, ServiceDes, ServicePrice, ServiceImage, serviceId);

                    String ItemName = itemRVModel.getItemName();
                    String ItemDescription = itemRVModel.getItemDes();
                    String ItemPrice = itemRVModel.getItemPrice();
                    String ItemImage = itemRVModel.getItemImage();
                    String itemId = itemRVModel.getItemId();
                    UserItemRVModel itemRVModel = new UserItemRVModel(ItemName, ItemDescription, ItemPrice, ItemImage, itemId);
                    Intent i = new Intent(UserDeliveryActivity.this, UserPaymentActivity.class);
                    i.putExtra("item", itemRVModel);
                    i.putExtra("service", serviceRVModel);
                    i.putExtra("delivery", userDeliveryRVModel);
                    startActivity(i);
                }
            }
        });

        assert btnClear != null;
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDeliveryActivity.this,UserDeliveryActivity.class));
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
            Intent i = new Intent(UserDeliveryActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserDeliveryClick(int position) {
        displayBottomSheet(userDeliveryRVModelArrayList.get(position));
    }
}