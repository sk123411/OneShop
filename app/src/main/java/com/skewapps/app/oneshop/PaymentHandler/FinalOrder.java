package com.skewapps.app.oneshop.PaymentHandler;

import android.app.Activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.Database.Database;
import com.skewapps.app.oneshop.Home;
import com.skewapps.app.oneshop.Model.ConfirmOrders;
import com.skewapps.app.oneshop.Model.Order;
import com.skewapps.app.oneshop.R;

import java.util.ArrayList;
import java.util.List;

public class FinalOrder extends AppCompatActivity implements PaymentStatusListener {


    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String price;

    List<Order> orderList = new ArrayList<>();
    private String UPI_ID="YOUR_UPI_ID";
    private String PAYEE_NAME="YOUR_PAYEE_NAME";
    private String TRANSACTION_ID = "UNIQUE_TRANSACTION_ID";
    private String TRANSACTION_REF_ID = "UNIQUE_TRANSACTION_REF_ID";
    private String TRANSACTION_AMOUNT = "1";
    private String TRANSACTION_DES = "TRANSACTION_DESCRIPTION";

    /**
     * Initialize the Google Pay API on creation of the activity
     *
     * @see Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);



        if (getIntent()!=null){
            price = getIntent().getStringExtra(Common.FINALPRICE);
        } else {
            Toast.makeText(this,"Unable to process your order",Toast.LENGTH_SHORT).show();
            finish();
        }


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("ConfirmOrders");
        orderList = new Database(this).getcarts();



        initItemUI();
        Button payButton = findViewById(R.id.easyPayButton);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                        .with(FinalOrder.this)
                        .setPayeeVpa(UPI_ID)
                        .setPayeeName(PAYEE_NAME)
                        .setTransactionId(TRANSACTION_ID)
                        .setTransactionRefId(TRANSACTION_REF_ID)
                        .setDescription(TRANSACTION_DES)
                        .setAmount(TRANSACTION_AMOUNT)
                        .build();


                easyUpiPayment.startPayment();
                easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) FinalOrder.this);


            }
        });



        Button TestOrder = findViewById(R.id.testOrder);
        TestOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendCustomerDetails();
            }
        });

    }





    private void sendCustomerDetails(){
        ConfirmOrders confirmOrders = new ConfirmOrders(
               Common.FO_PHONE,Common.FO_NAME,Common.FO_FULLADRESS,Common.FO_PINCODE,Common.FO_CITY,Common.FO_STATE,Common.FO_COUNTRY
                ,price, orderList,"0","N.A","N.A");

        reference.child("New_Order").child(Common.FO_PHONE).setValue(confirmOrders);


                Toast.makeText(getApplicationContext(),"Thank You!, Order PLaced", Toast.LENGTH_SHORT).show();
              new Database(getApplication()).cleanCart();

              startActivity(new Intent(this, Home.class));


    }



    private void initItemUI() {

       TextView priceText = (TextView)findViewById(R.id.text_item_price);
       priceText.setText("Rs " + price);


    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        Toast.makeText(this,"transaction completed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionSuccess() {
        Toast.makeText(this,"transaction success",Toast.LENGTH_LONG).show();
        sendCustomerDetails();

    }

    @Override
    public void onTransactionSubmitted() {
        Toast.makeText(this,"transaction submitted",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onTransactionFailed() {
        Toast.makeText(this,"transaction failed",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(this,"transaction cancelled",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAppNotFound() {
        Toast.makeText(this,"app not found",Toast.LENGTH_LONG).show();

    }
}
