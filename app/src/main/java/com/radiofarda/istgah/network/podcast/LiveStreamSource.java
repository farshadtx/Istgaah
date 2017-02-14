
package com.radiofarda.istgah.network.podcast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveStreamSource {

    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("url")
    @Expose
    private String url;
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

}
