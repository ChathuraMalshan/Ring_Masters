package com.madcruddemo;

import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryRVModel implements Parcelable {
    private String deliveryLocation;
    private String deliveryDes;
    private String deliveryPrice;
    private String deliveryImage;
    private String deliveryId;

    public DeliveryRVModel() {
    }

    public DeliveryRVModel(String deliveryLocation, String deliveryDes, String deliveryPrice, String deliveryImage, String deliveryId) {
        this.deliveryLocation = deliveryLocation;
        this.deliveryDes = deliveryDes;
        this.deliveryPrice = deliveryPrice;
        this.deliveryImage = deliveryImage;
        this.deliveryId = deliveryId;
    }

    protected DeliveryRVModel(Parcel in) {
        deliveryLocation = in.readString();
        deliveryDes = in.readString();
        deliveryPrice = in.readString();
        deliveryImage = in.readString();
        deliveryId = in.readString();
    }

    public static final Creator<DeliveryRVModel> CREATOR = new Creator<DeliveryRVModel>() {
        @Override
        public DeliveryRVModel createFromParcel(Parcel in) {
            return new DeliveryRVModel(in);
        }

        @Override
        public DeliveryRVModel[] newArray(int size) {
            return new DeliveryRVModel[size];
        }
    };

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getDeliveryDes() {
        return deliveryDes;
    }

    public void setDeliveryDes(String deliveryDes) {
        this.deliveryDes = deliveryDes;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getDeliveryImage() {
        return deliveryImage;
    }

    public void setDeliveryImage(String deliveryImage) {
        this.deliveryImage = deliveryImage;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(deliveryLocation);
        parcel.writeString(deliveryDes);
        parcel.writeString(deliveryPrice);
        parcel.writeString(deliveryImage);
        parcel.writeString(deliveryId);
    }
}
