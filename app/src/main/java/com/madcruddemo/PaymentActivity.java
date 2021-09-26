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

public class PaymentActivity extends AppCompatActivity implements PaymentRVAdapter.PaymentClickInterface {
    private RecyclerView paymentRV;
    private ProgressBar ProgressBar;
    private FloatingActionButton addPaymentFab;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PaymentRVModel> paymentRVModelArrayList;
    private RelativeLayout bottomSheetPayment;
    private PaymentRVAdapter paymentRVAdapter ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        bottomSheetPayment = findViewById(R.id.bottomSheetPayment);
        paymentRV = findViewById(R.id.paymentRV);
        ProgressBar = findViewById(R.id.ProgressBar);
        addPaymentFab = findViewById(R.id.addPaymentFab);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments");
        paymentRVModelArrayList = new ArrayList<>();
        paymentRVAdapter = new PaymentRVAdapter(paymentRVModelArrayList,this,this);
        paymentRV.setLayoutManager(new LinearLayoutManager(this));
        paymentRV.setAdapter(paymentRVAdapter);

        assert addPaymentFab != null;
        addPaymentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this,AddPaymentActivity.class));
            }
        });
        getAllTypes();
    }

    private void getAllTypes() {
        paymentRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                paymentRVModelArrayList.add(snapshot.getValue(PaymentRVModel.class));
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ProgressBar.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayBottomSheet(PaymentRVModel paymentRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_payment,bottomSheetPayment);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView paymentType = layout.findViewById(R.id.paymentType);
        TextView paymentOffers = layout.findViewById(R.id.paymentOffers);
        Button btnEditPayment = layout.findViewById(R.id.btnEditPayment);
        Button btnDeletePayment = layout.findViewById(R.id.btnDeletePayment);
        ImageView paymentImageView = layout.findViewById(R.id.paymentImageView);

        paymentType.setText(paymentRVModel.getPaymentType());
        paymentOffers.setText(paymentRVModel.getPaymentOffers());
        Picasso.get().load(paymentRVModel.getPaymentImage()).into(paymentImageView);

        assert btnEditPayment != null;
        btnEditPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentActivity.this, EditPaymentActivity.class);
                i.putExtra("payment",paymentRVModel);
                startActivity(i);
            }
        });

        assert btnDeletePayment != null;
        btnDeletePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(paymentRVModel.getPaymentImage()));
                startActivity(i);
            }
        });
    }

    private void deletePayment() {
        databaseReference.setValue(null);
        databaseReference.removeValue();
        Toast.makeText(this,"Payment Deleted...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,PaymentActivity.class));
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
            Intent i = new Intent(PaymentActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentClick(int position) {
        displayBottomSheet(paymentRVModelArrayList.get(position));
    }
}