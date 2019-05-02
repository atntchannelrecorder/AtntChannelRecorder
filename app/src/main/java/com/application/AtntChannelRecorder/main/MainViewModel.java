package com.application.AtntChannelRecorder.main;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.application.AtntChannelRecorder.channel.repository.ChannelRepo;
import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;
import com.application.AtntChannelRecorder.channel.viewmodel.ProgramDisplayModel;
import com.application.AtntChannelRecorder.user.repository.UserPojo;
import com.application.AtntChannelRecorder.user.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class MainViewModel extends ViewModel {
    public static final String TAG = "MainViewModel";

    public MainViewModel() {
        super();
    }

    public Flowable<String> getRecordingNotificationFlowable() {
        return UserRepo.getInstance().getUserPojoFlowable()
            .map(userPojo -> {
                return getRecordingNotificationFromUser(userPojo);
            });
    }

    public String getRecordingNotificationFromUser(UserPojo userPojo) {
        if(userPojo.getCurrentRecording() == null) {
            return "EMPTY";
        }
        if(userPojo.getCurrentRecording() != null) {
            Log.d(TAG, "return non null recording");
            return userPojo.getCurrentRecording().getTitle() + " recording";
        }
        else if(userPojo.getPastRecordings() != null && userPojo.getPastRecordings().size() > 0) {
            Log.d(TAG, "return non null from scheduling");
            return userPojo.getPastRecordings().size() + " scheduled recordings";
        }
        else {
            Log.d(TAG, "return non null 2nd");
            return null;
        }
    }


    public void refreshRepo() {
        ChannelRepo.getInstance().refreshPrograms();
    }
}
