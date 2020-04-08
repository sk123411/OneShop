package com.skewapps.app.oneshop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.Database.Database;
import com.skewapps.app.oneshop.HomeAdapters.homeAdapter;

import com.skewapps.app.oneshop.Model.ProductsData;
import com.skewapps.app.oneshop.Viewholder.ProductViewHolder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private  FirebaseDatabase database;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewLatest;
    private RecyclerView recyclerViewDeals;
    private  RecyclerView recyclerViewSaved;
    private RecyclerView recyclerViewSearch;
    private RecyclerView recyclerViewOffer;
    private RecyclerView recyclerViewReview;
    private Database localDB;
    private  NotificationBadge badge;
    private  FirebaseRecyclerOptions<ProductsData> options;
    private FirebaseRecyclerAdapter<ProductsData, ProductViewHolder> adapter;


    private TextView fullname;
    private TextView savedItemText;
    private homeAdapter homeAdapter;
    private Button seeAllLatest;
    private Button seeAllPopular;
    private Button seeAllDeals;
    private ViewPager viewPager;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setFonts();
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.REFERENCE_PRODUCTS);

        localDB = new Database(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.pager_slider);
        ViewPagerAdapter PagerAdapter = new ViewPagerAdapter(this, Common.imageUrls);
        viewPager.setAdapter(PagerAdapter);
        PagerAdapter.del();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(), 2000, 4000);

        seeAllPopular = findViewById(R.id.popu);
        seeAllLatest = findViewById(R.id.seeLatID);
        seeAllDeals = findViewById(R.id.seeDealsID);
        savedItemText = findViewById(R.id.savedItemText);
        seeAllPopular.setOnClickListener(this);
        seeAllLatest.setOnClickListener(this);
        seeAllDeals.setOnClickListener(this);

        recyclerViewSearch = findViewById(R.id.searchT);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));


        recyclerView = findViewById(R.id.recyclerPopular);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewLatest = findViewById(R.id.recyclerLatest);
        recyclerViewLatest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewDeals = findViewById(R.id.recyclerDeals);
        recyclerViewDeals.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        fullname = view.findViewById(R.id.txtFullName);
        fullname.setText("Shivam");

        if (Common.isConnectedToInternet(this)) {
            PopularAdapter();
            LatestAdapter();
            DealsAdapter();
        } else {

            Toast.makeText(Home.this, "Please check your internet connection",
                    Toast.LENGTH_LONG).show();
        }

    }


    private void PopularAdapter() {
        final ArrayList<String> imageList = new ArrayList<>();
        final ArrayList<String> titlelist = new ArrayList<>();
        final ArrayList<String> keyList = new ArrayList<>();

        reference.orderByChild(Common.REFERENCE_ID).equalTo(Common.REFERENCE_POPULAR_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    String image = snapshot.child("link").getValue(String.class);
                    String title = snapshot.child("name").getValue(String.class);
                    String key = snapshot.getKey();
                    Log.d("KEY>>>>>>>>","DATA " + key);

                    imageList.add(image);
                    titlelist.add(title);
                    keyList.add(key);

                    counter++;


                    if (counter == 3)
                        break;

                }

                homeAdapter = new homeAdapter(imageList, titlelist, keyList, Home.this);
                recyclerView.setAdapter(homeAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void SearchAdapter(final String search) {
        final ArrayList<String> imageList = new ArrayList<>();
        final ArrayList<String> titlelist = new ArrayList<>();
        final ArrayList<String> keyList = new ArrayList<>();

        DatabaseReference referencePopu = database.getReference();
        referencePopu.child("ProductsData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                titlelist.clear();
                imageList.clear();
                //     urlList.clear();
                recyclerViewSearch.removeAllViews();
                int counter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String snapshotKey = snapshot.getKey();

                    //         String full_name = snapshot.child("title").getValue(String.class);

                    String image = snapshot.child("link").getValue(String.class);
                    String title = snapshot.child("name").getValue(String.class);
                    String key = snapshot.getKey();

                    //    String url = snapshot.child("url").getValue(String.class);

                    if (title.toLowerCase().contains(search.toLowerCase())) {
                        //      titleList.add(full_name);
                        imageList.add(image);
                        titlelist.add(title);
                        keyList.add(key);
                        //   urlList.add(url);
                        counter++;


                    }


                    // }


                }

                homeAdapter = new homeAdapter(imageList, titlelist, keyList, Home.this);
                recyclerViewSearch.setAdapter(homeAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void LatestAdapter() {

        final ArrayList<String> listLatest = new ArrayList<>();
        final ArrayList<String> titlelist = new ArrayList<>();
        final ArrayList<String> keyList = new ArrayList<>();
        reference.orderByChild(Common.REFERENCE_ID).equalTo(Common.REFERENCE_LATEST_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String image = snapshot.child("link").getValue(String.class);
                    String title = snapshot.child("name").getValue(String.class);
                    String key = snapshot.getKey();

                    listLatest.add(image);
                    titlelist.add(title);
                    keyList.add(key);
                    counter++;

                    if (counter == 3)
                        break;

                }

                homeAdapter = new homeAdapter(listLatest, titlelist, keyList, Home.this);
                recyclerViewLatest.setAdapter(homeAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void DealsAdapter() {

        final ArrayList<String> imagelist = new ArrayList<>();
        final ArrayList<String> titlelist = new ArrayList<>();
        final ArrayList<String> keyList = new ArrayList<>();

        reference.orderByChild(Common.REFERENCE_ID).equalTo(Common.REFERENCE_DEALS_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String image = snapshot.child("link").getValue(String.class);
                    String title = snapshot.child("name").getValue(String.class);
                    String key = snapshot.getKey();


                    imagelist.add(image);
                    titlelist.add(title);
                    keyList.add(key);
                    counter++;


                    if (counter == 3)
                        break;

                }

                homeAdapter = new homeAdapter(imagelist, titlelist, keyList, Home.this);
                recyclerViewDeals.setAdapter(homeAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCartCount();

        if (adapter!=null) {
            adapter.startListening();
        }
    }


    @Override
    public void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        View view = (View) menu.findItem(R.id.menu_cartt).getActionView();
        badge = (NotificationBadge) view.findViewById(R.id.badgeCount);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Cart.class));

            }
        });
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchAdapter(s);
                return false;
            }

        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                recyclerViewSearch.setVisibility(View.VISIBLE);
                return true;
            }



            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                recyclerViewSearch.setVisibility(View.GONE);
                return true;
            }



        });



       updateCartCount();




        return true;
    }

    private void updateCartCount() {

        if (badge==null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (localDB.getCountCart()==0){
                    badge.setVisibility(View.INVISIBLE);


                }   else{
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(localDB.getCountCart()));

                }         }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.menu_cart) {
//
//
//            loadAdapter();
//        }
        int id  = item.getItemId();

        if (id==R.id.menu_cartt){

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id==R.id.shop){
            Intent categoryIntent = new Intent(Home.this,ShopCategoryActivity.class);
            categoryIntent.putExtra(Common.CATEGORY_NAME,Common.SHOP);
            startActivity(categoryIntent);


        }
        if (id == R.id.nav_cart) {
            // Handle the camera action
            startActivity(new Intent(Home.this,Cart.class));
        } else if (id == R.id.nav_orders) {
            final Intent ordersIntent = new Intent(Home.this,OrderStatus.class);
            ordersIntent.putExtra(Common.CATEGORY_NAME,Common.ORDERS);


            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.order_dialog);
            final EditText number = dialog.findViewById(R.id.order_dialog_edit);
            Button submit = dialog.findViewById(R.id.order_dialog_submit);
            dialog.show();
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String input = number.getText().toString().trim();
                    ordersIntent.putExtra(Common.ORDER_NUMBER,input);
                    startActivity(ordersIntent);
                    dialog.dismiss();
                }
            });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.popu:
              startIntent(Common.REFERENCE_POPULAR_KEY);
                break;

            case R.id.seeLatID:
                startIntent(Common.REFERENCE_LATEST_KEY);

                break;

            case R.id.seeDealsID:
                startIntent(Common.REFERENCE_DEALS_KEY);
                break;


        }

    }

    private void startIntent(String key){

        Intent intent = new Intent(Home.this, Products.class);
        intent.putExtra(Common.CATEGORY_ID_SELECTED,key);
        startActivity(intent);
    }


    public class MyTimeTask extends TimerTask{


        @Override
        public void run() {
            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }else {

                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}


