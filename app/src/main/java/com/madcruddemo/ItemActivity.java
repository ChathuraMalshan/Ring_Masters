package com.madcruddemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ItemActivity extends AppCompatActivity implements ItemRVAdapter.ItemClickInterface {
    private RecyclerView itemRecycleView;
    private ProgressBar ProgressBar;
    private FloatingActionButton itemAddFab;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<ItemRVModel> itemRVModelArrayList;
    private RelativeLayout bottomSheetItem;
    private ItemRVAdapter itemRVAdapter ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        bottomSheetItem = findViewById(R.id.bottomSheetItem);
        itemRecycleView = findViewById(R.id.itemRecycleView);
        ProgressBar = findViewById(R.id.ProgressBar);
        itemAddFab = findViewById(R.id.itemAddFab);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Items");
        itemRVModelArrayList = new ArrayList<>();
        itemRVAdapter = new ItemRVAdapter(itemRVModelArrayList,this,this);
        itemRecycleView.setLayoutManager(new LinearLayoutManager(this));
        itemRecycleView.setAdapter(itemRVAdapter);


        assert itemAddFab != null;
        itemAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ItemActivity.this,AddItemActivity.class));
            }
        });
        getAllItems();
    }

    private void getAllItems() {
        itemRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                itemRVModelArrayList.add(snapshot.getValue(ItemRVModel.class));
                itemRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                itemRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ProgressBar.setVisibility(View.GONE);
                itemRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProgressBar.setVisibility(View.GONE);
                itemRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onItemClick(int position) {
        displayBottomSheet(itemRVModelArrayList.get(position));
    }
    private void displayBottomSheet(ItemRVModel itemRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_item,bottomSheetItem);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView itemName = layout.findViewById(R.id.ItemName);
        TextView itemDescription = layout.findViewById(R.id.ItemDescription);
        TextView itemPrice = layout.findViewById(R.id.ItemPrice);
        Button btnEditItem = layout.findViewById(R.id.btnEditItem);
        Button btnViewItem = layout.findViewById(R.id.btnViewItem);
        ImageView itemImage = layout.findViewById(R.id.ItemImage);

        itemName.setText(itemRVModel.getItemName());
        itemDescription.setText(itemRVModel.getItemDes());
        itemPrice.setText(itemRVModel.getItemPrice());
        Picasso.get().load(itemRVModel.getItemImage()).into(itemImage);


        assert btnEditItem != null;
        btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ItemActivity.this, EditItemActivity.class);
                i.putExtra("item",itemRVModel);
                startActivity(i);
            }
        });

        assert btnViewItem != null;
        btnViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(itemRVModel.getItemImage()));
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
            Intent i = new Intent(ItemActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}