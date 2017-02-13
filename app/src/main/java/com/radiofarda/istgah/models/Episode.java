package com.radiofarda.istgah.models;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import com.radiofarda.istgah.network.podcast.PodcastsFetcher;
import com.radiofarda.istgah.network.podcast.ProgramList;
import com.radiofarda.istgah.views.adapters.EpisodesAdapter;

/**
 * Created by maz on 2/13/17.
 */
public class Episode {
    private final ProgramList programList;

    public Episode(ProgramList programList) {
        this.programList = programList;
    }

    public String getDescription() {
        return programList.getLinkHighQuality();
    }

    public String getDate() {
        return programList.getDate();
    }

    public static void loadEpisodeAdapter(EpisodesAdapter episodesAdapter) {
        new AsyncTask<ArrayAdapter, Void, Void>() {
            @Override
            protected Void doInBackground(ArrayAdapter[] params) {
                for (ArrayAdapter item : params) {
                    new PodcastsFetcher().fetch(programList -> {
                        for (ProgramList program : programList) {
                            item.addAll(new Episode(program));
                        }
                    });
                }
                return null;
            }
        }.execute(episodesAdapter);
    }
}
