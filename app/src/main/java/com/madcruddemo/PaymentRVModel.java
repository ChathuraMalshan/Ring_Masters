package com.madcruddemo;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentRVModel implements Parcelable {
    private String paymentType;
    private String paymentOffers;
    private String paymentImage;
    private String paymentId;

    public PaymentRVModel() {
    }

    public PaymentRVModel(String paymentType, String paymentOffers, String paymentImage, String paymentId) {
        this.paymentType = paymentType;
        this.paymentOffers = paymentOffers;
        this.paymentImage = paymentImage;
        this.paymentId = paymentId;
    }

    protected PaymentRVModel(Parcel in) {
        paymentType = in.readString();
        paymentOffers = in.readString();
        paymentImage = in.readString();
        paymentId = in.readString();
    }

    public static final Creator<PaymentRVModel> CREATOR = new Creator<PaymentRVModel>() {
        @Override
        public PaymentRVModel createFromParcel(Parcel in) {
            return new PaymentRVModel(in);
        }

        @Override
        public PaymentRVModel[] newArray(int size) {
            return new PaymentRVModel[size];
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
