package com.abhat.designquotes.network;

/**
 * Created by cumulations on 23/4/17.
 */

public interface QuoteInteractor {

    interface onQuoteFetchFinished {
        void onSuccess(String quote, String author);

        void onNetworkError();
    }

    void fetchQuoteFromNetwork(onQuoteFetchFinished listener);
}
