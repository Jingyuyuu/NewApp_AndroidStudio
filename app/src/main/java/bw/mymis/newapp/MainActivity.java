package bw.mymis.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import bw.mymis.newapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnABController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionBar actionBar=MainActivity.this.getSupportActionBar();
                if(actionBar.isShowing()){
                    Toast.makeText(MainActivity.this, "ActionBar開啟中,即將關閉", Toast.LENGTH_SHORT).show();
                    actionBar.hide();
                }else{
                    Toast.makeText(MainActivity.this, "ActionBar關閉中,即將開啟", Toast.LENGTH_SHORT).show();
                    actionBar.show();
                }
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_load:
                Intent intentMaintain=new Intent(MainActivity.this,MaintainActivity.class);
                startActivity(intentMaintain);
                break;
            case R.id.menu_list:
                Intent intentRestaurantList=new Intent(MainActivity.this,RestaurantListActivity.class);
                startActivity(intentRestaurantList);
                break;
            case R.id.menu_exit:
                this.finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}