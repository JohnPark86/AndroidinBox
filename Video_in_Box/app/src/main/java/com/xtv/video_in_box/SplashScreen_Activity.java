package com.xtv.video_in_box;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SplashScreen_Activity extends Activity
{
    final String TAG = "Splash_Screen_Activity";

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

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            JsonParser jsonParser = new JsonParser();
            json = jsonParser.getJSONFromUrl("http://hills.ccsf.edu/~jpark41/ViB/sample.json");
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


            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("json",json.toString());
            startActivity(i);
            finish();
        }

    }
}
