package com.apps.brandon.twitterfeed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.brandon.twitterfeed.R;
import com.apps.brandon.twitterfeed.models.Tweet;

import java.util.List;

public class TweetAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Tweet> tweets;

    public TweetAdapter(Context context) {
        initializeAdapter(context);
    }

    private void initializeAdapter(Context context){
        if(context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    public void UpdateAdapter(List<Tweet> tweets){
        this.tweets = tweets;
    }

    /**
     * The number of items in the list is determined by the number of speeches
     * in our array.
     *
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        if(tweets == null){
            return 0;
        }
        return (tweets.size());
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficent to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public Object getItem(int position) {
        if(position < 0 || position >= tweets.size() ){
            return null;
        }
        return tweets.get(position);
    }

    /**
     * Use the array index as a unique id.
     *
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position) {
        if(position < 0 || position >= tweets.size() ){
            throw new IndexOutOfBoundsException();
        }
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tweet_item, null);
        }

        ImageView tweetUserPic = (ImageView) convertView.findViewById(R.id.tweet_user_pic);
        ImageView tweetStatusPic = (ImageView) convertView.findViewById(R.id.tweet_media);

        TextView tweetUserName = (TextView) convertView.findViewById(R.id.tweet_user_name);
        TextView tweetText = (TextView) convertView.findViewById(R.id.tweet_text);
        TextView tweetPostDate = (TextView) convertView.findViewById(R.id.tweet_post_date);
        Tweet tweet = tweets.get(position);

        tweetUserPic.setImageBitmap(tweet.UserPic);
        if(tweet.StatusPic != null) {
            tweetStatusPic.setImageBitmap(tweet.StatusPic);
        }
        tweetText.setText(tweet.Status);
        tweetUserName.setText("Posted by "+tweet.UserName);
        tweetPostDate.setText("Posted on " + tweet.PostDate);

        return convertView;
    }
}
