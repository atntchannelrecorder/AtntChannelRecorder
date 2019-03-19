package com.application.AtntChannelRecorder.main;

import android.arch.lifecycle.ViewModel;

import com.application.AtntChannelRecorder.channel.repository.ChannelRepo;
import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;
import com.application.AtntChannelRecorder.channel.viewmodel.ProgramDisplayModel;
import com.application.AtntChannelRecorder.user.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class MainViewModel extends ViewModel {

    public MainViewModel() {
        super();
    }

    public Flowable<String> getRecordingNotificationFlowable() {
        return UserRepo.getInstance().getUserPojoFlowable()
            .map(userPojo -> {
                if(userPojo.getCurrentRecording() != null) {
                    return userPojo.getCurrentRecording().getTitle() + " recording";
                }
                else if(userPojo.getPastRecordings() != null && userPojo.getPastRecordings().size() > 0) {
                    return userPojo.getPastRecordings().size() + " scheduled recordings";
                }
                else {
                    return null;
                }
            });
    }
}
