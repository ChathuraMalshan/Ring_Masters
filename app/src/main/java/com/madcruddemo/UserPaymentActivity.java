package com.madcruddemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class UserPaymentActivity extends AppCompatActivity implements UserPaymentRVAdapter.UserPaymentClickInterface {
    private RecyclerView paymentRV;
    private ProgressBar ProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<UserPaymentRVModel> userPaymentRVModelArrayList;
    private RelativeLayout bottomSheetUserP;
    private UserPaymentRVAdapter userPaymentRVAdapter ;
    private FirebaseAuth mAuth;
    private ServiceRVModel serviceRVModel;
    private UserItemRVModel itemRVModel;
    private UserDeliveryRVModel deliveryRVModel;
    private int tot;
    private int dis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);

        bottomSheetUserP = findViewById(R.id.bottomSheetUserP);
        paymentRV = findViewById(R.id.paymentRV);
        ProgressBar = findViewById(R.id.ProgressBar);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments");
        userPaymentRVModelArrayList = new ArrayList<>();
        userPaymentRVAdapter = new UserPaymentRVAdapter(userPaymentRVModelArrayList,this,this);
        paymentRV.setLayoutManager(new LinearLayoutManager(this));
        paymentRV.setAdapter(userPaymentRVAdapter);

        getAllTypes();
    }

    private void getAllTypes() {
        userPaymentRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                userPaymentRVModelArrayList.add(snapshot.getValue(UserPaymentRVModel.class));
                userPaymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                userPaymentRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ProgressBar.setVisibility(View.GONE);
                userPaymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                userPaymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayBottomSheet(UserPaymentRVModel userPaymentRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_user_payment,bottomSheetUserP);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView paymentType = layout.findViewById(R.id.paymentType);
        TextView paymentOffers = layout.findViewById(R.id.paymentOffers);
        Button btnConfirmPayment = layout.findViewById(R.id.btnConfirmPayment);
        Button btnResetPayment = layout.findViewById(R.id.btnResetPayment);
        ImageView paymentImageView = layout.findViewById(R.id.paymentImageView);

        paymentType.setText(userPaymentRVModel.getPaymentType());
        paymentOffers.setText(userPaymentRVModel.getPaymentOffers());
        Picasso.get().load(userPaymentRVModel.getPaymentImage()).into(paymentImageView);

        assert btnConfirmPayment != null;
        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceRVModel = getIntent().getParcelableExtra("service");
                itemRVModel = getIntent().getParcelableExtra("item");
                deliveryRVModel = getIntent().getParcelableExtra("delivery");
                if (serviceRVModel != null && itemRVModel != null && deliveryRVModel != null) {
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

                    String DeliveryLocation = deliveryRVModel.getDeliveryLocation();
                    String DeliveryDes = deliveryRVModel.getDeliveryDes();
                    String DeliveryPrice = deliveryRVModel.getDeliveryPrice();
                    String DeliveryImageView = deliveryRVModel.getDeliveryImage();
                    String DeliveryId = deliveryRVModel.getDeliveryId();
                    UserDeliveryRVModel deliveryRVModel = new UserDeliveryRVModel(DeliveryLocation, DeliveryDes, DeliveryPrice, DeliveryImageView, DeliveryId);

                    String serviceName = serviceRVModel.getServiceName();
                    String servicePrice = serviceRVModel.getServicePrice();
                    String itemName = itemRVModel.getItemName();
                    String itemCost = itemRVModel.getItemPrice();
                    String deliveryLocation = deliveryRVModel.getDeliveryLocation();
                    String deliveryCost = deliveryRVModel.getDeliveryPrice();
                    String paymentType = userPaymentRVModel.getPaymentType();
                    String paymentOffer = userPaymentRVModel.getPaymentOffers();
                    String imageView = serviceRVModel.getServiceImage();

                    int sPrice = Integer.parseInt(servicePrice);
                    int iCost = Integer.parseInt(itemCost);
                    int dCost = Integer.parseInt(deliveryCost);
                    int pDiscount = Integer.parseInt(paymentOffer);

                    int i1 = ((sPrice + iCost + dCost) * pDiscount) / 100;
                    tot = (sPrice + iCost + dCost) - i1;
                    dis = ((sPrice + iCost + dCost) * pDiscount) / 100;
                    paymentOffer = "" + dis;
                    String total = "" + tot;

                    OrderListRVModel orderListRVModel = new OrderListRVModel(serviceName,servicePrice,itemName,itemCost,deliveryLocation,deliveryCost,paymentType,paymentOffer,imageView, total);

                    Log.d("info",orderListRVModel.getServiceName());
                    Log.d("info",orderListRVModel.getServicePrice());
                    Log.d("info",orderListRVModel.getItemName());
                    Log.d("info",orderListRVModel.getItemPrice());
                    Log.d("info",orderListRVModel.getDeliveryLocation());
                    Log.d("info",orderListRVModel.getDeliveryPrice());
                    Log.d("info",orderListRVModel.getPaymentType());
                    Log.d("info",orderListRVModel.getPaymentOffer());
                    Log.d("info",orderListRVModel.getTotal());

                    Log.i("info","Total "+tot+ "Discount "+dis);
                    Intent i = new Intent(UserPaymentActivity.this, UserOrderListActivity.class);
                    i.putExtra("order", orderListRVModel);
                    startActivity(i);
                }
            }
        });

        assert btnResetPayment != null;
        btnResetPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPaymentActivity.this,UserActivity.class));
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
            Intent i = new Intent(UserPaymentActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onUserPaymentClick(int position) {
        displayBottomSheet(userPaymentRVModelArrayList.get(position));
    }
}