package com.radiofarda.istgah.models;

import com.radiofarda.istgah.network.podcast.ProgramList;

/**
 * Created by maz on 2/13/17.
 */
public class Episode {
    private final ProgramList programList;

    public Episode(ProgramList programList) {
        this.programList = programList;
    }

    public String getDescription() {
        return null;
    }

    public String getDate() {
        return programList.getDate();
    }
}
