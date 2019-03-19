package com.application.AtntChannelRecorder.channel.repository;

import com.google.firebase.firestore.PropertyName;
import com.google.gson.annotations.SerializedName;

public class ProgramPojo {

    @PropertyName("id")
    @SerializedName("id")
    private long mId;

    @PropertyName("title")
    @SerializedName("title")
    private String mTitle;

    @PropertyName("startTime")
    @SerializedName("startTime")
    private int mStartTime;

    @PropertyName("duration")
    @SerializedName("duration")
    private int mDuration;

    @PropertyName("poster")
    @SerializedName("poster")
    private String mPoster;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getStartTime() {
        return mStartTime;
    }

    public void setStartTime(int startTime) {
        mStartTime = startTime;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }
}
