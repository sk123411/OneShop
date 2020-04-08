package com.skewapps.app.oneshop;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.Database.Database;
import com.skewapps.app.oneshop.Model.Order;
import com.skewapps.app.oneshop.Model.ProductsData;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductDetail extends AppCompatActivity {

    TextView productName,productPrice,productDescription;
    ImageView product_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CounterFab btncart;
    ElegantNumberButton numberButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProductsData data;
    String foodID;
    String SliderID;
    private String TAG = this.getClass().getName();
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setFonts();
        setContentView(R.layout.activity_product_detail);
        Log.d(TAG,"im here");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.REFERENCE_PRODUCTS);


        numberButton = (ElegantNumberButton) findViewById(R.id.numberButton);
        btncart = (CounterFab) findViewById(R.id.cart_btn);

        product_image = findViewById(R.id.img_product);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExtentedBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        foodID = getIntent().getStringExtra(Common.CATEGORY_ID_SELECTED);

        if (foodID!=null){
        loadData(foodID);
        }


         btncart.setOnClickListener(new View.OnClickListener() {

            // boolean isclicked=true;
             @Override
             public void onClick(View v) {
              //   if (isclicked) {
                     new Database(getBaseContext()).addToCart(new Order(Common.CATEGORY_ID_SELECTED,
                             data.getName(), numberButton.getNumber(),
                             data.getPrice(),
                             data.getDiscount(),
                             data.getLink()
                     ));

                     Toast.makeText(ProductDetail.this, "Added to cart", Toast.LENGTH_LONG).show();
                     btncart.setCount(new Database(ProductDetail.this).getCountCart());


             }


         });



    }

    private void loadData(String ref) {

    reference.child(ref).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            data = dataSnapshot.getValue(ProductsData.class);

            Picasso.get().load(data.getLink()).into(product_image);
            productName.setText(data.getName());
            productPrice.setText(data.getPrice());
            productDescription.setText(data.getDescription());

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }


}
