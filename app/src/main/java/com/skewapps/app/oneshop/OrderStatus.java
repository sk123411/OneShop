package com.skewapps.app.oneshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.app.Dialog;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.HomeAdapters.orderDetailAdapter;
import com.skewapps.app.oneshop.Interface.ItemClickListner;
import com.skewapps.app.oneshop.Model.ConfirmOrders;
import com.skewapps.app.oneshop.Model.ProductsData;
import com.skewapps.app.oneshop.Viewholder.OrderViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager manager;
    FirebaseRecyclerOptions<ConfirmOrders> options;
    FirebaseRecyclerAdapter<ConfirmOrders, OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference reference;
    String toolbarName;
    String orderNumber;
    orderDetailAdapter orderDetailAdapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setFonts();
        setContentView(R.layout.activity_order_status);

        toolbarName = getIntent().getStringExtra(Common.CATEGORY_NAME);
        orderNumber = getIntent().getStringExtra(Common.ORDER_NUMBER);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarName);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("ConfirmOrders");

        recyclerView = findViewById(R.id.listorders);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        loadOrders();

    }

    private void loadOrders() {

        Query query = reference.child("New_Order").orderByChild(Common.ORDER_NUMBER_ID).equalTo(orderNumber);
        options = new FirebaseRecyclerOptions.Builder<ConfirmOrders>().setQuery(query,ConfirmOrders.class).build();
        adapter = new FirebaseRecyclerAdapter<ConfirmOrders, OrderViewHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position, @NonNull ConfirmOrders model) {

                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                holder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                holder.txtOrderAddress.setText(model.getAddress());
                holder.txtOrderUsername.setText(model.getPhone());
                holder.textAmount.setText("Order Amount "+" " +"$"+model.getPrice());
                holder.Courier.setText("Courier Name: " + model.getCourier());
                holder.Tracking.setText("Tracking Number: " + model.getTracking());
                holder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position) {

                        Dialog dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.dialog_main_detail);
                        final RecyclerView recyclerViewD = dialog.findViewById(R.id.order_detail_recyler);
                        recyclerViewD.setLayoutManager(new LinearLayoutManager(view.getContext()));

                        adapter.getRef(position).child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final ArrayList<String> imagelist = new ArrayList<>();
                                final ArrayList<String> quantityList = new ArrayList<>();
                                final ArrayList<String> priceList = new ArrayList<>();

                                int counter = 0;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    String image = snapshot.child("image").getValue(String.class);
                                    String quantity = snapshot.child("quantity").getValue(String.class);
                                    String price = snapshot.child("price").getValue(String.class);

                                    imagelist.add(image);
                                    quantityList.add(quantity);
                                    priceList.add(price);
                                    counter++;


                                }

                                orderDetailAdapter = new orderDetailAdapter(imagelist, quantityList, priceList, OrderStatus.this);
                                recyclerViewD.setAdapter(orderDetailAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        dialog.show();
                    }
                });





                if (adapter.getItem(holder.getAdapterPosition()).getStatus().equals("2")){
                    holder.cancelOrder.setText("SHIPPED");
                }
                if (adapter.getItem(holder.getAdapterPosition()).getStatus().equals("3")){
                    holder.cancelOrder.setText("Cancel Request");
                  //  holder.cancelOrder.setBackgroundColor(getColor(R.color.colorYellow));
                }
                if (adapter.getItem(holder.getAdapterPosition()).getStatus().equals("0")){
                    holder.cancelOrder.setText("Cancel Order");
                }



                holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (adapter.getItem(holder.getAdapterPosition()).getStatus().equals("0")){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle("Are you sure you want to cancel order?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteOrder(adapter.getRef(holder.getAdapterPosition()).getKey());
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.show();

                        } else if (adapter.getItem(holder.getAdapterPosition()).getStatus().equals("3")){

                            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle("Are you sure you want to cancel request?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CancelRequest(adapter.getRef(holder.getAdapterPosition()).getKey());
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.show();
                        } else if (adapter.getItem(holder.getAdapterPosition()).getStatus().equals("2")) {
                            Toast.makeText(OrderStatus.this,"Order is arriving at your doorstep",Toast.LENGTH_SHORT).show();

                        }  else {
                            Toast.makeText(OrderStatus.this,"You cannot delete this order",Toast.LENGTH_SHORT).show();
                        }
                    }


                });



            }

            private void CancelRequest(String key) {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("status","0");
                reference.child(key).updateChildren(hashMap);

            }

            private void deleteOrder(String key) {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("status","3");
                reference.child(key).updateChildren(hashMap);

        //    reference.child(key).updateChildren()

            }
            private String convertCodeToStatus(String status) {
                if (status.equals("0"))
                    return "Placed";
                else if(status.equals("1"))
                    return "On my way";
                else if (status.equals("2"))
                    return "Shipped";
                else if (status.equals("3"))
                    return "Processing Cancellation Request";
                else if (status.equals("4"))
                    return "Order Cancelled";
                else
                    return "Status Not Available";

            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order,viewGroup,false);



                return new OrderViewHolder(v);
            }
        };

            setAdapter();

    }





    private void setAdapter() {


        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null)
            adapter.stopListening();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

}
