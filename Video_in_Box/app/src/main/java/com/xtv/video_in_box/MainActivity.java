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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;


/*
 Main Log-In screen. 2 TextFields and One Button to Load next activity.
*/
public class MainActivity extends Activity
{
    private final String TAG = "Main_Activity";
    Button login;
    String json;
    ArrayList<HashMap<String, String>> arraylist;


    // Gets Intent from last activity and loads it into ArrayList holder for passing to next method.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyTheme);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        arraylist = (ArrayList<HashMap<String, String>>)i.getSerializableExtra("List");
        login = (Button) findViewById(R.id.LoginButton);
        login.setOnClickListener(loginB);
    }

    // if login button is clicked arraylist is bundled and passed to new activity.
    View.OnClickListener loginB = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

           // Intent i = new Intent(getApplicationContext(), VideoList.class);
           // i.putExtra("List",arraylist);
           // startActivity(i);
           new HttpRequestTask().execute();
            Log.i(TAG,"onClicked");
        }
    };

    private class HttpRequestTask extends AsyncTask<Void, Void, Login> {
        @Override
        protected Login doInBackground(Void... params) {
            try {

                String url = "http://10.0.3.2:8080/api/Users/login?access_token=fOsjbHvoGNbpqIl6tGicpzvlqiTXH95h3Y4A1ESlsewkTLs8Uj2K9WRAu0JxV1y9";
                url.concat("&username={user}&password={pass}");
              //  final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Login login = restTemplate.getForObject(url, Login.class);
                Log.i(TAG, "doInBackground()");
                return login;
            } catch (Exception e) {
                Log.e("MainActivity: ", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Login login) {
            Log.i("username: ", login.getUsername());
            Log.i("password: ",login.getPassword());
     //       Log.i("id:  ",login.getId());
     //       Log.i("content:  ", login.getContent());

        }

    }
}
