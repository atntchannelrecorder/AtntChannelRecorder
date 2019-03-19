package com.application.AtntChannelRecorder.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.application.AtntChannelRecorder.repo_channel.ChannelPojo;
import com.application.AtntChannelRecorder.repo_channel.ChannelRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class ChannelViewModel extends ViewModel {

    public ChannelViewModel() {
        super();
    }

    public Flowable<List<ChannelModel>> getChannelFlowable(int channelNumber) {
        return ChannelRepo.getInstance().getFlowableChannel(channelNumber)
                .map(channelPojos -> {
                    List<ChannelModel> channelDisplayModels = new ArrayList<ChannelModel>();
                    for(ChannelPojo channelPojo : channelPojos) {
                        channelDisplayModels.add(new ChannelModel(channelPojo));
                    }
                    return channelDisplayModels;
                });
    }

    // TODO: Implement the ViewModel

}
