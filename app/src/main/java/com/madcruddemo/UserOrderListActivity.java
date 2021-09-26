package com.madcruddemo;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserOrderListActivity extends AppCompatActivity{

    private ServiceRVModel serviceRVModel;
    private UserItemRVModel itemRVModel;
    private UserDeliveryRVModel deliveryRVModel;
    private UserPaymentRVModel paymentRVModel;

    private RecyclerView orderRV;
    private ProgressBar idProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private OrderListRVModel orderListRVModel;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);
        serviceRVModel = getIntent().getParcelableExtra("service");
        orderListRVModel = getIntent().getParcelableExtra("order");
        itemRVModel = getIntent().getParcelableExtra("item");
        deliveryRVModel = getIntent().getParcelableExtra("delivery");
        paymentRVModel = getIntent().getParcelableExtra("payment");

        orderRV = findViewById(R.id.orderRV);
        idProgressBar = findViewById(R.id.ProgressBar);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Orders");

//        orderListRVModelArrayList = new ArrayList<>();
//        orderListRVAdapter = new OrderListRVAdapter(orderListRVModelArrayList,this,this);
//        orderRV.setLayoutManager(new LinearLayoutManager(this));
//        orderRV.setAdapter(orderListRVAdapter);

        TextView orderServiceName = findViewById(R.id.orderServiceName);
        TextView orderServicePrice = findViewById(R.id.orderServiceFee);
        TextView orderItemName = findViewById(R.id.orderItemName);
        TextView orderItemCost = findViewById(R.id.orderItemCost);
        TextView orderDeliveryLocation = findViewById(R.id.orderDeliveryLocation);
        TextView orderDeliveryCost = findViewById(R.id.orderDeliveryCost);
        TextView orderPaymentType = findViewById(R.id.orderPaymentType);
        TextView orderPaymentOffer = findViewById(R.id.orderPaymentOffer);
        TextView orderTotal = findViewById(R.id.orderTotal);
        ImageView orderImageView = findViewById(R.id.orderImageView);
        Button btnConfirmPurchase = findViewById(R.id.btnConfirmPurchase);
        Button btnCancelPurchase = findViewById(R.id.btnCancelPurchase);

        orderServiceName.setText(orderListRVModel.getServiceName());
        orderServicePrice.setText(orderListRVModel.getServicePrice());
        orderItemName.setText(orderListRVModel.getItemName());
        orderItemCost.setText("Rs. " + orderListRVModel.getItemPrice());
        orderDeliveryLocation.setText(orderListRVModel.getDeliveryLocation());
        orderDeliveryCost.setText("Rs. " + orderListRVModel.getDeliveryPrice());
        orderPaymentType.setText(orderListRVModel.getPaymentType());
        orderPaymentOffer.setText(""+ orderListRVModel.getPaymentOffer());
        orderTotal.setText(orderListRVModel.getTotal());
        Picasso.get().load(orderListRVModel.getServiceImage()).into(orderImageView);
        String ServiceName = orderListRVModel.getServiceName();
        String ServicePrice = orderListRVModel.getServicePrice();
        String ItemName = orderListRVModel.getItemName();
        String ItemCost = orderListRVModel.getItemPrice();
        String DeliveryLocation = orderListRVModel.getDeliveryLocation();
        String DeliveryCost = orderListRVModel.getDeliveryPrice();
        String PaymentType = orderListRVModel.getPaymentType();
        String PaymentOffer = orderListRVModel.getPaymentOffer();
        String ImageView = orderListRVModel.getServiceImage();
        String total = orderListRVModel.getTotal();
        Toast.makeText(UserOrderListActivity.this,"Total " + total + "Discount - " + PaymentOffer,Toast.LENGTH_SHORT).show();

        btnConfirmPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idProgressBar.setVisibility(View.VISIBLE );

                OrderListRVModel orderListRVModel = new OrderListRVModel(ServiceName,ServicePrice,ItemName,ItemCost,DeliveryLocation,DeliveryCost,PaymentType,PaymentOffer,ImageView, total);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        idProgressBar.setVisibility(View.GONE);
                        databaseReference.child(ServiceName).setValue(orderListRVModel);
                        Toast.makeText(UserOrderListActivity.this,"Transaction Successful..",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserOrderListActivity.this, UserActivity.class);
                        i.putExtra("order",orderListRVModel);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserOrderListActivity.this,"Error is.."+ error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCancelPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserOrderListActivity.this,UserActivity.class));
            }
        });

//        getAllServices();

    }

//    private void getAllServices(){
//        orderListRVModelArrayList.clear();
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                idProgressBar.setVisibility(View.GONE);
//                orderListRVModelArrayList.add(snapshot.getValue(OrderListRVModel.class));
//                orderListRVAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                idProgressBar.setVisibility(View.GONE);
//                orderListRVAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                idProgressBar.setVisibility(View.GONE);
//                orderListRVAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                idProgressBar.setVisibility(View.GONE);
//                orderListRVAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void displayBottomSheet(OrderListRVModel orderListRVModel){
//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//        View layout = LayoutInflater.from(this).inflate(R.layout.user_order_list_rv_item,bottomSheetOrderRL);
//        bottomSheetDialog.setContentView(layout);
//        bottomSheetDialog.setCancelable(false);
//        bottomSheetDialog.setCanceledOnTouchOutside(true);
//        bottomSheetDialog.show();
//
//
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.idLogOut) {
//            Toast.makeText(this, "User logged out..", Toast.LENGTH_SHORT).show();
//            mAuth.signOut();
//            Intent i = new Intent(UserOrderListActivity.this, LoginActivity.class);
//            startActivity(i);
//            this.finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onOrderItemClick(int position) {
//        displayBottomSheet(orderListRVModelArrayList.get(position));
//    }
}