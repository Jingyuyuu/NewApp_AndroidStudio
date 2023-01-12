package bw.mymis.newapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import bw.mymis.newapp.Modle.SimpleRestaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    SQLiteDatabase db;
    ArrayList<SimpleRestaurant> restAll;
    RestaurantItemClickListener listener;
    public RestaurantAdapter(SQLiteDatabase db, RestaurantItemClickListener listener) {
        this.db = db;
        this.listener = listener;
        restAll = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from restaurant;", null );
        //請將資料轉成 adapter 本的資料結構備用 以免 db被系統回收關閉
        if( cursor.getCount() > 0 ) {
            cursor.moveToFirst();
            do {
                SimpleRestaurant simpleRestaurant = new SimpleRestaurant(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(5)
                );
                restAll.add(simpleRestaurant);
            }while ( cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText( restAll.get(position).getName() );
        holder.txtAddress.setText( restAll.get(position).getAddress() );
        holder.txtRegion.setText( restAll.get(position).getRegion() );

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int pos=holder.getAdapterPosition();
                String name=restAll.get(pos).getName();
                String addr = restAll.get(pos).getAddress();
                listener.onClick(pos,restAll.get(pos).getName());
                //Toast.makeText(holder.itemView.getContext(), "餐廳名稱："+name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return restAll.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtName;
        TextView txtRegion;
        TextView txtAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.ResImg);
            this.txtName=itemView.findViewById(R.id.ResName);
            this.txtRegion=itemView.findViewById(R.id.ResRegion);
            this.txtAddress=itemView.findViewById(R.id.ResAddress);

        }
    }


}
