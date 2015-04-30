/*
    Application Name: VideoInBox
    File Name:
    Date:
    Author:
    Description:
 */

package com.xtv.video_in_box;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/*
    list of Channel Names
 */
public class ChannelList extends Activity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private ImageButton inbox,fav;
    private ArrayList<HashMap<String, String>> arraylist;
    private ListView lv;
    private ListViewAdapter adapter;
    private SearchView sView;
    private dbAdapter mDbHelper;
    private TextView idTV, titleTV, mediaTV;
    private ImageView thumb;
    private ImageLoader imageLoader;
    private final String TAG = "Channel_List";

    /*
        Gets arraylist from sent Activity as a holder to send back to
        Main List. Instantiates buttons along bottom for other Activites.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyTheme);
        setContentView(R.layout.activity_channel_list);

        sView = (SearchView)findViewById(R.id.searchView);
        sView.setIconifiedByDefault(false);
        sView.setOnQueryTextListener(this);
        sView.setOnCloseListener(this);

        mDbHelper = new dbAdapter(this);
        mDbHelper.open();
        mDbHelper.deleteAllVideos();

        Intent i = getIntent();
        arraylist = (ArrayList<HashMap<String, String>>)i.getSerializableExtra("List");

        for (int x = 0; x < arraylist.size(); x++){
            HashMap<String, String> map = new HashMap<String, String>();
            map = arraylist.get(x);
            Log.i("id: ", map.get("id"));
            Log.i("title: ",map.get("title"));
            Log.i("media: ",map.get("media"));
            Log.i("thumb: ",map.get("thumb"));
            mDbHelper.createVideo(map.get("id"),map.get("title"),map.get("media"),map.get("thumb"));
        }

        inbox = (ImageButton)findViewById(R.id.inboxbutton);
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),VideoList.class);
                i.putExtra("List",arraylist);
                startActivity(i);
            }
        });

        fav = (ImageButton)findViewById(R.id.favButton);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),FavoriteList.class);
                in.putExtra("List",arraylist);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onClose() {
        showResults("");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        showResults(newText + "*");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        showResults(query + "*");
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDbHelper  != null) {
            mDbHelper.close();
        }
    }


    private void showResults(String query) {

        Cursor cursor = mDbHelper.searchVideos((query != null ? query.toString() : "@@@@"));
        ArrayList<HashMap<String, String>> resultlist = new ArrayList<>();

        if (cursor == null) {
            //
        } else {
        // Create a simple cursor adapter for the definitions and apply them to the ListView
            lv = (ListView)findViewById(R.id.list);

            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(1));
                map.put("title", cursor.getString(2));
                map.put("media", cursor.getString(3));
                map.put("thumb", cursor.getString(4));
                resultlist.add(map);
                cursor.moveToNext();
            }
            adapter = new ListViewAdapter(getApplicationContext(),resultlist);
            lv.setAdapter(adapter);

        }
    }
}