package com.skewapps.app.oneshop.HomeAdapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.ProductDetail;
import com.skewapps.app.oneshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.myView> {

    private ArrayList<String> imageList;
    private ArrayList<String> titlelist;
    private ArrayList<String> keyList;

    Context context;


    public homeAdapter(ArrayList<String> imageList, ArrayList<String> titlelist,ArrayList<String> keyList,Context context) {
        this.imageList = imageList;
        this.titlelist = titlelist;
        this.context = context;
        this.keyList = keyList;
    }

    @NonNull
    @Override
    public homeAdapter.myView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_home_item,viewGroup,false);

        return new myView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull homeAdapter.myView myView, final int i) {

        Picasso.get().load(imageList.get(i)).into(myView.home_image);
        myView.home_title.setText(titlelist.get(i));

        myView.home_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra(Common.CATEGORY_ID_SELECTED,String.valueOf(keyList.get(i)));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

class myView extends RecyclerView.ViewHolder{

        ImageView home_image;
        TextView home_title;
    private myView(@NonNull View itemView) {
        super(itemView);
        home_image = itemView.findViewById(R.id.home_image);
        home_title = itemView.findViewById(R.id.home_title);

    }
}
}

