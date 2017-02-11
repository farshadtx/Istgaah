package com.radiofarda.istgah.istgaah.tools;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PodcastsFetcher {
    public void fetch(PodcastsFetcherCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.bejbej.ca/rpf/")
                .build();

        PodcastsService podcastsService = retrofit.create(PodcastsService.class);
        Observable<PodcastsData> programs = podcastsService.getProgramList();

        programs.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(podcastsData -> {
                    Log.i("Network Calls", "Done fetching with " + podcastsData.getProgramList().size() + " items!");
                    callback.onFetchFinished(podcastsData.getProgramList());
                });
    }
}