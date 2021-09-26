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

public class UserActivity extends AppCompatActivity implements ServiceRVAdapter.ServiceClickInterface{

    private RecyclerView serviceRV;
    private ProgressBar idProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<ServiceRVModel> serviceRVModelArrayList;
    private RelativeLayout bottomSheetUserS;
    private ServiceRVAdapter serviceRVAdapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        bottomSheetUserS = findViewById(R.id.bottomSheetUserS);
        serviceRV = findViewById(R.id.idRVServices);
        idProgressBar = findViewById(R.id.idProgressBar);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Services");
        serviceRVModelArrayList = new ArrayList<>();
        serviceRVAdapter = new ServiceRVAdapter(serviceRVModelArrayList,this,this);
        serviceRV.setLayoutManager(new LinearLayoutManager(this));
        serviceRV.setAdapter(serviceRVAdapter);

        getAllServices();
    }

    private void getAllServices(){
        serviceRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                serviceRVModelArrayList.add(snapshot.getValue(ServiceRVModel.class));
                serviceRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                serviceRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                idProgressBar.setVisibility(View.GONE);
                serviceRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                idProgressBar.setVisibility(View.GONE);
                serviceRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onServiceClick(int position) {
        displayBottomSheet(serviceRVModelArrayList.get(position));
    }

    private void displayBottomSheet(ServiceRVModel serviceRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_user_service,bottomSheetUserS);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView serviceName = layout.findViewById(R.id.idTVServiceName);
        TextView serviceDescription = layout.findViewById(R.id.idTVServiceDescription);
        TextView servicePrice = layout.findViewById(R.id.idTVServicePrice);
        ImageView serviceImage = layout.findViewById(R.id.idIVService);
        Button btnServiceSelect = layout.findViewById(R.id.btnServiceSelect);

        serviceName.setText(serviceRVModel.getServiceName());
        serviceDescription.setText(serviceRVModel.getServiceDes());
        servicePrice.setText("Rs. " + serviceRVModel.getServicePrice());
        Picasso.get().load(serviceRVModel.getServiceImage()).into(serviceImage);

        btnServiceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserActivity.this, UserItemActivity.class);
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
            Intent i = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}