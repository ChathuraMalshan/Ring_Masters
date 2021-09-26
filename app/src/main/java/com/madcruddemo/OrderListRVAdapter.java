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

public class OrderListRVAdapter extends RecyclerView.Adapter<OrderListRVAdapter.ViewHolder> {

    private final ArrayList<OrderListRVModel> orderListRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final OrderListClickInterface orderListClickInterface;
    private int position;

    public OrderListRVAdapter(ArrayList<OrderListRVModel> orderListRVModelArrayList, Context context, OrderListClickInterface orderListClickInterface) {
        this.orderListRVModelArrayList = orderListRVModelArrayList;
        this.context = context;
        this.orderListClickInterface = orderListClickInterface;
    }

    @NonNull
    @Override
    public OrderListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.user_order_list_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        OrderListRVModel orderListRVModel = orderListRVModelArrayList.get(position);
        holder.serviceName.setText(orderListRVModel.getServiceName());
        holder.servicePrice.setText("Rs. " + orderListRVModel.getServicePrice());
        holder.itemName.setText(orderListRVModel.getItemName());
        holder.itemPrice.setText("Rs. " + orderListRVModel.getItemPrice());
        holder.deliveryLocation.setText(orderListRVModel.getDeliveryLocation());
        holder.deliveryPrice.setText("Rs. " + orderListRVModel.getDeliveryPrice());
        holder.paymentType.setText(orderListRVModel.getPaymentType());
        holder.paymentOffer.setText("Rs. " + orderListRVModel.getPaymentOffer());
        Picasso.get().load(orderListRVModel.getServiceImage()).into(holder.serviceImage);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderListClickInterface.onOrderItemClick(position);
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
        return orderListRVModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView serviceName;
        private final TextView servicePrice;
        private final TextView itemName;
        private final TextView itemPrice;
        private final TextView deliveryLocation;
        private final TextView deliveryPrice;
        private final TextView paymentType;
        private final TextView paymentOffer;
        private final ImageView serviceImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.orderServiceName);
            servicePrice = itemView.findViewById(R.id.orderServiceFee);
            itemName = itemView.findViewById(R.id.orderItemName);
            itemPrice = itemView.findViewById(R.id.orderItemCost);
            deliveryLocation = itemView.findViewById(R.id.orderDeliveryLocation);
            deliveryPrice = itemView.findViewById(R.id.orderDeliveryCost);
            paymentType = itemView.findViewById(R.id.orderPaymentType);
            paymentOffer = itemView.findViewById(R.id.orderPaymentOffer);
            serviceImage = itemView.findViewById(R.id.orderImageView);
        }
    }

    public interface OrderListClickInterface {
        void onOrderItemClick(int position);
    }
}
