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

public class UserDeliveryRVAdapter extends RecyclerView.Adapter<UserDeliveryRVAdapter.ViewHolder> {

    private final ArrayList<UserDeliveryRVModel> userDeliveryRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final UserDeliveryClickInterface userDeliveryClickInterface;
    private int position;

    public UserDeliveryRVAdapter(ArrayList<UserDeliveryRVModel> userDeliveryRVModelArrayList, Context context, UserDeliveryClickInterface userDeliveryClickInterface) {
        this.userDeliveryRVModelArrayList = userDeliveryRVModelArrayList;
        this.context = context;
        this.userDeliveryClickInterface = userDeliveryClickInterface;
    }

    @NonNull
    @Override
    public UserDeliveryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.user_delivery_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDeliveryRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        UserDeliveryRVModel userDeliveryRVModel = userDeliveryRVModelArrayList.get(position);
        holder.deliveryLocation.setText(userDeliveryRVModel.getDeliveryLocation());
        holder.deliveryPrice.setText("Rs. " + userDeliveryRVModel.getDeliveryPrice());
        holder.deliveryDes.setText(userDeliveryRVModel.getDeliveryDes());
        Picasso.get().load(userDeliveryRVModel.getDeliveryImage()).into(holder.deliveryImageView);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDeliveryClickInterface.onUserDeliveryClick(position);
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
        return userDeliveryRVModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView deliveryLocation;
        private final TextView deliveryDes;
        private final TextView deliveryPrice;
        private final ImageView deliveryImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deliveryLocation = itemView.findViewById(R.id.deliveryLocation);
            deliveryPrice = itemView.findViewById(R.id.deliveryPrice);
            deliveryImageView = itemView.findViewById(R.id.deliveryImageView);
            deliveryDes = itemView.findViewById(R.id.deliveryDes);
        }
    }

    public interface UserDeliveryClickInterface {
        void onUserDeliveryClick(int position);
    }
}
