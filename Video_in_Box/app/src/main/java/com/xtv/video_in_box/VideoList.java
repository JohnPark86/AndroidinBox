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
Displays listing of all videos that were loaded in splash scteen.
 */
public class VideoList extends Activity {

    String id, thumb, title, media, json;
    ListView lv;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist, favorites;
    HashMap<String, String> map = new HashMap<String, String>();
    ImageButton fav,vids;
    /*
        Instantiates buttons along bottom of app and sets them up to pass necessary
        data for their own specific activites or to pass back to this activity when it is re-loaded.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyTheme);
        setContentView(R.layout.activity_video_list);

        Intent i = getIntent();
        arraylist = (ArrayList<HashMap<String, String>>)i.getSerializableExtra("List");

        Log.i("A: ", arraylist.toString());

        fav = (ImageButton)findViewById(R.id.favButton);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),FavoriteList.class);
                favorites = ListViewAdapter.favorites;
                Log.i("Favorites: ", favorites.toString());
                in.putExtra("favorites",favorites);
                in.putExtra("List",arraylist);
                startActivity(in);
            }
        });

        vids = (ImageButton)findViewById(R.id.vidButton);
        vids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),ChannelList.class);
                in.putExtra("List",arraylist);
                startActivity(in);
            }
        });

        /*
        Sets ArrayList as ArrayAdapter and populates ListView on screen.
        If a Button is clicked it launches MediaPlayer Activity to play selected
        video.
         */

        lv = (ListView)findViewById(R.id.list);
        adapter = new ListViewAdapter(getApplicationContext(),arraylist);
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
