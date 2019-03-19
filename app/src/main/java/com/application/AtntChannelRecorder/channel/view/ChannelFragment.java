package com.application.AtntChannelRecorder.channel.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.AtntChannelRecorder.R;
import com.application.AtntChannelRecorder.channel.viewmodel.ProgramDisplayModel;
import com.application.AtntChannelRecorder.channel.viewmodel.ChannelViewModel;
import com.application.AtntChannelRecorder.shared.LoadingCallbacks;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

public class ChannelFragment extends Fragment {

    public static final String TAG = "ChannelFragment";
    private ChannelViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ChannelAdapter mChannelAdapter;
    protected CompositeDisposable mCompositeDisposable;

    public static ChannelFragment newInstance() {
        return new ChannelFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCompositeDisposable = new CompositeDisposable();
        mViewModel = ViewModelProviders.of(this).get(ChannelViewModel.class);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCompositeDisposable.dispose();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int channelNumber = getArguments().getInt("channel_number");

        View view = inflater.inflate(R.layout.channel_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.channel_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mChannelAdapter = new ChannelAdapter(new ArrayList(),
                this,
                getContext(),
                channelNumber,
                new LoadingCallbacks() {
                    @Override
                    public void loadingCallbacks(Single<String> singleLoading) {

                    }
                }
        );
        mRecyclerView.setAdapter(mChannelAdapter);
        mCompositeDisposable.add(
                mViewModel.getChannelFlowable(channelNumber)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith((new DisposableSubscriber<List<ProgramDisplayModel>>() {
                            @Override
                            public void onNext(List<ProgramDisplayModel> channelModels) {
                                Log.d(TAG, "Got channel models in fragment");
                                mChannelAdapter.setData(channelModels);
                            }

                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }))
        );
        return view;
    }


}
