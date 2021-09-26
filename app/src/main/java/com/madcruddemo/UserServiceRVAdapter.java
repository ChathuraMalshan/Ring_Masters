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

public class UserServiceRVAdapter extends RecyclerView.Adapter<UserServiceRVAdapter.ViewHolder> {

    private final ArrayList<UserServiceRVModel> userServiceRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final UserServiceClickInterface userServiceClickInterface;
    private int position;

    public UserServiceRVAdapter(ArrayList<UserServiceRVModel> userServiceRVModelArrayList, Context context, UserServiceClickInterface userServiceClickInterface) {
        this.userServiceRVModelArrayList = userServiceRVModelArrayList;
        this.context = context;
        this.userServiceClickInterface = userServiceClickInterface;
    }

    @NonNull
    @Override
    public UserServiceRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.user_service_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserServiceRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        UserServiceRVModel userServiceRVModel = userServiceRVModelArrayList.get(position);
        holder.idIVServiceName.setText(userServiceRVModel.getServiceName());
        holder.idIVPrice.setText("Rs. " + userServiceRVModel.getServicePrice());
        Picasso.get().load(userServiceRVModel.getServiceImage()).into(holder.idIVService);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userServiceClickInterface.onUserServiceClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return userServiceRVModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView idIVServiceName;
        private final TextView idIVPrice;
        private final ImageView idIVService;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idIVServiceName = itemView.findViewById(R.id.idSIServiceName);
            idIVPrice = itemView.findViewById(R.id.idSIPrice);
            idIVService = itemView.findViewById(R.id.idSIService);
        }
    }

    public interface UserServiceClickInterface{
        void onUserServiceClick(int position);
    }
}
