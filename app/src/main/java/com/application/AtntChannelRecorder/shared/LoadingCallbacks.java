package com.application.AtntChannelRecorder.shared;

import io.reactivex.Single;

public interface LoadingCallbacks {
    void loadingCallbacks(Single<String> singleLoading);
}
