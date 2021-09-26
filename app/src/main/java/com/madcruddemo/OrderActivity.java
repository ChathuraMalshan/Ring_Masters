//package com.madcruddemo;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class OrderActivity extends AppCompatActivity implements OrderListRVAdapter.OrderListClickInterface {
//
//    private ServiceRVModel serviceRVModel;
//    private UserItemRVModel itemRVModel;
//    private UserDeliveryRVModel deliveryRVModel;
//    private UserPaymentRVModel paymentRVModel;
//
//    private RecyclerView orderRV;
//    private ProgressBar idProgressBar;
//    private FirebaseDatabase firebaseDatabase;
//    private DatabaseReference databaseReference;
//    private ArrayList<OrderListRVModel> orderListRVModelArrayList;
//    private RelativeLayout bottomSheetOrderRL;
//    private OrderListRVAdapter orderListRVAdapter;
//    private FirebaseAuth mAuth;
//
//    public OrderActivity() {
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order);
//        serviceRVModel = getIntent().getParcelableExtra("service");
//        itemRVModel = getIntent().getParcelableExtra("item");
//        deliveryRVModel = getIntent().getParcelableExtra("delivery");
//        paymentRVModel = getIntent().getParcelableExtra("payment");
//
//        bottomSheetOrderRL = findViewById(R.id.bottomSheetOrderRL);
//        orderRV = findViewById(R.id.orderRV);
//        idProgressBar = findViewById(R.id.ProgressBar);
//        mAuth = FirebaseAuth.getInstance();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("Orders");
//        orderListRVModelArrayList = new ArrayList<>();
//        orderListRVAdapter = new OrderListRVAdapter(orderListRVModelArrayList,this,this);
//        orderRV.setLayoutManager(new LinearLayoutManager(this));
//        orderRV.setAdapter(orderListRVAdapter);
//
//        getAllServices();
//
//    }
//
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
//        TextView orderServiceName = layout.findViewById(R.id.orderServiceName);
//        TextView orderServicePrice = layout.findViewById(R.id.orderServiceFee);
//        TextView orderItemName = layout.findViewById(R.id.orderItemName);
//        TextView orderItemCost = layout.findViewById(R.id.orderItemCost);
//        TextView orderDeliveryLocation = layout.findViewById(R.id.orderDeliveryLocation);
//        TextView orderDeliveryCost = layout.findViewById(R.id.orderDeliveryCost);
//        TextView orderPaymentType = layout.findViewById(R.id.orderPaymentType);
//        TextView orderPaymentOffer = layout.findViewById(R.id.orderPaymentOffer);
//        ImageView orderImageView = layout.findViewById(R.id.orderImageView);
//        Button btnConfirmPurchase = layout.findViewById(R.id.btnConfirmPurchase);
//        Button btnCancelPurchase = layout.findViewById(R.id.btnCancelPurchase);
//
//        orderServiceName.setText(orderListRVModel.getServiceName());
//        orderServicePrice.setText(orderListRVModel.getServicePrice());
//        orderItemName.setText(orderListRVModel.getItemName());
//        orderItemCost.setText("Rs. " + orderListRVModel.getItemPrice());
//        orderDeliveryLocation.setText(orderListRVModel.getDeliveryLocation());
//        orderDeliveryCost.setText("Rs. " + orderListRVModel.getDeliveryPrice());
//        orderPaymentType.setText(orderListRVModel.getPaymentType());
//        orderPaymentOffer.setText("Rs. " + orderListRVModel.getPaymentOffer());
//        Picasso.get().load(orderListRVModel.getServiceImage()).into(orderImageView);
//
//
//        btnConfirmPurchase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                idProgressBar.setVisibility(View.VISIBLE );
//                String ServiceName = orderListRVModel.getServiceName();
//                String ServicePrice = orderListRVModel.getServicePrice();
//                String ItemName = orderListRVModel.getItemName();
//                String ItemCost = orderListRVModel.getItemPrice();
//                String DeliveryLocation = orderListRVModel.getDeliveryLocation();
//                String DeliveryCost = orderListRVModel.getDeliveryPrice();
//                String PaymentType = orderListRVModel.getPaymentType();
//                String PaymentOffer = orderListRVModel.getPaymentType();
//                String ImageView = orderListRVModel.getPaymentOffer();
//
//                OrderListRVModel orderListRVModel = new OrderListRVModel(ServiceName,ServicePrice,ItemName,ItemCost,DeliveryLocation,DeliveryCost,PaymentType,PaymentOffer,ImageView);
//
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        idProgressBar.setVisibility(View.GONE);
//                        databaseReference.child(ServiceName).setValue(orderListRVModel);
//                        Toast.makeText(OrderActivity.this,"Transaction Successful..",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(OrderActivity.this, UserActivity.class);
//                        i.putExtra("order",orderListRVModel);
//                        startActivity(i);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(OrderActivity.this,"Error is.."+ error.toString(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//        btnCancelPurchase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(OrderActivity.this,UserActivity.class));
//            }
//        });
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
//            Intent i = new Intent(OrderActivity.this, LoginActivity.class);
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
//}