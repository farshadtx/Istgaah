package com.radiofarda.istgah.bejbej.network.podcast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class LiveStreamSource extends RealmObject {
    @SerializedName("order")
    @Expose
    String order;
    @SerializedName("url")
    @Expose
    @PrimaryKey
    @Required
    String url;
    @SerializedName("desc")
    @Expose
    private String desc;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "LiveStreamSource{" +
                "order='" + order + '\'' +
                ", url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

}
