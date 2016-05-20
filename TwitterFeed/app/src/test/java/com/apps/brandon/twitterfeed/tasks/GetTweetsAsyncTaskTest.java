package com.apps.brandon.twitterfeed.tasks;

import android.graphics.Bitmap;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetTweetsAsyncTaskTest {

    @Test
    public void testGetBitmapFromURLIsNull() {
        GetTweetsAsyncTask tweetsAsyncTask = new GetTweetsAsyncTask(null, null);
        Bitmap bitmap = tweetsAsyncTask.getBitmapFromURL(null);
        assertEquals(bitmap, null);
    }
}