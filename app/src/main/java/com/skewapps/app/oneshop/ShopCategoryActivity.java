package com.skewapps.app.oneshop;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.Interface.ItemClickListner;
import com.skewapps.app.oneshop.Model.Category;
import com.skewapps.app.oneshop.Viewholder.CategoryViewHolder;
import com.squareup.picasso.Picasso;

public class ShopCategoryActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String toolbarName;
    FirebaseRecyclerOptions<Category> options;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_category);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Category");

        toolbarName = getIntent().getStringExtra(Common.CATEGORY_NAME);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarName);

        recyclerView = findViewById(R.id.recyclerCategory);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loadAdapter();



    }

    private void loadAdapter() {

        options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(reference, Category.class).build();
        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull final Category model) {
                Picasso.get().load(model.getUrl()).into(holder.category_image);
                holder.category_title.setText(model.getName());

                holder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(ShopCategoryActivity.this,Products.class);
                        intent.putExtra(Common.CATEGORY_ID_SELECTED,adapter.getRef(position).getKey());
                        intent.putExtra(Common.HOME_ID_NAME,model.getName());
                        startActivity(intent);

                    }
                });


            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_item,viewGroup,false);

                return new CategoryViewHolder(v);        }
        };



        setAdapter();

    }

    void setAdapter(){

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null)
            adapter.stopListening();

    }

    @Override
    public void onResume() {
        super.onResume();
       // fab.setCount(new Database(this).getCountCart());

        if (adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
