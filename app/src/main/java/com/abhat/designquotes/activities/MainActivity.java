package com.abhat.designquotes.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abhat.designquotes.App;
import com.abhat.designquotes.R;
import com.abhat.designquotes.network.QuoteInteractorImpl;
import com.abhat.designquotes.presenters.QuotePresenter;
import com.abhat.designquotes.presenters.QuotePresenterImpl;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    private Button mQuoteButton;
    private ProgressBar mProgressBar;
    private TextView mQuote;
    private TextView mQuoteAuthor;
    private FloatingActionButton mFloatingActionButton;
    private RelativeLayout mRelativeLayout;
    private QuotePresenter mQuotePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.setContext(this);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mQuoteButton = (Button)findViewById(R.id.quotebtn);
        mQuote = (TextView)findViewById(R.id.quote);
        mFloatingActionButton = (FloatingActionButton)findViewById(R.id.share);
        mQuoteAuthor = (TextView)findViewById(R.id.author);
        mRelativeLayout = (RelativeLayout)findViewById(R.id.activity_main);
        Typeface roboto = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Light.ttf");
        Typeface robotoAuthor = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Bold.ttf");
        mQuote.setTypeface(roboto);
        mQuoteAuthor.setTypeface(robotoAuthor);
        mQuoteButton.setOnClickListener(this);
        mFloatingActionButton.setOnClickListener(this);
        mQuotePresenter = new QuotePresenterImpl(this, new QuoteInteractorImpl());
        mQuotePresenter.fetchQuote();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(mRelativeLayout, "No internet connection, Please try again", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showQuote(String quote, String author) {
        fadein(quote, author);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quotebtn:
                fadeout();
                mQuotePresenter.fetchQuote();
                break;
            case R.id.share:
                shareQuote();
                break;
        }
    }

    private void shareQuote() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mQuote.getText() + " - " + mQuoteAuthor.getText());
        startActivity(Intent.createChooser(sharingIntent, "share"));
    }

    private void fadein(final String quote, final String author) {
        Animation from = new AlphaAnimation(0.0f, 1.0f);
        from.setDuration(500);
        mQuote.setText(quote);
        mQuoteAuthor.setText(author);
        mQuote.startAnimation(from);
        mQuoteAuthor.startAnimation(from);
        from.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeout() {
        Animation from = new AlphaAnimation(1.0f, 0.0f);
        from.setDuration(500);
        mQuote.startAnimation(from);
        mQuoteAuthor.startAnimation(from);
        from.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mQuote.setText("");
                mQuoteAuthor.setText("");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
