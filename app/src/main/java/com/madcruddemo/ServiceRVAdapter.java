package com.madcruddemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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

public class ServiceRVAdapter extends RecyclerView.Adapter<ServiceRVAdapter.ViewHolder> {

    private final ArrayList<ServiceRVModel> serviceRVModelArrayList;
    private final Context context;
    int lastPos = -1;
    private final ServiceClickInterface serviceClickInterface;
    private int position;

    public ServiceRVAdapter(ArrayList<ServiceRVModel> serviceRVModelArrayList, Context context, ServiceClickInterface serviceClickInterface) {
        this.serviceRVModelArrayList = serviceRVModelArrayList;
        this.context = context;
        this.serviceClickInterface = serviceClickInterface;
    }

    @NonNull
    @Override
    public ServiceRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.service_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        ServiceRVModel serviceRVModel = serviceRVModelArrayList.get(position);
        String img = serviceRVModel.getServiceImage();
        if(img.isEmpty()){
            Log.i("img","No images to display");
        }else {
            holder.idIVServiceName.setText(serviceRVModel.getServiceName());
            holder.idIVPrice.setText("Rs. " + serviceRVModel.getServicePrice());
            Picasso.get().load(serviceRVModel.getServiceImage()).into(holder.idIVService);
            setAnimation(holder.itemView, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    serviceClickInterface.onServiceClick(position);
                }
            });
        }
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
        return serviceRVModelArrayList.size();
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

    public interface ServiceClickInterface{
        void onServiceClick(int position);
    }
}
