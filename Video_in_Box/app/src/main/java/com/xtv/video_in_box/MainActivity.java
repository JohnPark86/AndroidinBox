/*
    Application Name: VideoInBox
    File Name:
    Date:
    Author:
    Description:
 */

package com.xtv.video_in_box;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/*
 Main Log-In screen. 2 TextFields and One Button to Load next activity.
*/
public class MainActivity extends Activity
{
    private final String TAG = "Main_Activity";
    Button login;
    String json;
    ArrayList<HashMap<String, String>> arraylist;
    EditText userText, passText;


    // Gets Intent from last activity and loads it into ArrayList holder for passing to next method.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyTheme);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        userText = (EditText)findViewById(R.id.username_Text);
        passText = (EditText)findViewById(R.id.password_text);
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
            String username = userText.getText().toString();
            String password = passText.getText().toString();
           BufferedReader in = null;
            String data = null;
            Log.i(TAG,username+" - " +password);

            try{

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost request = new HttpPost("http://10.1.10.36:3000/api/Users/Login?");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(request);
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                data = in.readLine();
                Log.i(TAG,data.toString());
                if(response.getStatusLine().getStatusCode()==200)
                {
                    Intent i = new Intent(getApplicationContext(), VideoList.class);
                    i.putExtra("List", arraylist);
                    startActivity(i);
                }
                if(response.getStatusLine().getStatusCode()==401)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Incorrect Login ");
                    alertDialog.setMessage("The Username or Password entered does not match our records. Please try again. ");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }catch(Exception e){
                Log.e(TAG, "Error in http connection "+e.toString());
            }

        }

    };
}
