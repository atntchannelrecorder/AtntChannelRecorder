package com.application.AtntChannelRecorder.channel.repository;


import android.util.Log;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChannelRepo {

    public final static String TAG = "ChannelRepo";
    private static ChannelRepo sChannelRepo = null;

    private Flowable<List<ProgramPojo>> mChannel_1;
    private Flowable<List<ProgramPojo>> mChannel_2;
    private Flowable<List<ProgramPojo>> mChannel_3;

    private FlowableEmitter<List<ProgramPojo>> mChannel_1_Emitter;
    private FlowableEmitter<List<ProgramPojo>> mChannel_2_Emitter;
    private FlowableEmitter<List<ProgramPojo>> mChannel_3_Emitter;


    ChannelService mBackendService;

    public static ChannelRepo getInstance() {
        if(sChannelRepo == null) {
            sChannelRepo = new ChannelRepo();
        }
        return sChannelRepo;
    }


    private ChannelRepo() {
        Log.d(TAG, "Instantiating Channel Repo");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-atnt-channel-recorder.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mBackendService = retrofit.create(ChannelService.class);
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
        Log.d(TAG, "Pull from channel 1");
        mBackendService.listRepos(1).enqueue(new Callback<List<ProgramPojo>>() {
            @Override
            public void onResponse(Call<List<ProgramPojo>> call, Response<List<ProgramPojo>> response) {
                Log.d(TAG, "Got data from get request");
                mChannel_1_Emitter.onNext(response.body());
            }

            @Override
            public void onFailure(Call<List<ProgramPojo>> call, Throwable t) {
                Log.d(TAG, "Failed getting channel 1: " + t);
            }
        });
    }

    public void getChannel_2() {
        Log.d(TAG, "Pull from channel 2");
        mBackendService.listRepos(2).enqueue(new Callback<List<ProgramPojo>>() {
            @Override
            public void onResponse(Call<List<ProgramPojo>> call, Response<List<ProgramPojo>> response) {
                mChannel_2_Emitter.onNext(response.body());
            }

            @Override
            public void onFailure(Call<List<ProgramPojo>> call, Throwable t) {
                Log.d(TAG, "Failed getting channel 2: " + t);
            }
        });
    }

    public void getChannel_3() {
        Log.d(TAG, "Pull from channel 3");
        mBackendService.listRepos(3).enqueue(new Callback<List<ProgramPojo>>() {
            @Override
            public void onResponse(Call<List<ProgramPojo>> call, Response<List<ProgramPojo>> response) {
                mChannel_3_Emitter.onNext(response.body());
            }

            @Override
            public void onFailure(Call<List<ProgramPojo>> call, Throwable t) {
                Log.d(TAG, "Failed getting channel 3: " + t);
            }
        });
    }


    public Flowable<List<ProgramPojo>> getFlowableChannel(int channelNumber) {
        switch(channelNumber) {
            case 1:
                return mChannel_1;
            case 2:
                return mChannel_2;
            default:
                return mChannel_3;
        }
    }

    public void refreshPrograms() {
        mBackendService.refreshPrograms().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "Get channels after refresh");
                getChannel_1();
                getChannel_2();
                getChannel_3();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG,"Error refreshing:  " + t);
                getChannel_1();
                getChannel_2();
                getChannel_3();
            }
        });
    }

}
