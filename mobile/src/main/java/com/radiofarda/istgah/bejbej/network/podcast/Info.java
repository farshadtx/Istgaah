package com.radiofarda.istgah.bejbej.network.podcast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Info extends RealmObject {
    public static final int SINGLE_ID = 1;
    @PrimaryKey
    private int id = SINGLE_ID;
    @SerializedName("zip_password")
    @Expose
    @Required
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

    @Override
    public String toString() {
        return "Info{" +
                ", zipPassword='" + zipPassword + '\'' +
                ", titleEncoding='" + titleEncoding + '\'' +
                ", descriptionEncoding='" + descriptionEncoding + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Info info = (Info) o;

        if (zipPassword != null ? !zipPassword.equals(info.zipPassword) : info.zipPassword != null)
            return false;
        if (titleEncoding != null ? !titleEncoding.equals(info.titleEncoding) : info.titleEncoding != null)
            return false;
        return descriptionEncoding != null ? descriptionEncoding.equals(info.descriptionEncoding) : info.descriptionEncoding == null;
    }

    @Override
    public int hashCode() {
        int result = zipPassword != null ? zipPassword.hashCode() : 0;
        result = 31 * result + (titleEncoding != null ? titleEncoding.hashCode() : 0);
        result = 31 * result + (descriptionEncoding != null ? descriptionEncoding.hashCode() : 0);
        return result;
    }


}
