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

public class DeliveryActivity extends AppCompatActivity implements DeliveryRVAdapter.DeliveryClickInterface{

    private RecyclerView deliveryRv;
    private ProgressBar idProgressBar;
    private FloatingActionButton addDeliveryFab;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<DeliveryRVModel> deliveryRVModelArrayList;
    private RelativeLayout bottomSheetDelivery;
    private DeliveryRVAdapter deliveryRVAdapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        bottomSheetDelivery = findViewById(R.id.bottomSheetDelivery);
        deliveryRv = findViewById(R.id.deliveryRv);
        idProgressBar = findViewById(R.id.idProgressBar);
        addDeliveryFab = findViewById(R.id.addDeliveryFab);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Delivery");
        deliveryRVModelArrayList = new ArrayList<>();
        deliveryRVAdapter = new DeliveryRVAdapter(deliveryRVModelArrayList,this,this);
        deliveryRv.setLayoutManager(new LinearLayoutManager(this));
        deliveryRv.setAdapter(deliveryRVAdapter);
        addDeliveryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeliveryActivity.this,AddDeliveryActivity.class));
            }
        });
        getAllDeliveries();
    }

    private void getAllDeliveries(){
        deliveryRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                deliveryRVModelArrayList.add(snapshot.getValue(DeliveryRVModel.class));
                deliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                deliveryRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                idProgressBar.setVisibility(View.GONE);
                deliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                deliveryRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayBottomSheet(DeliveryRVModel deliveryRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_delivery,bottomSheetDelivery);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView deliveryLocation = layout.findViewById(R.id.deliveryLocation);
        TextView deliveryDes = layout.findViewById(R.id.deliveryDes);
        TextView deliveryPrice = layout.findViewById(R.id.deliveryPrice);
        Button editBtn = bottomSheetDialog.findViewById(R.id.btnEditDelivery);
        Button viewDetailsBtn = bottomSheetDialog.findViewById(R.id.btnViewDelivery);
        ImageView deliveryImageView = layout.findViewById(R.id.deliveryImageView);

        deliveryLocation.setText(deliveryRVModel.getDeliveryLocation());
        deliveryDes.setText(deliveryRVModel.getDeliveryDes());
        deliveryPrice.setText(deliveryRVModel.getDeliveryPrice());
        Picasso.get().load(deliveryRVModel.getDeliveryImage()).into(deliveryImageView);

        assert editBtn != null;
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeliveryActivity.this, EditDeliveryActivity.class);
                i.putExtra("delivery",deliveryRVModel);
                startActivity(i);
            }
        });

        assert viewDetailsBtn != null;
        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(deliveryRVModel.getDeliveryImage()));
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
            Intent i = new Intent(DeliveryActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeliveryClick(int position) {
        displayBottomSheet(deliveryRVModelArrayList.get(position));
    }
}