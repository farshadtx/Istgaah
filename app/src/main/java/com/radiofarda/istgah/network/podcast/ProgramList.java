
package com.radiofarda.istgah.network.podcast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgramList {

    @SerializedName("file_id")
    @Expose
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

}
