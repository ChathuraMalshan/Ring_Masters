package com.madcruddemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ViewHolder> {

    private final ArrayList<ItemRVModel> itemRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final ItemClickInterface itemClickInterface;
    private int position;

    public ItemRVAdapter(ArrayList<ItemRVModel> itemRVModelArrayList, Context context, ItemClickInterface itemClickInterface) {
        this.itemRVModelArrayList = itemRVModelArrayList;
        this.context = context;
        this.itemClickInterface = itemClickInterface;
    }

    @NonNull
    @Override
    public ItemRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.item_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        ItemRVModel itemRVModel = itemRVModelArrayList.get(position);
        holder.itemName.setText(itemRVModel.getItemName());
        holder.itemPrice.setText("Rs. " + itemRVModel.getItemPrice());
        holder.itemDescription.setText(itemRVModel.getItemDes());
        Picasso.get().load(itemRVModel.getItemImage()).into(holder.itemImageView);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickInterface.onItemClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int postion){
        if(postion>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = postion;
        }
    }

    @Override
    public int getItemCount() {
        return itemRVModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView itemName;
        private final TextView itemDescription;
        private final TextView itemPrice;
        private final ImageView itemImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemDescription = itemView.findViewById(R.id.itemDescription);
        }
    }

    public interface ItemClickInterface {
        void onItemClick(int position);
    }
}
