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

public class UserItemRVAdapter extends RecyclerView.Adapter<UserItemRVAdapter.ViewHolder> {

    private final ArrayList<UserItemRVModel> userItemRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final UserItemClickInterface userItemClickInterface;
    private int position;

    public UserItemRVAdapter(ArrayList<UserItemRVModel> userItemRVModelArrayList, Context context, UserItemClickInterface userItemClickInterface) {
        this.userItemRVModelArrayList = userItemRVModelArrayList;
        this.context = context;
        this.userItemClickInterface = userItemClickInterface;
    }

    @NonNull
    @Override
    public UserItemRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.user_item_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        UserItemRVModel itemRVModel = userItemRVModelArrayList.get(position);
        holder.itemName.setText(itemRVModel.getItemName());
        holder.itemPrice.setText("Rs. " + itemRVModel.getItemPrice());
        holder.itemDescription.setText(itemRVModel.getItemDes());
        Picasso.get().load(itemRVModel.getItemImage()).into(holder.itemImageView);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userItemClickInterface.onItemClick(position);
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
        return userItemRVModelArrayList.size();
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

    public interface UserItemClickInterface {
        void onItemClick(int position);
    }
}
