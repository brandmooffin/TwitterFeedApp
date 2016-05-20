package com.apps.brandon.twitterfeed.tasks;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.apps.brandon.twitterfeed.MainActivity;
import com.apps.brandon.twitterfeed.models.Tweet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class GetTweetsAsyncTask extends AsyncTask<String, Void, Boolean> {

    private TwitterFactory twitterFactory;
    private MainActivity mainActivity;
    private List<Tweet> tweets;

    public GetTweetsAsyncTask(TwitterFactory twitterFactory, MainActivity mainActivity){
        this.twitterFactory = twitterFactory;
        this.mainActivity = mainActivity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            getTweets();
            return true;
        } catch (Exception e) {
            System.out.println("\nError while calling service");
            System.out.println(e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        mainActivity.showProgress(false);

        if (success) {
            mainActivity.searchResultText.setText("");
            mainActivity.UpdateList(tweets);
        }else{
            mainActivity.searchResultText.setText("Uh-oh! Looks like something went wrong searching. Please try again later.");
        }
    }

    @Override
    protected void onCancelled() {
        mainActivity.showProgress(false);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    private void getTweets(){
        String hashtagSearch = mainActivity.hashtagSearch;

        if(!hashtagSearch.contains("#")){
            hashtagSearch = "#"+ hashtagSearch;
        }

        Twitter twitter = twitterFactory.getInstance();
        try {
            Query query = new Query(hashtagSearch);
            QueryResult result = twitter.search(query);
            tweets = new ArrayList<Tweet>();
            for (twitter4j.Status status : result.getTweets()) {
                Tweet tweet = new Tweet();
                tweet.UserName = status.getUser().getName();
                tweet.PostDate = status.getCreatedAt().toString();
                tweet.Status = status.getText();
                tweet.UserPic = getBitmapFromURL(status.getUser().getOriginalProfileImageURL());
                if(status.getMediaEntities().length > 0) {
                    tweet.StatusPic = getBitmapFromURL(status.getMediaEntities()[0].getMediaURL());
                }
                tweets.add(tweet);
            }
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
