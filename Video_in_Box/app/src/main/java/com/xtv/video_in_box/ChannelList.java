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
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;

/*
    list of Channel Names
 */
public class ChannelList extends Activity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private ImageButton inbox,fav;
    private ArrayList<HashMap<String, String>> arraylist;
    private ListView lv;
    private SearchView sView;
    private dbAdapter mDbHelper;

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

        Intent i = getIntent();
        arraylist = (ArrayList<HashMap<String, String>>)i.getSerializableExtra("List");

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
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
