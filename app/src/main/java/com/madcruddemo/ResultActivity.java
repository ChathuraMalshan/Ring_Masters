package com.madcruddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView txtResult = findViewById(R.id.txtResult);
    private ServiceRVModel serviceRVModel;
    private UserItemRVModel itemRVModel;
    private UserDeliveryRVModel deliveryRVModel;
    private UserPaymentRVModel paymentRVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        serviceRVModel = getIntent().getParcelableExtra("service");
        itemRVModel = getIntent().getParcelableExtra("item");
        deliveryRVModel = getIntent().getParcelableExtra("delivery");
        paymentRVModel = getIntent().getParcelableExtra("payment");

        String txt = serviceRVModel.getServiceName().toString();
        txtResult.setText(txt);
    }
}