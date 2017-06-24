package com.example.avnee.redditdemo.utils;

import com.orm.SugarRecord;

import io.realm.RealmObject;

/**
 * Created by avneeshk on 4/13/2017.
 */

public class OfflineGson extends RealmObject{
    String alternativeart;
    String pics;
    String gifs;
    String adviceanimals;
    String cats;
    String images;
    String photoshopbattles;
    String hmmm;
    String all;
    String aww;

    public String getAlternativeart() {
        return alternativeart;
    }

    public void setAlternativeart(String alternativeart) {
        this.alternativeart = alternativeart;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getGifs() {
        return gifs;
    }

    public void setGifs(String gifs) {
        this.gifs = gifs;
    }

    public String getAdviceanimals() {
        return adviceanimals;
    }

    public void setAdviceanimals(String adviceanimals) {
        this.adviceanimals = adviceanimals;
    }

    public String getCats() {
        return cats;
    }

    public void setCats(String cats) {
        this.cats = cats;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPhotoshopbattles() {
        return photoshopbattles;
    }

    public void setPhotoshopbattles(String photoshopbattles) {
        this.photoshopbattles = photoshopbattles;
    }

    public String getHmmm() {
        return hmmm;
    }

    public void setHmmm(String hmmm) {
        this.hmmm = hmmm;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getAww() {
        return aww;
    }

    public void setAww(String aww) {
        this.aww = aww;
    }

    public OfflineGson(){
    }

}
