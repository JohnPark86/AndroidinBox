package com.xtv.video_in_box;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity
{
    Button login;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        json = i.getStringExtra("json");
        login = (Button) findViewById(R.id.LoginButton);
        login.setOnClickListener(loginB);
    }

    View.OnClickListener loginB = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent i = new Intent(getApplicationContext(), VideoList.class);
            i.putExtra("json",json.toString());
            startActivity(i);
        }
    };


}
