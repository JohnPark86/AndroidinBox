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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

// Displays Splash Screen Image while Application loads JSON Data
public class SplashScreen_Activity extends Activity
{
    final String TAG = "Splash_Screen_Activity";
    static String ID = "id";
    static String TITLE = "title";
    static String MEDIA = "media";
    static String THUMB = "thumb";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);
        new PrefetchData().execute();
    }

    ///////////////////__PrefecthData__//////////////////////////////

    private class PrefetchData extends AsyncTask<Void, Void, Void>
    {
        ArrayList<JSONObject> objList;
        String json;
        ArrayList<HashMap<String, String>> arraylist;
        JSONObject jsonobject;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        /*
            AsyncTask method that loads JSON data and parses it into a HashMap then puts
            hashMap into ArrayList to Bundle and pass to another Activity.
         */
        @Override
        protected Void doInBackground(Void... arg0)
        {
            JsonParser jsonParser = new JsonParser();
            json = jsonParser.getJSONFromUrl("http://hills.ccsf.edu/~jpark41/ViB/sample.json");
            JSONArray jsar = null;
            arraylist = new ArrayList<HashMap<String, String>>();

            try {
                jsar  = new JSONArray(json);

                for (int i = 0; i < jsar.length(); i++){
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsar.getJSONObject(i);
                    map.put("id", jsonobject.getString("id"));
                    map.put("title", jsonobject.getString("title"));
                    map.put("media", jsonobject.getString("media"));
                    map.put("thumb", jsonobject.getString("thumb"));
                    arraylist.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /*
            After the data is loaded the splash screen ends and the next activity is called.
         */
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("List",arraylist);
            startActivity(i);
            finish();
        }

    }
}
