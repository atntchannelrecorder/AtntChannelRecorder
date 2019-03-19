package com.application.AtntChannelRecorder.user.repository;

import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;
import com.google.firebase.firestore.PropertyName;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPojo {

    @PropertyName("userId")
    String userId;

    @PropertyName("currentRecording")
    ProgramPojo currentRecording;

    @PropertyName("scheduledRecordings")
    List<ProgramPojo> scheduledRecordings;

    @PropertyName("pastRecordings")
    List<ProgramPojo> pastRecordings;

    UserPojo() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public ProgramPojo getCurrentRecording() {
        return currentRecording;
    }

    public void setCurrentRecording(ProgramPojo currentRecording) {
        this.currentRecording = currentRecording;
    }

    public List<ProgramPojo> getScheduledRecordings() {
        return scheduledRecordings;
    }

    public void setScheduledRecordings(List<ProgramPojo> scheduledRecordings) {
        this.scheduledRecordings = scheduledRecordings;
    }

    public List<ProgramPojo> getPastRecordings() {
        return pastRecordings;
    }

    public void setPastRecordings(List<ProgramPojo> pastRecordings) {
        this.pastRecordings = pastRecordings;
    }
}
