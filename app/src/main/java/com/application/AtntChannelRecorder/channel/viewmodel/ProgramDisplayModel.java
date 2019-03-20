package com.application.AtntChannelRecorder.channel.viewmodel;

import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;
import com.application.AtntChannelRecorder.user.repository.UserPojo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProgramDisplayModel {

    private String mTitle;

    private String mStartTime;

    private String mPoster;

    private ProgramPojo mProgramPojo;

    private boolean isCurrentlyRecording;

    private boolean isScheduledToRecord;

    ProgramDisplayModel(ProgramPojo channelPojo, UserPojo userPojo) {
        mProgramPojo = channelPojo;
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
        mStartTime += " - " + yourDateFormat.format(new Date(channelPojo.getStartTime() +
                channelPojo.getDuration() * 1000));

        //Determine if currently recording
        if(userPojo.getCurrentRecording() == null) {
            isCurrentlyRecording = false;
        }
        else if(userPojo.getCurrentRecording().getId() ==channelPojo.getId()) {
            isCurrentlyRecording = true;
        }
        else {
            isScheduledToRecord = false;
        }

        mPoster = channelPojo.getPoster();
    }


    public String getTitle() {
        return mTitle;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getPoster() {
        return mPoster;
    }

    public ProgramPojo getProgramPojo() {
        return mProgramPojo;
    }

    public boolean isCurrentlyRecording() {
        return isCurrentlyRecording;
    }

    public boolean isScheduledToRecord() {
        return isScheduledToRecord;
    }
}
