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

public class UserPaymentRVAdapter extends RecyclerView.Adapter<UserPaymentRVAdapter.ViewHolder> {

    private final ArrayList<UserPaymentRVModel> userPaymentRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final UserPaymentClickInterface userPaymentClickInterface;
    private int position;

    public UserPaymentRVAdapter(ArrayList<UserPaymentRVModel> userPaymentRVModelArrayList, Context context, UserPaymentClickInterface userPaymentClickInterface) {
        this.userPaymentRVModelArrayList = userPaymentRVModelArrayList;
        this.context = context;
        this.userPaymentClickInterface = userPaymentClickInterface;
    }

    @NonNull
    @Override
    public UserPaymentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.user_payment_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPaymentRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        UserPaymentRVModel userPaymentRVModel = userPaymentRVModelArrayList.get(position);
        holder.paymentType.setText(userPaymentRVModel.getPaymentType());
        holder.paymentOffers.setText(userPaymentRVModel.getPaymentOffers());
        Picasso.get().load(userPaymentRVModel.getPaymentImage()).into(holder.paymentImageView);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPaymentClickInterface.onUserPaymentClick(position);
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
        return userPaymentRVModelArrayList.size();
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

    public interface UserPaymentClickInterface {
        void onUserPaymentClick(int position);
    }
}
