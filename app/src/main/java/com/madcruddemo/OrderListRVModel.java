package com.madcruddemo;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderListRVModel implements Parcelable {

    private String serviceName;
    private String servicePrice;
    private String itemName;
    private String itemPrice;
    private String deliveryLocation;
    private String deliveryPrice;
    private String paymentType;
    private String paymentOffer;
    private String serviceImage;
    private String total;

    public OrderListRVModel() {
    }



    public OrderListRVModel(String serviceName, String servicePrice, String itemName, String itemPrice, String deliveryLocation, String deliveryPrice, String paymentType, String paymentOffer, String serviceImage,String total) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.deliveryLocation = deliveryLocation;
        this.deliveryPrice = deliveryPrice;
        this.paymentType = paymentType;
        this.paymentOffer = paymentOffer;
        this.serviceImage = serviceImage;
        this.total = total;
    }

    protected OrderListRVModel(Parcel in) {
        serviceName = in.readString();
        servicePrice = in.readString();
        itemName = in.readString();
        itemPrice = in.readString();
        deliveryLocation = in.readString();
        deliveryPrice = in.readString();
        paymentType = in.readString();
        paymentOffer = in.readString();
        serviceImage = in.readString();
        total = in.readString();
    }

    public static final Creator<OrderListRVModel> CREATOR = new Creator<OrderListRVModel>() {
        @Override
        public OrderListRVModel createFromParcel(Parcel in) {
            return new OrderListRVModel(in);
        }

        @Override
        public OrderListRVModel[] newArray(int size) {
            return new OrderListRVModel[size];
        }
    };

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaymentOffer() {
        return paymentOffer;
    }

    public void setPaymentOffer(String paymentOffer) {
        this.paymentOffer = paymentOffer;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serviceName);
        parcel.writeString(servicePrice);
        parcel.writeString(itemName);
        parcel.writeString(itemPrice);
        parcel.writeString(deliveryLocation);
        parcel.writeString(deliveryPrice);
        parcel.writeString(paymentType);
        parcel.writeString(paymentOffer);
        parcel.writeString(serviceImage);
        parcel.writeString(total);

    }
}
