package com.xtv.video_in_box;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VideoList extends Activity {

    String id, thumb, title, media, json;
    ArrayList<JSONObject> objList;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        Intent i = getIntent();

        json = i.getStringExtra("json");
        JSONArray jsar = null;
        try {
            jsar  = new JSONArray(json);

            objList = new ArrayList<JSONObject>();

            for(int x=0; x < jsar.length(); x++)
            {
                JSONObject value = jsar.getJSONObject(x);
                objList.add(value);
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

     /*   JSONObject j  = objList.get(1);
        try {
            id = j.getString("id");
            title = j.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
     */

        lv = (ListView)findViewById(R.id.list);
        lv.setAdapter(new ArrayAdapter<JSONObject>(this, android.R.layout.simple_list_item_1, objList));
    }

}
