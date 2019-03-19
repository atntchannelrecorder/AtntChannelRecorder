package com.application.AtntChannelRecorder.shared;

import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class LoadingActivity extends AppCompatActivity implements LoadingCallbacks{

    protected abstract ProgressBar getProgressBar();

    public void loadingCallbacks(Single<String> singleLoading) {
        singleLoading.observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<String>() {
                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onError(Throwable e) {

                }
            });
    }

}
