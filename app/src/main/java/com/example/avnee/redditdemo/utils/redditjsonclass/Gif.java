package com.example.avnee.redditdemo.utils.redditjsonclass;

/**
 * Created by avnee on 4/2/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Gif {

    @SerializedName("source")
    @Expose
    private Source_ source;
    @SerializedName("resolutions")
    @Expose
    private List<Resolution_> resolutions = null;

    public Source_ getSource() {
        return source;
    }

    public void setSource(Source_ source) {
        this.source = source;
    }

    public List<Resolution_> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<Resolution_> resolutions) {
        this.resolutions = resolutions;
    }

}