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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/*
    List of faorited videos.
 */
public class FavoriteList extends Activity {

    ImageButton inbox,vids;
    ArrayList<HashMap<String, String>> arraylist, favlist;
    ListView lv;
    ListViewAdapter adapter;

    /*
        Gets arraylist from sent Activity as a holder to send back to
        Main List. Instantiates buttons along bottom for other Activites.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyTheme);
        setContentView(R.layout.activity_favorite_list);

        Intent i = getIntent();
        arraylist = (ArrayList<HashMap<String, String>>) i.getSerializableExtra("List");
        favlist = (ArrayList<HashMap<String, String>>) i.getSerializableExtra("favorites");
        Log.i("A: ", arraylist.toString());

        inbox = (ImageButton) findViewById(R.id.inboxbutton);
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VideoList.class);
                i.putExtra("List", arraylist);
                startActivity(i);
            }
        });

        vids = (ImageButton)findViewById(R.id.vidButton);
        vids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ChannelList.class);
                in.putExtra("List", arraylist);
                startActivity(in);
            }
        });

        lv = (ListView)findViewById(R.id.favList);
        adapter = new ListViewAdapter(getApplicationContext(),favlist);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(getApplicationContext(), MediaPlayer_Activity.class);
                i.putExtra("title", adapter.data.get(position).get(SplashScreen_Activity.TITLE));
                i.putExtra("media", arraylist.get(position).get(SplashScreen_Activity.ID));
                startActivity(i);
            }
        });

    }
}