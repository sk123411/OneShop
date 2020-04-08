package com.skewapps.app.oneshop.Viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skewapps.app.oneshop.Interface.ItemClickListner;
import com.skewapps.app.oneshop.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId,txtOrderStatus,txtOrderUsername,txtOrderAddress;
    public Button cancelOrder;
    public TextView textAmount;
    public TextView Courier,Tracking;
    private ItemClickListner itemClickListner;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderAddress = itemView.findViewById(R.id.order_address);
        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderUsername = itemView.findViewById(R.id.order_username);
        cancelOrder = itemView.findViewById(R.id.cancelOrder);
        textAmount  = itemView.findViewById(R.id.orderAmount);
        Courier = itemView.findViewById(R.id.orderCourier);
        Tracking = itemView.findViewById(R.id.orderTracking);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
    itemClickListner.onClick(v,getAdapterPosition());
    }
}
