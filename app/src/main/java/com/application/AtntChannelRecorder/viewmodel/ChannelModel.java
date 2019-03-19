package com.application.AtntChannelRecorder.viewmodel;

import com.application.AtntChannelRecorder.repo_channel.ChannelPojo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChannelModel {
    private String mId;

    private String mTitle;

    private String mStartTime;

    private String mDuration;

    private String mPoster;

    ChannelModel(ChannelPojo channelPojo) {
        mId = channelPojo.getId();

        //Create title with duration
        float hours =  (float)channelPojo.getDuration()/3600;
        DecimalFormat df = new DecimalFormat("###.#");
        mTitle = channelPojo.getTitle() + " (" + df.format(hours) + " hrs)";

        //First check if the same day
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal2.setTime(new Date(channelPojo.getStartTime()));
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

        Date startDate = new Date(channelPojo.getStartTime());

        if(!sameDay) {
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("M/dd", Locale.US);
            mStartTime =  simpleDateformat.format(startDate) + ", ";
        }
        else {
            mStartTime = "Today, ";
        }
        SimpleDateFormat yourDateFormat = new SimpleDateFormat("h:mm a", Locale.US);
        mStartTime += yourDateFormat.format(startDate);
        mStartTime += " - " + yourDateFormat.format(new Date(channelPojo.getStartTime() + channelPojo.getDuration() * 1000));



//        mStartTime = startDate.toString();
        mPoster = channelPojo.getPoster();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }
}
