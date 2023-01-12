package bw.mymis.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import bw.mymis.newapp.databinding.ActivityRestaurantListBinding;

public class RestaurantListActivity extends AppCompatActivity {
    ActivityRestaurantListBinding binding;
    RestaurantItemClickListener restaurantItemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRestaurantListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_red002)));
        getSupportActionBar().setTitle("所有餐廳列表：");
        getSupportActionBar().setSubtitle("花蓮");

        restaurantItemClickListener=new RestaurantItemClickListener() {
            @Override
            public void onClick(int position, String restaurantName) {
                Toast.makeText(RestaurantListActivity.this, "資料位置："+position+"餐廳名稱："+restaurantName, Toast.LENGTH_SHORT).show();
            }
        };


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.ResRecyclerView.setLayoutManager(linearLayoutManager);
        binding.ResRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        SQLiteDatabase db = openOrCreateDatabase("restaurants",MODE_PRIVATE,null);
        binding.ResRecyclerView.setAdapter( new RestaurantAdapter(db,restaurantItemClickListener) );

    }

    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    */
    public void callByAdapter(int pos , String name) {

    }
}