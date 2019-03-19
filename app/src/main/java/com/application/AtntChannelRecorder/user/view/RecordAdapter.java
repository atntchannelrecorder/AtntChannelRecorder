package com.application.AtntChannelRecorder.user.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;
import com.application.AtntChannelRecorder.channel.view.ChannelAdapter;
import com.application.AtntChannelRecorder.shared.LoadingCallbacks;
import com.application.AtntChannelRecorder.channel.viewmodel.ProgramDisplayModel;

import java.util.List;

public class RecordAdapter extends ChannelAdapter {

    RecordAdapter(List<ProgramDisplayModel> channelModels, Context context, LoadingCallbacks loadingCallbacks) {
        super(channelModels, null, context, 0, loadingCallbacks);
    }

    @Override
    protected void actionButtonBind(Button actionButton, ProgramPojo programPojo) {
        actionButton.setText("Cancel");
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
