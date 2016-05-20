package com.apps.brandon.twitterfeed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.apps.brandon.twitterfeed.adapters.TweetAdapter;
import com.apps.brandon.twitterfeed.models.Tweet;
import com.apps.brandon.twitterfeed.tasks.GetTweetsAsyncTask;

import java.util.ArrayList;
import java.util.List;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends ListActivity implements AdapterView.OnItemSelectedListener{

    public String hashtagSearch = "";

    private TwitterFactory twitterFactory;
    private View mainProgressView;
    private Handler refreshTweetsHandler;

    public TextView searchResultText;
    public EditText searchText;
    public TweetAdapter tweetsAdapter;
    public int refreshInterval = 10;

    final Integer[] refreshIntervalItems = new Integer[]{5, 10, 15, 30, 60};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeTwitterFactory();
        initializeView();
        initializeApp();
    }

    private void initializeTwitterFactory(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("CnsQAVjrOIGR7znhjDy1JJEck")
                .setOAuthConsumerSecret("zRpuh38QnoiaSaFVf0tPate3Lw2q0yuPMAST4S55vIHjulfCaF")
                .setOAuthAccessToken("263464725-dqFm3lUdmToLlDTAKSTwB0oXWdvjwK4Jpb5QxAU4")
                .setOAuthAccessTokenSecret("sXr5hFypEPv7gKfULA7wo1k9Unl8xIJYb2OUHrp38YZME");
        twitterFactory = new TwitterFactory(cb.build());
    }

    private void initializeView(){
        mainProgressView = findViewById(R.id.main_progress);
        searchText = (EditText) findViewById(R.id.search_text);
        searchResultText = (TextView)findViewById(R.id.search_result_desc);
        Spinner refreshIntervalSpinner = (Spinner) findViewById(R.id.refresh_interval_spinner);

        tweetsAdapter = new TweetAdapter(this);

        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hashtagSearch = s.toString();
            }
        });

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, refreshIntervalItems);
        refreshIntervalSpinner.setAdapter(adapter);
        refreshIntervalSpinner.setOnItemSelectedListener(this);

        for(int i =0; i < refreshIntervalItems.length; i++){
            if(refreshInterval == refreshIntervalItems[i]){
                refreshIntervalSpinner.setSelection(i);
            }
        }
    }

    private void initializeApp(){
        refreshTweetsHandler = new Handler();
        refreshTweetsHandler.postDelayed(refreshTweetsRunnable, refreshInterval*1000);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            getListView().setVisibility(show ? View.GONE : View.VISIBLE);
            getListView().animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    getListView().setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mainProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mainProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mainProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mainProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            getListView().setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        refreshInterval = refreshIntervalItems[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void UpdateList(List<Tweet> tweets){
        tweetsAdapter.UpdateAdapter(tweets);
        setListAdapter(tweetsAdapter);
    }

    private Runnable refreshTweetsRunnable = new Runnable() {
        @Override
        public void run() {
            if(hashtagSearch.isEmpty()){
                UpdateList(new ArrayList<Tweet>());
                searchResultText.setText("No results found");
            }else {
                showProgress(true);
                searchResultText.setText("Refreshing, please wait...");
                GetTweetsAsyncTask getTweetsTask = new GetTweetsAsyncTask(twitterFactory, MainActivity.this);
                getTweetsTask.execute();
            }

            refreshTweetsHandler.postDelayed(this, refreshInterval*1000);
        }
    };
}
