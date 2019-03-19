package com.application.AtntChannelRecorder.channel.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;
import com.application.AtntChannelRecorder.channel.repository.ChannelRepo;
import com.application.AtntChannelRecorder.user.repository.UserPojo;
import com.application.AtntChannelRecorder.user.repository.UserRepo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

public class ChannelViewModel extends ViewModel {

    public ChannelViewModel() {
        super();
    }

    /**
     * Combine two flowables. The first is the channel flowable, the next is the user flowable
     * The user flowable contains what the user is recording and has on queue
     * The channel flowable is all currenlty available shows
     * @param channelNumber
     * @return
     */
    public Flowable<List<ProgramDisplayModel>> getChannelFlowable(int channelNumber) {
        Flowable<List<ProgramPojo>> programFlowable = ChannelRepo.getInstance()
                .getFlowableChannel(channelNumber);
        Flowable<UserPojo> userPojoFlowable = UserRepo.getInstance().getUserPojoFlowable();

        return Flowable.combineLatest(programFlowable, userPojoFlowable, (listProgramPojo, userPojo) -> {
            List<ProgramDisplayModel> channelDisplayModels = new ArrayList<ProgramDisplayModel>();
            for(ProgramPojo channelPojo : listProgramPojo) {
                channelDisplayModels.add(new ProgramDisplayModel(channelPojo, userPojo));
            }
            return channelDisplayModels;
        });
    }
    // TODO: Implement the ViewModel

}
