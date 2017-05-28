package com.abhat.designquotes.network;

import android.os.AsyncTask;
import android.util.Log;

import com.abhat.designquotes.App;
import com.abhat.designquotes.Quote;
import com.abhat.designquotes.activities.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cumulations on 23/4/17.
 */

public class QuoteInteractorImpl implements QuoteInteractor {
    private onQuoteFetchFinished mListener;
    private Quote quotes;

    @Override
    public void fetchQuoteFromNetwork(final onQuoteFetchFinished listener) {
        this.mListener = listener;
        Observable.fromCallable(new Callable<Quote>() {
            @Override
            public Quote call() throws Exception {
                return fetchQuote();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Quote>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Quote quote) {
                        if (mListener != null) {
                            mListener.onSuccess(quotes.getQuote(), quotes.getAuthor());
                        }
                    }
                });
    }



    /*
       http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    */
    private boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8"); //Google DNS (8.8.8.8)
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    private Quote fetchQuote() {
        String url = "https://quotesondesign.com/";

        try {
            Document doc  = Jsoup.connect(url).get();
            Elements elem = doc.select("div#quote-content");
            final String quote = elem.get(0).text();
            Elements elem1 = doc.select("span#quote-title");
            final String quoteAuthor = elem1.get(0).text();
            Log.d("QUOTE", "" + quote);
            quotes = new Quote();
            quotes.setQuote(quote);
            quotes.setAuthor(quoteAuthor);
            return quotes;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("QUOTE", "error");
        }
        return null;
    }
}
