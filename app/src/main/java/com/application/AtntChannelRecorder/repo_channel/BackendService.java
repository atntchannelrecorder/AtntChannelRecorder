package com.application.AtntChannelRecorder.repo_channel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BackendService {

    @GET("getChannels/")
    Call<List<ChannelPojo>> listRepos(@Query("channel_number") int channelId);

}
