/*
    Application Name: VideoInBox
    File Name:
    Date:
    Author:
    Description:
 */

package com.xtv.video_in_box;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/*
    Layout for each specific item in ListView
 */
class ListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    static ArrayList<HashMap<String, String>> favorites = new ArrayList<HashMap<String,String>>();;
    ImageLoader imageLoader;
    ImageView thumb;
    HashMap<String, String> resultp = new HashMap<String, String>();
    HashMap<String, String> favMap = new HashMap<String, String>();
    String mediaURL;
    ImageButton addFav;

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    public void addList(ArrayList<HashMap<String, String>> arraylist)
    {
        data = arraylist;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getMediaURL(int position){

        return data.get(position).get(SplashScreen_Activity.MEDIA);
    }

    /*
    Gets information for each item in list. Gets location of item by position and loads
    data from splash screen JSON hashmap accordingly until all listview items have been populated.
     */
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView title, media;
        final Drawable notFav = context.getResources().getDrawable(R.drawable.add_favorit_btn);
        final Drawable addedFav = context.getResources().getDrawable(R.drawable.add_favorit_btn_acitve);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        resultp = data.get(position);
        addFav = (ImageButton)itemView.findViewById(R.id.favImgBtn);
        addFav.setTag(position);

        title = (TextView) itemView.findViewById(R.id.title);
        thumb = (ImageView) itemView.findViewById(R.id.thumb);

        title.setText(resultp.get(SplashScreen_Activity.TITLE));
        mediaURL = resultp.get(SplashScreen_Activity.MEDIA);
        imageLoader.DisplayImage(resultp.get(SplashScreen_Activity.THUMB), thumb);

        /*
        Check to see if current views favorite button is currently pressed. If so, the view
        applies the correct image upon redrawing.
         */
        if(favorites.contains(data.get(position)))
            ((ImageButton) itemView.findViewById(R.id.favImgBtn)).setImageResource(R.drawable.add_favorit_btn_acitve);
        else
            ((ImageButton) itemView.findViewById(R.id.favImgBtn)).setImageResource(R.drawable.add_favorit_btn);

        /*
        Adds video to favorite list and adds its location to the list of favorited views
        for redrawing purposes.
         */
        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int which = -1;
                Object obj = v.getTag();
                if (obj instanceof Integer) {
                    which = ((Integer) obj).intValue();
                }

                if(favorites.contains(data.get(which))) {
                    Log.i("WHICH: ", String.valueOf(which));
                    favorites.remove(favorites.indexOf(data.get(which)));
                }
                else if (which > -1) {
                    favMap = data.get(which);
                    favorites.add(favMap);
                }

                if (favorites.contains(favMap)) {
                    ((ImageButton) v.findViewById(R.id.favImgBtn)).setImageResource(favorites.contains(data.get(position)) ? R.drawable.add_favorit_btn_acitve : R.drawable.add_favorit_btn);
                } else {
                   ((ImageButton) v.findViewById(R.id.favImgBtn)).setImageResource(R.drawable.add_favorit_btn_acitve);
                }
            notifyDataSetChanged();
            }
        });
        return itemView;
    }

}