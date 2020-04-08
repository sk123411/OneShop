package com.skewapps.app.oneshop.Viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skewapps.app.oneshop.Interface.ItemClickListner;
import com.skewapps.app.oneshop.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView category_image;
    public TextView category_title;
    ItemClickListner itemClickListner;
    public ImageView home_image;

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        category_image = itemView.findViewById(R.id.c_image);
        category_title = itemView.findViewById(R.id.c_title);
        home_image = itemView.findViewById(R.id.imageView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v,getAdapterPosition());
    }
}
