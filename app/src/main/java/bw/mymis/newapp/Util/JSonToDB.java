package bw.mymis.newapp.Util;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSonToDB {
    private String jsonString;
    private SQLiteDatabase db;

    public JSonToDB(SQLiteDatabase db){
        this.db=db;
    }

    public void writeToDatabase(String jsonString){
        this.jsonString=jsonString;
        try{
            JSONObject rawData=new JSONObject(jsonString);
            JSONArray records=rawData.getJSONObject("XML_Head").getJSONObject("Infos").getJSONArray("Info");
            for(int i=0;i<records.length();i++){
                JSONObject obj=records.getJSONObject(i);
                db.execSQL("insert into restaurant values(?,?,?,?,?,?,?,?);",
                        new Object[]{
                                obj.getString("Id"),
                                obj.getString("Name"),
                                obj.getString("Description"),
                                obj.getString("Region"),
                                obj.getString("Town"),
                                obj.getString("Add"),
                                obj.getString("Tel"),
                                obj.getString("Opentime")
                        });
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
