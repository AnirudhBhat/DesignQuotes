package com.abhat.designquotes.activities;

/**
 * Created by cumulations on 23/4/17.
 */

public interface MainView {
    void showProgressBar();
    void hideProgressBar();
    void showNetworkError();
    void showQuote(String quote, String author);
}
