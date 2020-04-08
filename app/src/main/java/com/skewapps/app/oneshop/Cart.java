package com.skewapps.app.oneshop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.Database.Database;
import com.skewapps.app.oneshop.Interface.RecyclerItemTouchHelperListner;
import com.skewapps.app.oneshop.Model.Order;
import com.skewapps.app.oneshop.ShippingAddress.BottomDialog;
import com.skewapps.app.oneshop.Viewholder.CartAdapter;
import com.skewapps.app.oneshop.Viewholder.CartViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Cart extends AppCompatActivity implements RecyclerItemTouchHelperListner {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference reference;
    private TextView TextTotalPrice;
    Button orderPlacedBtn;
    public String fP;
    public static String address;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    List<Order> cart = new ArrayList<>();
    public static CartAdapter adapter;
    RelativeLayout rootLayout;
    private Dialog dialog;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/cf.otf").setFontAttrId(R.attr.fontPath).build());

        setContentView(R.layout.activity_cart);

// Create a new Places client instance.
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_customer_address);
        dialog.setTitle("Enter Details");
     //   dialog.setFeatureDrawable(1, getDrawable(R.drawable.ic_shopping_cart_black_24dp));

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("ConfirmOrders");

        recyclerView = (RecyclerView) findViewById(R.id.listcart);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);


        TextTotalPrice = findViewById(R.id.total);
        orderPlacedBtn = findViewById(R.id.placeOrder);

        orderPlacedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.size() > 0) {
//                    Intent intent = new Autocomplete.IntentBuilder(
//                            AutocompleteActivityMode.FULLSCREEN, fields)
//                              .setTypeFilter(TypeFilter.ADDRESS)
//                            .build(Cart.this);
//                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
//


                    //         showAlertDialog();
                    BottomDialog bottomDialog = new BottomDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString(Common.FINALPRICE, fP);
                    bottomDialog.setArguments(bundle);
                    bottomDialog.show(getSupportFragmentManager(), "bottom");
                    //         showAlertDialog();


//                    Intent intent = new Intent(Cart.this,FinalOrder.class);
//                    intent.putExtra(Common.FINALPRICE,fP);
//                    startActivity(intent);


                } else {
                    Toast.makeText(Cart.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadListProduct();


    }


    public void getPrice(String setPrice) {
        TextTotalPrice.setText("Rs " + setPrice);

    }


    private void loadListProduct() {


        cart = new Database(this).getcarts();
        adapter = new CartAdapter(cart, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        int total = 0;
        for (Order order : cart) {
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            TextTotalPrice.setText("Rs " + total);
            fP = String.valueOf(total);


        }
    }





    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == Common.DELETE)
            deleteCart(item.getOrder());

        return true;
    }

    private void deleteCart(int order) {

        cart.remove(order);
        new Database(this).cleanCart();

        for (Order item : cart)
            new Database(this).addToCart(item);

        loadListProduct();

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i, int adapterPosition) {

        if (viewHolder instanceof CartViewHolder) {
            String name = ((CartAdapter) recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()).getProductName();
            final Order delete = (((CartAdapter) recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()));
            final int deleteIndex = viewHolder.getAdapterPosition();
            adapter.removeItem(deleteIndex);
            new Database(getBaseContext()).removeFromCart(delete.getProductId());
            for (Order item : cart)
                new Database(this).addToCart(item);
            loadListProduct();


            int total = 0;
            List<Order> orders = new Database(getBaseContext()).getcarts();
            for (Order order : orders)
                total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            TextTotalPrice.setText("Rs" + total);
            //    Common.confirmOrders.setImage(order.getImage());


            Snackbar snackbar = Snackbar.make(rootLayout, name + "removed from cart!"
                    , Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreItem(delete, deleteIndex);
                    new Database(getBaseContext()).addToCart(delete);

                    loadListProduct();


                    int total = 0;
                    List<Order> orders = new Database(getBaseContext()).getcarts();
                    for (Order order : orders)
                        total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
                    Locale locale = new Locale("en", "US");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                    TextTotalPrice.setText(fmt.format(total));
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }



}