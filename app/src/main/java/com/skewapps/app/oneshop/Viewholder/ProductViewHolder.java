package com.skewapps.app.oneshop.Viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skewapps.app.oneshop.Interface.ItemClickListner;
import com.skewapps.app.oneshop.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView product_image;
    private ItemClickListner itemClickListner;
    public TextView name;
    public ImageView imageFav;
    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        product_image = itemView.findViewById(R.id.product_image);
        name = itemView.findViewById(R.id.product_name_pr);
        imageFav = itemView.findViewById(R.id.favorites);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v,getAdapterPosition());
    }
}
