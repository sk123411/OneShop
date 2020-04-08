package com.skewapps.app.oneshop.Viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.Interface.ItemClickListner;
import com.skewapps.app.oneshop.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

    public TextView txt_cart_name,txt_price;
    public ElegantNumberButton img_cart_count;
    public ImageView cart_item_image;
    private ItemClickListner itemClickListner;
    public RelativeLayout viewBckground;
    public LinearLayout foreGround;


    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_name = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_price);
        img_cart_count = itemView.findViewById(R.id.cart_item_numberButton);
        cart_item_image = itemView.findViewById(R.id.cart_item_image);
        viewBckground = itemView.findViewById(R.id.viewBackground);
        foreGround = (LinearLayout) itemView.findViewById(R.id.viewForeground);

       itemView.setOnCreateContextMenuListener(this);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }


}