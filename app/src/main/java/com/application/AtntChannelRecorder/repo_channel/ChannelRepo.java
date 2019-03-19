package com.application.AtntChannelRecorder.repo_channel;


import android.util.Log;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChannelRepo {

    public final static String TAG = "ChannelRepo";
    private static ChannelRepo sChannelRepo = null;
    private Flowable<List<ChannelPojo>> mChannel_1;
    private Flowable<List<ChannelPojo>> mChannel_2;
    private Flowable<List<ChannelPojo>> mChannel_3;
    private FlowableEmitter<List<ChannelPojo>> mChannel_1_Emitter;
    private FlowableEmitter<List<ChannelPojo>> mChannel_2_Emitter;
    private FlowableEmitter<List<ChannelPojo>> mChannel_3_Emitter;


    BackendService mBackendService;

    public static ChannelRepo getInstance() {
        if(sChannelRepo == null) {
            sChannelRepo = new ChannelRepo();
        }
        return sChannelRepo;
    }


    private ChannelRepo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-atnt-channel-recorder.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mBackendService = retrofit.create(BackendService.class);
        mChannel_1 = Flowable.create(emitter -> {
            mChannel_1_Emitter = emitter;
            getChannel_1();
        }, BackpressureStrategy.BUFFER);
        mChannel_2 = Flowable.create(emitter -> {
            mChannel_2_Emitter = emitter;
            getChannel_2();
        }, BackpressureStrategy.BUFFER);
        mChannel_3 = Flowable.create(emitter -> {
            mChannel_3_Emitter = emitter;
            getChannel_3();
        }, BackpressureStrategy.BUFFER);



    }

    public void getChannel_1() {
        mBackendService.listRepos(1).enqueue(new Callback<List<ChannelPojo>>() {
            @Override
            public void onResponse(Call<List<ChannelPojo>> call, Response<List<ChannelPojo>> response) {
                Log.d(TAG, "Got data from get request");
                mChannel_1_Emitter.onNext(response.body());
            }

            @Override
            public void onFailure(Call<List<ChannelPojo>> call, Throwable t) {

            }
        });
    }

    public void getChannel_2() {
        mBackendService.listRepos(2).enqueue(new Callback<List<ChannelPojo>>() {
            @Override
            public void onResponse(Call<List<ChannelPojo>> call, Response<List<ChannelPojo>> response) {
                mChannel_2_Emitter.onNext(response.body());
            }

            @Override
            public void onFailure(Call<List<ChannelPojo>> call, Throwable t) {

            }
        });
    }

    public void getChannel_3() {
        mBackendService.listRepos(3).enqueue(new Callback<List<ChannelPojo>>() {
            @Override
            public void onResponse(Call<List<ChannelPojo>> call, Response<List<ChannelPojo>> response) {
                mChannel_3_Emitter.onNext(response.body());
            }

            @Override
            public void onFailure(Call<List<ChannelPojo>> call, Throwable t) {

            }
        });
    }


    public Flowable<List<ChannelPojo>> getFlowableChannel(int channelNumber) {
        switch(channelNumber) {
            case 1:
                return mChannel_1;
            case 2:
                return mChannel_2;
            default:
                return mChannel_3;
        }
    }

}
