package com.madcruddemo;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemRVModel implements Parcelable {
    private String itemName;
    private String itemDes;
    private String itemPrice;
    private String itemImage;
    private String itemId;

    public ItemRVModel() {
    }

    public ItemRVModel(String itemName, String itemDes, String itemPrice, String itemImage, String itemId) {
        this.itemName = itemName;
        this.itemDes = itemDes;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.itemId = itemId;
    }

    protected ItemRVModel(Parcel in) {
        itemName = in.readString();
        itemDes = in.readString();
        itemPrice = in.readString();
        itemImage = in.readString();
        itemId = in.readString();
    }

    public static final Creator<ItemRVModel> CREATOR = new Creator<ItemRVModel>() {
        @Override
        public ItemRVModel createFromParcel(Parcel in) {
            return new ItemRVModel(in);
        }

        @Override
        public ItemRVModel[] newArray(int size) {
            return new ItemRVModel[size];
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
