package com.madcruddemo;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceRVModel implements Parcelable {
    private String serviceName;
    private String serviceDes;
    private String servicePrice;
    private String serviceImage;
    private String serviceId;

    public ServiceRVModel() {
    }

    public ServiceRVModel(String serviceName, String serviceDes, String servicePrice, String serviceImage, String serviceId) {
        this.serviceName = serviceName;
        this.serviceDes = serviceDes;
        this.servicePrice = servicePrice;
        this.serviceImage = serviceImage;
        this.serviceId = serviceId;
    }


    protected ServiceRVModel(Parcel in) {
        serviceName = in.readString();
        serviceDes = in.readString();
        servicePrice = in.readString();
        serviceImage = in.readString();
        serviceId = in.readString();
    }

    public static final Creator<ServiceRVModel> CREATOR = new Creator<ServiceRVModel>() {
        @Override
        public ServiceRVModel createFromParcel(Parcel in) {
            return new ServiceRVModel(in);
        }

        @Override
        public ServiceRVModel[] newArray(int size) {
            return new ServiceRVModel[size];
        }
    };

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDes() {
        return serviceDes;
    }

    public void setServiceDes(String serviceDes) {
        this.serviceDes = serviceDes;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serviceName);
        parcel.writeString(serviceDes);
        parcel.writeString(servicePrice);
        parcel.writeString(serviceImage);
        parcel.writeString(serviceId);
    }
}
