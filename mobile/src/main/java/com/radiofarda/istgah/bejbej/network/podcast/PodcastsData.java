package com.radiofarda.istgah.bejbej.network.podcast;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;

public class PodcastsData {
    @SerializedName("live_stream_sources")
    @Expose
    private List<LiveStreamSource> liveStreamSources = null;
    @SerializedName("program_list")
    @Expose
    private List<ProgramList> programList = null;
    @SerializedName("info")
    @Expose
    private Info info;

    public List<LiveStreamSource> getLiveStreamSources() {
        return liveStreamSources;
    }

    public void setLiveStreamSources(RealmList<LiveStreamSource> liveStreamSources) {
        this.liveStreamSources = liveStreamSources;
    }

    public List<ProgramList> getProgramList() {
        return programList;
    }

    public void setProgramList(RealmList<ProgramList> programList) {
        this.programList = programList;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
