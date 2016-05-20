package com.apps.brandon.twitterfeed.adapters;

import com.apps.brandon.twitterfeed.models.Tweet;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by brandom on 5/20/2016.
 */
public class TweetAdapterTest {

    @Test
    public void testUpdateAdapterIsNull() {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        tweetAdapter.UpdateAdapter(null);
        assertEquals(tweetAdapter.getCount(), 0);
    }

    @Test
    public void testUpdateAdapterIsEmpty() {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        tweetAdapter.UpdateAdapter(new ArrayList<Tweet>());
        assertEquals(tweetAdapter.getCount(), 0);
    }

    @Test
    public void testUpdateAdapterIsMany() {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        List<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(new Tweet());
        tweets.add(new Tweet());
        tweetAdapter.UpdateAdapter(tweets);
        assertEquals(tweetAdapter.getCount(), 2);
    }

    @Test
    public void testGetItemIsFirstTweet() {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        List<Tweet> tweets = new ArrayList<Tweet>();

        Tweet tweet = new Tweet();
        tweet.Status = "Status";

        tweets.add(tweet);
        tweets.add(new Tweet());
        tweets.add(new Tweet());

        tweetAdapter.UpdateAdapter(tweets);

        Tweet tweetFromGetItem = (Tweet)tweetAdapter.getItem(0);
        assertEquals(tweet, tweetFromGetItem);
    }

    @Test
    public void testGetItemIsNull_OutOfIndex_BelowRange() {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        List<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(new Tweet());
        tweets.add(new Tweet());

        tweetAdapter.UpdateAdapter(tweets);

        Tweet tweetFromGetItem = (Tweet)tweetAdapter.getItem(-1);
        assertEquals(null, tweetFromGetItem);
    }

    @Test
    public void testGetItemIsNull_OutOfIndex_AboveRange() {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        List<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(new Tweet());
        tweets.add(new Tweet());

        tweetAdapter.UpdateAdapter(tweets);

        Tweet tweetFromGetItem = (Tweet)tweetAdapter.getItem(tweets.size()+1);
        assertEquals(null, tweetFromGetItem);
    }

    @Test
    public void testGetItemIdForFirstTweet() {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        List<Tweet> tweets = new ArrayList<Tweet>();

        tweets.add(new Tweet());
        tweets.add(new Tweet());

        tweetAdapter.UpdateAdapter(tweets);

        assertEquals(0, tweetAdapter.getItemId(0));
    }

    @Test
    public void testGetItemId_OutOfIndex_BelowRange() throws IndexOutOfBoundsException {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        List<Tweet> tweets = new ArrayList<Tweet>();

        tweets.add(new Tweet());
        tweets.add(new Tweet());

        tweetAdapter.UpdateAdapter(tweets);

        try {
            tweetAdapter.getItemId(-1);
            Assert.fail();
        }catch(IndexOutOfBoundsException e){

        }
    }

    @Test
    public void testGetItemId_OutOfIndex_AboveRange() throws IndexOutOfBoundsException {
        TweetAdapter tweetAdapter = new TweetAdapter(null);
        List<Tweet> tweets = new ArrayList<Tweet>();

        tweets.add(new Tweet());
        tweets.add(new Tweet());

        tweetAdapter.UpdateAdapter(tweets);
        try {
            tweetAdapter.getItemId(tweets.size() + 1);
            Assert.fail();
        }catch(IndexOutOfBoundsException e){

        }
    }
}