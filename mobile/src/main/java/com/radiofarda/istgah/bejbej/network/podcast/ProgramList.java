package com.radiofarda.istgah.bejbej.network.podcast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ProgramList extends RealmObject {
    @SerializedName("file_id")
    @Expose
    @PrimaryKey
    @Required
    private String fileId;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("size_high_quality")
    @Expose
    private String sizeHighQuality;
    @SerializedName("size_medium_quality")
    @Expose
    private String sizeMediumQuality;
    @SerializedName("size_low_quality")
    @Expose
    private String sizeLowQuality;
    @SerializedName("size_high_quality_mb")
    @Expose
    private String sizeHighQualityMb;
    @SerializedName("size_medium_quality_mb")
    @Expose
    private String sizeMediumQualityMb;
    @SerializedName("size_low_quality_mb")
    @Expose
    private String sizeLowQualityMb;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("artwork_40")
    @Expose
    private String artwork40;
    @SerializedName("artwork_500")
    @Expose
    private String artwork500;
    @SerializedName("link_high_quality")
    @Expose
    private String linkHighQuality;
    @SerializedName("link_medium_quality")
    @Expose
    private String linkMediumQuality;
    @SerializedName("link_low_quality")
    @Expose
    private String linkLowQuality;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSizeHighQuality() {
        return sizeHighQuality;
    }

    public void setSizeHighQuality(String sizeHighQuality) {
        this.sizeHighQuality = sizeHighQuality;
    }

    public String getSizeMediumQuality() {
        return sizeMediumQuality;
    }

    public void setSizeMediumQuality(String sizeMediumQuality) {
        this.sizeMediumQuality = sizeMediumQuality;
    }

    public String getSizeLowQuality() {
        return sizeLowQuality;
    }

    public void setSizeLowQuality(String sizeLowQuality) {
        this.sizeLowQuality = sizeLowQuality;
    }

    public String getSizeHighQualityMb() {
        return sizeHighQualityMb;
    }

    public void setSizeHighQualityMb(String sizeHighQualityMb) {
        this.sizeHighQualityMb = sizeHighQualityMb;
    }

    public String getSizeMediumQualityMb() {
        return sizeMediumQualityMb;
    }

    public void setSizeMediumQualityMb(String sizeMediumQualityMb) {
        this.sizeMediumQualityMb = sizeMediumQualityMb;
    }

    public String getSizeLowQualityMb() {
        return sizeLowQualityMb;
    }

    public void setSizeLowQualityMb(String sizeLowQualityMb) {
        this.sizeLowQualityMb = sizeLowQualityMb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArtwork40() {
        return artwork40;
    }

    public void setArtwork40(String artwork40) {
        this.artwork40 = artwork40;
    }

    public String getArtwork500() {
        return artwork500;
    }

    public void setArtwork500(String artwork500) {
        this.artwork500 = artwork500;
    }

    public String getLinkHighQuality() {
        return linkHighQuality;
    }

    public void setLinkHighQuality(String linkHighQuality) {
        this.linkHighQuality = linkHighQuality;
    }

    public String getLinkMediumQuality() {
        return linkMediumQuality;
    }

    public void setLinkMediumQuality(String linkMediumQuality) {
        this.linkMediumQuality = linkMediumQuality;
    }

    public String getLinkLowQuality() {
        return linkLowQuality;
    }

    public void setLinkLowQuality(String linkLowQuality) {
        this.linkLowQuality = linkLowQuality;
    }

    @Override
    public String toString() {
        return "ProgramList{" +
                "fileId='" + fileId + '\'' +
                ", duration='" + duration + '\'' +
                ", sizeHighQuality='" + sizeHighQuality + '\'' +
                ", sizeMediumQuality='" + sizeMediumQuality + '\'' +
                ", sizeLowQuality='" + sizeLowQuality + '\'' +
                ", sizeHighQualityMb='" + sizeHighQualityMb + '\'' +
                ", sizeMediumQualityMb='" + sizeMediumQualityMb + '\'' +
                ", sizeLowQualityMb='" + sizeLowQualityMb + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", artwork40='" + artwork40 + '\'' +
                ", artwork500='" + artwork500 + '\'' +
                ", linkHighQuality='" + linkHighQuality + '\'' +
                ", linkMediumQuality='" + linkMediumQuality + '\'' +
                ", linkLowQuality='" + linkLowQuality + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramList that = (ProgramList) o;

        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null)
            return false;
        if (sizeHighQuality != null ? !sizeHighQuality.equals(that.sizeHighQuality) : that.sizeHighQuality != null)
            return false;
        if (sizeMediumQuality != null ? !sizeMediumQuality.equals(that.sizeMediumQuality) : that.sizeMediumQuality != null)
            return false;
        if (sizeLowQuality != null ? !sizeLowQuality.equals(that.sizeLowQuality) : that.sizeLowQuality != null)
            return false;
        if (sizeHighQualityMb != null ? !sizeHighQualityMb.equals(that.sizeHighQualityMb) : that.sizeHighQualityMb != null)
            return false;
        if (sizeMediumQualityMb != null ? !sizeMediumQualityMb.equals(that.sizeMediumQualityMb) : that.sizeMediumQualityMb != null)
            return false;
        if (sizeLowQualityMb != null ? !sizeLowQualityMb.equals(that.sizeLowQualityMb) : that.sizeLowQualityMb != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (artwork40 != null ? !artwork40.equals(that.artwork40) : that.artwork40 != null)
            return false;
        if (artwork500 != null ? !artwork500.equals(that.artwork500) : that.artwork500 != null)
            return false;
        if (linkHighQuality != null ? !linkHighQuality.equals(that.linkHighQuality) : that.linkHighQuality != null)
            return false;
        if (linkMediumQuality != null ? !linkMediumQuality.equals(that.linkMediumQuality) : that.linkMediumQuality != null)
            return false;
        return linkLowQuality != null ? linkLowQuality.equals(that.linkLowQuality) : that.linkLowQuality == null;
    }

    @Override
    public int hashCode() {
        int result = fileId != null ? fileId.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (sizeHighQuality != null ? sizeHighQuality.hashCode() : 0);
        result = 31 * result + (sizeMediumQuality != null ? sizeMediumQuality.hashCode() : 0);
        result = 31 * result + (sizeLowQuality != null ? sizeLowQuality.hashCode() : 0);
        result = 31 * result + (sizeHighQualityMb != null ? sizeHighQualityMb.hashCode() : 0);
        result = 31 * result + (sizeMediumQualityMb != null ? sizeMediumQualityMb.hashCode() : 0);
        result = 31 * result + (sizeLowQualityMb != null ? sizeLowQualityMb.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (artwork40 != null ? artwork40.hashCode() : 0);
        result = 31 * result + (artwork500 != null ? artwork500.hashCode() : 0);
        result = 31 * result + (linkHighQuality != null ? linkHighQuality.hashCode() : 0);
        result = 31 * result + (linkMediumQuality != null ? linkMediumQuality.hashCode() : 0);
        result = 31 * result + (linkLowQuality != null ? linkLowQuality.hashCode() : 0);
        return result;
    }


}
