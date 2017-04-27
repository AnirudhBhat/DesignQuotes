package com.abhat.designquotes.network;

import android.os.AsyncTask;
import android.util.Log;

import com.abhat.designquotes.App;
import com.abhat.designquotes.activities.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by cumulations on 23/4/17.
 */

public class QuoteInteractorImpl implements QuoteInteractor {
    private onQuoteFetchFinished mListener;
    @Override
    public void fetchQuoteFromNetwork(onQuoteFetchFinished listener) {
        this.mListener = listener;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (isOnline()) {
                    fetchQuote();
                } else {
                    if (mListener != null) {
                        mListener.onNetworkError();
                    }
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

    private void fetchQuote() {
        String url = "https://quotesondesign.com/";

        try {
            Document doc  = Jsoup.connect(url).get();
            Elements elem = doc.select("div#quote-content");
            final String quote = elem.get(0).text();
            Elements elem1 = doc.select("span#quote-title");
            final String quoteAuthor = elem1.get(0).text();
            Log.d("QUOTE", "" + quote);
            ((MainActivity) App.getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mListener != null) {
                        mListener.onSuccess(quote, quoteAuthor);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("QUOTE", "error");
        }
    }
}
