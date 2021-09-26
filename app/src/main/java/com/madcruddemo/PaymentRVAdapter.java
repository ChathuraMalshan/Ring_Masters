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

public class PaymentRVAdapter extends RecyclerView.Adapter<PaymentRVAdapter.ViewHolder> {

    private final ArrayList<PaymentRVModel> paymentRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final PaymentClickInterface paymentClickInterface;
    private int position;

    public PaymentRVAdapter(ArrayList<PaymentRVModel> paymentRVModelArrayList, Context context, PaymentClickInterface paymentClickInterface) {
        this.paymentRVModelArrayList = paymentRVModelArrayList;
        this.context = context;
        this.paymentClickInterface = paymentClickInterface;
    }

    @NonNull
    @Override
    public PaymentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.payment_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        PaymentRVModel paymentRVModel = paymentRVModelArrayList.get(position);
        holder.paymentType.setText(paymentRVModel.getPaymentType());
        holder.paymentOffers.setText(paymentRVModel.getPaymentOffers());
        Picasso.get().load(paymentRVModel.getPaymentImage()).into(holder.paymentImageView);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentClickInterface.onPaymentClick(position);
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
        return paymentRVModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView paymentType;
        private final TextView paymentOffers;
        private final ImageView paymentImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentType = itemView.findViewById(R.id.paymentType);
            paymentOffers = itemView.findViewById(R.id.paymentOffers);
            paymentImageView = itemView.findViewById(R.id.paymentImageView);
        }
    }

    public interface PaymentClickInterface {
        void onPaymentClick(int position);
    }
}
