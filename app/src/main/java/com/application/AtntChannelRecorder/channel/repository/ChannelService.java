package com.application.AtntChannelRecorder.channel.repository;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChannelService {

    @GET("programs/")
    Call<List<ProgramPojo>> listRepos(@Query("channel_number") int channelId);

    @GET("getUpdatedShows/")
    Call<String> refreshPrograms();

}
