/*
    Application Name: VideoInBox
    File Name:
    Date:
    Author:
    Description:
 */
package com.xtv.video_in_box;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/*
Activity that hosts  youtube media player to play video that is selcted.
 */
public class MediaPlayer_Activity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyAdC9kSxN454zVTg0Q08Yvbhr6OjQiVbAQ";
    ProgressDialog pDialog;
    WebView webView;
    TextView tv;
    String videoURL;

    /*
    Instantiates YouTube player and creates loader dialog/spinner image.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player_);
        //  webView = (WebView)findViewById(R.id.WebView);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);


        tv = (TextView) findViewById(R.id.tv1);
        Intent i = getIntent();
        videoURL = i.getStringExtra("media");

        pDialog = new ProgressDialog(this);
        // Set progressbar title
        pDialog.setTitle("Loading Content...");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        Log.i("url", videoURL);

        //    webView.getSettings().setJavaScriptEnabled(true);
        //    webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //    webView.loadUrl("http://www.youtube.com/embed/" + videoURL + "?autoplay=1&vq=small");
        //    webView.setWebChromeClient(new WebChromeClient());
    }

    /*
    Youtube Player Failed
     */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to Initialize!", Toast.LENGTH_LONG).show();
    }

    /*
        Player success, plays video.
     */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(videoURL);
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }


        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }

    };
}