package com.radiofarda.istgah.istgaah.tools;

import java.util.List;

/**
 * Created by Farshad on 2/11/17.
 *
 */

public interface PodcastsFetcherCallback {
    void onFetchFinished(List<ProgramList> result);
}