package com.xtv.video_in_box;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 4/23/15.
 */

//id, title, media, thumb
public class dbAdapter
{
    public static final String KEY_vidID = "videoID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MEDIA = "media";
    public static final String KEY_THUMB = "thumb";
    public static final String KEY_SEARCH = "searchData";
    private static final String DATABASE_NAME = "VideoData";
    private static final String FTS_VIRTUAL_TABLE = "VideoInfo";
    private static final int DATABASE_VERSION = 1;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String TAG = "dbAdapter";
    private static final String DATABASE_CREATE =
            "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE + " USING fts3(" +
                    KEY_vidID + "," +
                    KEY_TITLE + "," +
                    KEY_MEDIA + "," +
                    KEY_THUMB + "," +
                    KEY_SEARCH + "," +
                    " UNIQUE (" + KEY_vidID+ "));";
    private final Context mCtx;

    public dbAdapter(Context ctx)
    {
            this.mCtx = ctx;
    }

    public dbAdapter open() throws SQLException
    {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createVideo(String videoID, String title, String media, String thumb)
    {
        ContentValues initialValues = new ContentValues();
        String searchValue =     videoID + " " +
                title + " " +
                media + " " +
                thumb;
        initialValues.put(KEY_vidID, videoID);
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_MEDIA, media);
        initialValues.put(KEY_THUMB, thumb);
        initialValues.put(KEY_SEARCH, searchValue);

        return mDb.insert(FTS_VIRTUAL_TABLE, null, initialValues);
    }

    public Cursor searchVideos(String inputText) throws SQLException
    {
        Log.w(TAG, inputText);
        String query = "SELECT docid as _id," +
                KEY_vidID + "," +
                KEY_TITLE + "," +
                KEY_MEDIA + "," +
                KEY_THUMB +
                " from " + FTS_VIRTUAL_TABLE +
                " where " +  KEY_TITLE + " MATCH '" + inputText + "';";
        Log.w(TAG, query);
        Cursor mCursor = mDb.rawQuery(query,null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean deleteAllVideos()
    {
        int doneDelete = 0;
        doneDelete = mDb.delete(FTS_VIRTUAL_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

 ////////////////////////////__DATABASE_HELPER__////////////////////////////////

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }
    }

}


