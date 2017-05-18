package com.abhat.designquotes.presenters;

import com.abhat.designquotes.activities.MainView;
import com.abhat.designquotes.network.QuoteInteractor;

/**
 * Created by cumulations on 23/4/17.
 */

public class QuotePresenterImpl implements QuotePresenter, QuoteInteractor.onQuoteFetchFinished {

    private MainView mMainView;
    private QuoteInteractor mQuoteInteractor;

    public QuotePresenterImpl(MainView mainView, QuoteInteractor quoteInteractor) {
        this.mMainView = mainView;
        this.mQuoteInteractor = quoteInteractor;
    }

    @Override
    public void fetchQuote() {
        if (mQuoteInteractor != null && mMainView != null) {
            mMainView.showProgressBar();
            mQuoteInteractor.fetchQuoteFromNetwork(this);
        }
    }

    @Override
    public void onSuccess(String quote, String author) {
        if (mMainView != null) {
            mMainView.hideProgressBar();
            mMainView.showQuote(quote, author);
        }
    }

    @Override
    public void onNetworkError() {
        if (mMainView != null) {
            mMainView.showNetworkError();
        }
    }
}
