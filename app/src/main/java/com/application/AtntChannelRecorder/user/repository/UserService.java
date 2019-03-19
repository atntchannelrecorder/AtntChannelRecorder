package com.application.AtntChannelRecorder.user.repository;

import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("recording/")
    Single<String> bookRecording(@Query("channel_number") int channelNumber,
                                 @Query("program_id") long programId);

}
