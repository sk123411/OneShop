package com.skewapps.app.oneshop.HomeAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skewapps.app.oneshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class orderDetailAdapter extends RecyclerView.Adapter<orderDetailAdapter.myView> {

    private ArrayList<String> imageList;
    private ArrayList<String> quantitylist;
    private ArrayList<String> priceList;
    Context context;


    public orderDetailAdapter(ArrayList<String> imageList, ArrayList<String> quantitylist, ArrayList<String> priceList, Context context) {
        this.imageList = imageList;
        this.quantitylist = quantitylist;
        this.priceList = priceList;
        this.context = context;
    }

    @NonNull
    @Override
    public orderDetailAdapter.myView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_order_detail,viewGroup,false);

        return new myView(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull orderDetailAdapter.myView myView, final int i) {

        Picasso.get().load(imageList.get(i)).into(myView.order_image);
        myView.order_quantity.setText(quantitylist.get(i));
        myView.order_price.setText("$ "+priceList.get(i));





//
//        Picasso.get().load(imageList.get(i)).into(myView.home_image);
//        myView.home_title.setText(titlelist.get(i));
//
//        myView.home_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog dialog = new Dialog(v.getContext());
//                dialog.setContentView(R.layout.dialog_offer);
//                TextView offer_title = dialog.findViewById(R.id.offer_title);
//                TextView offer_des = dialog.findViewById(R.id.offer_des);
//                TextView offer_steps = dialog.findViewById(R.id.offer_steps);
//                ImageView offer_image = dialog.findViewById(R.id.offer_image);
//
//                Picasso.get().load(imageList.get(i)).into(offer_image);
//                offer_title.setText(titlelist.get(i));
//                offer_des.setText(descList.get(i));
//                offer_steps.setText(stepslist.get(i));
//
//                dialog.show();


//            }
//        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class myView extends RecyclerView.ViewHolder{

        ImageView order_image;
        TextView order_quantity;
        TextView order_price;
        private myView(@NonNull View itemView) {
            super(itemView);
            order_image = itemView.findViewById(R.id.order_detail_img);
            order_quantity = itemView.findViewById(R.id.order_detail_quantity);
            order_price = itemView.findViewById(R.id.order_detail_price);


        }
    }
}