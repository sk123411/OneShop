package com.skewapps.app.oneshop.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.skewapps.app.oneshop.Model.ConfirmOrders;
import com.skewapps.app.oneshop.R;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Common {
    public static final String DELETE = "Delete" ;
    public static final String FINALPRICE = "final_price";
    public static final String REFERENCE_PRODUCTS = "ProductsData" ;
    public static final String REFERENCE_ID ="id" ;
    public static  String ORDER_NUMBER;
    public static  String ORDER_NUMBER_ID= "phone";
    public static String CATEGORY_NAME;
    public static String CATEGORY_ID_SELECTED;
    public static final String HOME_ID_NAME="Home";
    public static ConfirmOrders confirmOrders;
    public static String imageLink;


    public static String FO_NAME;
    public static String FO_CITY;
    public static String FO_STATE;
    public static String FO_COUNTRY;
    public static String FO_PINCODE;
    public static String FO_FULLADRESS;
    public static String FO_PHONE;
    public static String ACT_SLIDER;
    public static final String ACT_SLIDER_KEY="act0901";

    public static final String SLIDER_IMG_URL_1="https://res.cloudinary.com/dlmt4hsgw/image/upload/slider_image_1.png";
    public static final String SLIDER_IMG_URL_2="https://res.cloudinary.com/dlmt4hsgw/image/upload/slider_image_2.png";
    public static final String SLIDER_IMG_URL_3="https://res.cloudinary.com/dlmt4hsgw/image/upload/slider_image_3.png";
    public static final String SLIDER_IMG_URL_4="https://res.cloudinary.com/dlmt4hsgw/image/upload/slider_image_4.png";
    public static final String REFERENCE_POPULAR_KEY="001";
    public static final String REFERENCE_LATEST_KEY="101";
    public static String CURRENT_REFERENCE;
    public static final String REFERENCE_DEALS_KEY="201";
    public static final String REFERENCE_SALE01_KEY="2012";
    public static final String REFERENCE_SALE02_KEY="2013";
    public static final String REFERENCE_SALE03_KEY="2014";
    public static final String REFERENCE_SALE04_KEY="2015";
    public static String GOOGLE_ADDRESS;

    public static String FAV="favo";

    public static String[] imageUrls = new String[]{  "https://res.cloudinary.com/dlmt4hsgw/image/upload/v1556120074/dp83.jpg",
            "https://res.cloudinary.com/dlmt4hsgw/image/upload/v1556119324/dp80.jpg",
            "https://cdn.pixabay.com/photo/2017/12/24/09/09/road-3036620_960_720.jpg",
    };
    public static String CATEGORY_HOME_ID_SELECTED;
    public static final String CATEGORY_POPULAR="Popular Choices";
    public static final String CATEGORY_LATEST="Latest Arrivals";
    public static final String CATEGORY_DEALS="Deals of the day";
    public static final String SHOP="Shop by Category";
    public static final String ORDERS="My Orders";
    public static final String REFERENCE_FAV="favorite";
    public static String REF_OFFER;
    public static final String REF_OFFER_KEY="offer";
    public static String REF_REVIEW;
    public static final String REF_REVIEW_KEY="review";
    public static String WEB_URL;


    public static void setFonts(){
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/one_font.otf").setFontAttrId(R.attr.fontPath).build());

}




    public static boolean isConnectedToInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        );
        if (connectivityManager!=null){

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info!=null){

                for (int i=0;i<info.length;i++){

                    if (info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;

                }
            }
        }

        return false;

    }






    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }




}
