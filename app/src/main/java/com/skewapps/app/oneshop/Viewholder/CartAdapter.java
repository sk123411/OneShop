package com.skewapps.app.oneshop.Viewholder;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.skewapps.app.oneshop.Cart;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.Database.Database;
import com.skewapps.app.oneshop.Model.Order;
import com.skewapps.app.oneshop.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> orders = new ArrayList<>();
    private Cart cart;
    public String imageLink;

    public CartAdapter(List<Order> orderList, Cart cart) {
        this.orders = orderList ;
        this.cart = cart;
    }



    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(cart);
        View view = layoutInflater.inflate(R.layout.layout_cart_item,viewGroup,false);

        return new CartViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int i) {


        cartViewHolder.img_cart_count.setNumber(orders.get(i).getQuantity());

        cartViewHolder.img_cart_count.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Order order = orders.get(i);
                order.setQuantity(String.valueOf(newValue));
                new Database(cart).updateCart(order);
                int total = 0;
                List<Order> orders = new Database(cart).getcarts();
                for(Order item:orders){
                    total+=(Integer.parseInt(item.getPrice()))*(Integer.parseInt(item.getQuantity()));

                    cart.getPrice(String.valueOf(total));

                    cart.fP = String.valueOf(total);



                }
            }
        });

        cartViewHolder.txt_price.setText("Rs " + orders.get(i).getPrice());
        cartViewHolder.txt_cart_name.setText(orders.get(i).getProductName());
        Picasso.get().load(orders.get(i).getImage()).into(cartViewHolder.cart_item_image);
        Common.imageLink = orders.get(i).getImage();




    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public Order getItem(int pos){

       return orders.get(pos);

    }

    public void removeItem(int pos){
        orders.remove(pos);
        notifyItemRemoved(pos);
    }

    public void restoreItem(Order item,int pos){

        orders.add(pos,item);
        notifyItemInserted(pos);
    }
}
