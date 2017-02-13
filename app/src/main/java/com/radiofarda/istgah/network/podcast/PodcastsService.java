package com.radiofarda.istgah.network.podcast;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Farshad on 2/11/17.
 *
 */

public interface PodcastsService {
    @GET("feed-ext.php")
    Observable<PodcastsData> getProgramList();
}
