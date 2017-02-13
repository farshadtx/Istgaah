
package com.radiofarda.istgah.network.podcast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("zip_password")
    @Expose
    private String zipPassword;
    @SerializedName("title_encoding")
    @Expose
    private String titleEncoding;
    @SerializedName("description_encoding")
    @Expose
    private String descriptionEncoding;

    public String getZipPassword() {
        return zipPassword;
    }

    public void setZipPassword(String zipPassword) {
        this.zipPassword = zipPassword;
    }

    public String getTitleEncoding() {
        return titleEncoding;
    }

    public void setTitleEncoding(String titleEncoding) {
        this.titleEncoding = titleEncoding;
    }

    public String getDescriptionEncoding() {
        return descriptionEncoding;
    }

    public void setDescriptionEncoding(String descriptionEncoding) {
        this.descriptionEncoding = descriptionEncoding;
    }

}
