package com.madcruddemo;

import android.os.Parcel;
import android.os.Parcelable;

public class UserPaymentRVModel implements Parcelable {
    private String paymentType;
    private String paymentOffers;
    private String paymentImage;
    private String paymentId;

    public UserPaymentRVModel() {
    }

    public UserPaymentRVModel(String paymentType, String paymentOffers, String paymentImage, String paymentId) {
        this.paymentType = paymentType;
        this.paymentOffers = paymentOffers;
        this.paymentImage = paymentImage;
        this.paymentId = paymentId;
    }

    protected UserPaymentRVModel(Parcel in) {
        paymentType = in.readString();
        paymentOffers = in.readString();
        paymentImage = in.readString();
        paymentId = in.readString();
    }

    public static final Creator<UserPaymentRVModel> CREATOR = new Creator<UserPaymentRVModel>() {
        @Override
        public UserPaymentRVModel createFromParcel(Parcel in) {
            return new UserPaymentRVModel(in);
        }

        @Override
        public UserPaymentRVModel[] newArray(int size) {
            return new UserPaymentRVModel[size];
        }
    };

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentOffers() {
        return paymentOffers;
    }

    public void setPaymentOffers(String paymentOffers) {
        this.paymentOffers = paymentOffers;
    }

    public String getPaymentImage() {
        return paymentImage;
    }

    public void setPaymentImage(String paymentImage) {
        this.paymentImage = paymentImage;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(paymentType);
        parcel.writeString(paymentOffers);
        parcel.writeString(paymentImage);
        parcel.writeString(paymentId);
    }
}
