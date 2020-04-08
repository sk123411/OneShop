package com.skewapps.app.oneshop;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rey.material.widget.ImageView;
import com.skewapps.app.oneshop.Common.Common;
import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter {
    private android.content.Context context;
    private String[] imageUrls;

    ViewPagerAdapter(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        Picasso.get()
                .load(imageUrls[position])
                .fit()
                .centerCrop()
                .into(imageView);
        container.addView(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (position){
                    case 0:
                        Toast.makeText(context,"clicked at " + position,Toast.LENGTH_SHORT).show();
                       startIntent(Common.REFERENCE_SALE01_KEY);

                        break;
                    case 1:
                        Toast.makeText(context,"clicked at " + position,Toast.LENGTH_SHORT).show();
                        startIntent(Common.REFERENCE_SALE02_KEY);




                        break;
                    case 2:
                        Toast.makeText(context,"clicked at " + position,Toast.LENGTH_SHORT).show();
                        startIntent(Common.REFERENCE_SALE03_KEY);


                        break;
                    case 3:
                        Toast.makeText(context,"clicked at " + position,Toast.LENGTH_SHORT).show();

                        startIntent(Common.REFERENCE_SALE04_KEY);

                        break;




                }

            }
        });


        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    public void del(){
        Common.deleteCache(context);
    }


    private void startIntent(String key){

        Intent intent = new Intent(context, Products.class);
        intent.putExtra(Common.CATEGORY_ID_SELECTED,key);
        context.startActivity(intent);
    }
}
