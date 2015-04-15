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

import java.util.ArrayList;
import java.util.HashMap;

/*
    list of Channel Names
 */
public class ChannelList extends Activity {

    ImageButton inbox,fav;
    ArrayList<HashMap<String, String>> arraylist;

    /*
        Gets arraylist from sent Activity as a holder to send back to
        Main List. Instantiates buttons along bottom for other Activites.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyTheme);
        setContentView(R.layout.activity_channel_list);

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
}
