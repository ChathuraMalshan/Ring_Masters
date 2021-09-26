package com.madcruddemo;

import android.os.Parcel;
import android.os.Parcelable;

public class UserItemRVModel implements Parcelable {
    private String itemName;
    private String itemDes;
    private String itemPrice;
    private String itemImage;
    private String itemId;

    public UserItemRVModel() {
    }

    public UserItemRVModel(String itemName, String itemDes, String itemPrice, String itemImage, String itemId) {
        this.itemName = itemName;
        this.itemDes = itemDes;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.itemId = itemId;
    }

    protected UserItemRVModel(Parcel in) {
        itemName = in.readString();
        itemDes = in.readString();
        itemPrice = in.readString();
        itemImage = in.readString();
        itemId = in.readString();
    }

    public static final Creator<UserItemRVModel> CREATOR = new Creator<UserItemRVModel>() {
        @Override
        public UserItemRVModel createFromParcel(Parcel in) {
            return new UserItemRVModel(in);
        }

        @Override
        public UserItemRVModel[] newArray(int size) {
            return new UserItemRVModel[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeString(itemDes);
        parcel.writeString(itemPrice);
        parcel.writeString(itemImage);
        parcel.writeString(itemId);
    }
}
