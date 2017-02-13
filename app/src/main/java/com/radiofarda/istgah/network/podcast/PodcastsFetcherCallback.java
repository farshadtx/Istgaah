package com.radiofarda.istgah.network.podcast;

import java.util.List;

/**
 * Created by Farshad on 2/11/17.
 *
 */

public interface PodcastsFetcherCallback {
    void onFetchFinished(List<ProgramList> result);
}