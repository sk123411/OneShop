package com.skewapps.app.oneshop;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.Database.Database;
import com.skewapps.app.oneshop.Interface.ItemClickListner;
import com.skewapps.app.oneshop.Model.ProductsData;
import com.skewapps.app.oneshop.Viewholder.ProductViewHolder;
import com.squareup.picasso.Picasso;

import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Products extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseRecyclerOptions<ProductsData> options;
    FirebaseRecyclerAdapter<ProductsData, ProductViewHolder> adapter;
    RecyclerView recyclerView;
    Database localDB;
    String productID;
    String toolbarName;
    int maxId;
    String mobile;

    DatabaseReference reference1;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setFonts();
        setContentView(R.layout.activity_products);

        productID = getIntent().getStringExtra(Common.CATEGORY_ID_SELECTED);
       toolbarName = getIntent().getStringExtra(Common.HOME_ID_NAME);







        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarName);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.REFERENCE_PRODUCTS);

      //   reference1 = database.getReference(phone).child("favorite");

        /*
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId = (int) dataSnapshot.getChildrenCount();
                    mobile = dataSnapshot.getKey();
               //     Log.d("MAXID", String.valueOf(maxId));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/







        localDB = new Database(getBaseContext());
        recyclerView = findViewById(R.id.recyclerProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        if (Common.isConnectedToInternet(getBaseContext())) {

            try {
                if (productID != null) {
                    loadAdapter();
                }

            } catch (Exception e) {
                Log.e("Error", "Error" + e.getMessage());
            }
        }
        else {
            Toast.makeText(Products.this,"Please check your internet connection",
                    Toast.LENGTH_LONG).show();
        }

    }


    private void loadAdapter() {
        Query query = reference.orderByChild(Common.REFERENCE_ID).equalTo(productID);
        options = new FirebaseRecyclerOptions.Builder<ProductsData>().setQuery(query,ProductsData.class).build();
        AdapterUI();
        setAdapter();


    }

    private void AdapterUI() {

        adapter = new FirebaseRecyclerAdapter<ProductsData, ProductViewHolder>(options) {



            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position, @NonNull final ProductsData model) {

                holder.name.setText(model.getName());
                Picasso.get().load(model.getLink()).into(holder.product_image);
//                if (localDB.isFavorite(adapter.getRef(position).getKey())){
//                    holder.imageFav.setImageResource(R.drawable.ic_favorite_black_24dp);
//                }

        /*
                holder.imageFav.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        /*
                        model.setPoskey(adapter.getRef(holder.getAdapterPosition()).getKey());


                        if (maxId <=11) {

                               final HashMap<String, Object> hashMap = new HashMap<>();
                               hashMap.put("link", model.getLink());
                               hashMap.put("id", model.getId());
                               hashMap.put("name", model.getName());
                               hashMap.put("description", model.getDescription());
                               hashMap.put("price", model.getPrice());
                               hashMap.put("Poskey", model.getPoskey());
                               reference1.child(String.valueOf(randomNum())).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       holder.imageFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                                       Toast.makeText(Products.this, "Success", Toast.LENGTH_SHORT).show();
                                   }
                               });
                               //      localDB.addToFav(adapter.getRef(holder.getAdapterPosition()).getKey());

                               //       }else{

                               //             }


                        }


                        else {

                            Toast.makeText(Products.this,"You have saved " +
                                    "maximum number of favorites",Toast.LENGTH_LONG).show();
                        }
                    }


                });

 */





                holder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position) {

                        Intent intent = new Intent(Products.this,ProductDetail.class);
                        intent.putExtra(Common.CATEGORY_ID_SELECTED,adapter.getRef(position).getKey());
                        startActivity(intent);


                    }
                });



            }



            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_product_item,viewGroup,false);

                return new ProductViewHolder(v);
            }



        };

    }

    private void setAdapter() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    private int randomNum(){
        final int min = 1;
        final int max = 20;
        final int random = new Random().nextInt((max - min) + 1) + min;
        return random;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null) {
            adapter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null) {
            adapter.startListening();
        }

    }

    @Override
    public void onStop() {
        if (adapter!=null) {
            adapter.stopListening();
        }
        super.onStop();
    }

}
