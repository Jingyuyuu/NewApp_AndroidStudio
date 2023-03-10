package bw.mymis.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bw.mymis.newapp.Util.JSonToDB;
import bw.mymis.newapp.Util.SimpleAPIWorker;
import bw.mymis.newapp.databinding.ActivityMaintainBinding;
import okhttp3.Request;

public class MaintainActivity extends AppCompatActivity {
    ActivityMaintainBinding binding;
    SharedPreferences activityPreferences;
    SQLiteDatabase db;
    ExecutorService executor ;
    final static String createTable =
            "create table if not exists restaurant(" +
            "_id text," +
            "name text," +
            "description text," +
            "region text," +
            "town text," +
            "address text," +
            "tel text," +
            "opentime text);";

    Handler dataHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String jsonString;
            Bundle bundle=msg.getData();
            int status=bundle.getInt("status");
            if(status==200){
                db.execSQL("drop table if exists restaurant;");
                db.execSQL(createTable);
                jsonString=bundle.getString("data");
                JSonToDB j2db=new JSonToDB(openOrCreateDatabase("restaurants",MODE_PRIVATE,null));
                j2db.writeToDatabase(jsonString);

            }
            Date now = new Date();
            activityPreferences.edit().putString("lastUpdate", now.toString());
            binding.refreshTXT.setText(activityPreferences.getString("lastUpdate","??????"));
            binding.progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(MaintainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaintainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activityPreferences = this.getPreferences(MODE_PRIVATE);
        executor = Executors.newSingleThreadExecutor();
        db = openOrCreateDatabase("restaurants", MODE_PRIVATE, null);

        binding.resetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPreferences.edit().remove(("isFirstTime")).remove("lastUpdate").apply();
                Toast.makeText(MaintainActivity.this, "?????????reset", Toast.LENGTH_SHORT).show();
            }
        });

        binding.upDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                Request request=new Request.Builder().url("https://media.taiwan.net.tw/XMLReleaseALL_public/restaurant_C_f.json")
                        .build();

                SimpleAPIWorker simpleAPIWorker = new SimpleAPIWorker(request, dataHandler);
                executor.execute(simpleAPIWorker);

            }
        });

        checkA();
        binding.refreshTXT.setText(activityPreferences.getString("lastUpdate", "???").toUpperCase());

    }


    public void checkA() {
        boolean needInitial = activityPreferences.getBoolean("isFirstTime", true);
        if (needInitial) {
            // ???????????????
            // ??????????????? & restaurant Table
            db.execSQL(createTable);
            // ??? raw ?????? restaurant.json ??? ?????? restaurant ?????????
            db.execSQL(createTable);
            Cursor cursor = db.rawQuery("select * from restaurant;",null);
            if( cursor == null || cursor.getCount() == 0 ) {
                //??????????????? restaurant ???????????? ?????? raw ?????? restaurant.json ????????? table
            }
            Date now = new Date();
            // ????????????????????? ?????? ??????????????????????????????
            activityPreferences.edit()
                    .putBoolean("isFirstTime", false)
                    .putString("lastUpdate", now.toString())
                    .apply();
        }
    }
}
