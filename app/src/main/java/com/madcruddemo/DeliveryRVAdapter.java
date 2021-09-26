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

public class DeliveryRVAdapter extends RecyclerView.Adapter<DeliveryRVAdapter.ViewHolder> {

    private final ArrayList<DeliveryRVModel> deliveryRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final DeliveryClickInterface deliveryClickInterface;
    private int position;

    public DeliveryRVAdapter(ArrayList<DeliveryRVModel> deliveryRVModelArrayList, Context context, DeliveryClickInterface deliveryClickInterface) {
        this.deliveryRVModelArrayList = deliveryRVModelArrayList;
        this.context = context;
        this.deliveryClickInterface = deliveryClickInterface;
    }

    @NonNull
    @Override
    public DeliveryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.delivery_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        DeliveryRVModel deliveryRVModel = deliveryRVModelArrayList.get(position);
        holder.deliveryLocation.setText(deliveryRVModel.getDeliveryLocation());
        holder.deliveryPrice.setText("Rs. " + deliveryRVModel.getDeliveryPrice());
        holder.deliveryDes.setText(deliveryRVModel.getDeliveryDes());
        Picasso.get().load(deliveryRVModel.getDeliveryImage()).into(holder.deliveryImageView);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryClickInterface.onDeliveryClick(position);
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
        return deliveryRVModelArrayList.size();
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

    public interface DeliveryClickInterface {
        void onDeliveryClick(int position);
    }
}
