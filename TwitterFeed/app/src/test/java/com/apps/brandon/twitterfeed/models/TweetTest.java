package com.apps.brandon.twitterfeed.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class TweetTest {

    @Test
    public void verifyDefaults(){
        Tweet tweet = new Tweet();

        assertNull(tweet.PostDate);
        assertNull(tweet.Status);
        assertNull(tweet.StatusPic);
        assertNull(tweet.UserName);
        assertNull(tweet.UserPic);
    }

    @Test
    public void canSetupTweet(){
        Tweet tweet = new Tweet();

        tweet.Status = "Status";

        assertEquals(tweet.Status, "Status");
    }
}