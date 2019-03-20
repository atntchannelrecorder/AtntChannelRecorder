package com.application.AtntChannelRecorder.channel.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.AtntChannelRecorder.R;
import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;
import com.application.AtntChannelRecorder.channel.viewmodel.ProgramDisplayModel;
import com.application.AtntChannelRecorder.shared.LoadingCallbacks;
import com.application.AtntChannelRecorder.user.repository.UserRepo;
import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    public static final String TAG = "ChannelAdapter";
    List<ProgramDisplayModel> mChannelModels;
    Fragment mFragment;
    int mChannelNumber;
    Context mContext;

    public ChannelAdapter(List<ProgramDisplayModel> channelModels,
                          Fragment fragment, Context context, int channelNumber) {
        mChannelModels = channelModels;
        mFragment = fragment;
        mContext = context;
        mChannelNumber = channelNumber;
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView startTime;
        ImageView moviePoster;
        Button button;
        ImageView statusIcon;
        ProgressBar recordBookingProgress;
        ChannelViewHolder(View v) {
            super(v);
            titleView = v.findViewById(R.id.tv_row_title);
            startTime = v.findViewById(R.id.tv_row_start_time);
            button = v.findViewById(R.id.bt_row_record);
            moviePoster = v.findViewById(R.id.iv_movie_poster);
            statusIcon = v.findViewById(R.id.iv_status_icon);
            recordBookingProgress = v.findViewById(R.id.pb_record_booking);
        }
    }


    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.program_list_row, viewGroup, false);
        return new ChannelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder channelViewHolder, int i) {
        ProgramDisplayModel channelModel = mChannelModels.get(i);
        channelViewHolder.titleView.setText(channelModel.getTitle());
        channelViewHolder.startTime.setText(channelModel.getStartTime());
        channelViewHolder.recordBookingProgress.setVisibility(View.INVISIBLE);

        if(channelModel.isCurrentlyRecording()) {
            Drawable myDrawable = mContext.getResources().getDrawable(R.drawable.record_dot);
            channelViewHolder.statusIcon.setImageDrawable(myDrawable);
            channelViewHolder.statusIcon.setVisibility(View.VISIBLE);
        }
        else {
            channelViewHolder.statusIcon.setVisibility(View.INVISIBLE);
        }
        if(mFragment == null) { //Fragment null when we use on activity
            Glide.with(mContext)
                    .load(channelModel.getPoster())
                    .into(channelViewHolder.moviePoster);
        }
        else { //Fragment not null
            Glide.with(mFragment)
                    .load(channelModel.getPoster())
                    .into(channelViewHolder.moviePoster);
        }

        boolean isCancel = (channelModel.isCurrentlyRecording() || channelModel.isScheduledToRecord());

        if(isCancel) {
            channelViewHolder.button.setText("Cancel");
        }
        else {
            channelViewHolder.button.setText("Record");
        }

        channelViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Single<String> restRequest;
                if(isCancel) {
                    restRequest = UserRepo.getInstance().removeRecording(channelModel.getProgramPojo());
                }
                else {
                    restRequest = UserRepo.getInstance().addToRecording(mChannelNumber, channelModel.getProgramPojo());
                }

                channelViewHolder.recordBookingProgress.setVisibility(View.VISIBLE);
                restRequest.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String s) {
                                Log.d(TAG, "Success: " + s);
                                channelViewHolder.recordBookingProgress.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "Failed: " + e);
                                channelViewHolder.recordBookingProgress.setVisibility(View.INVISIBLE);
                            }
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        if(mChannelModels == null) return 0;
        return mChannelModels.size();
    }

    public void setData(List<ProgramDisplayModel> channelModels) {
        mChannelModels = channelModels;
        notifyDataSetChanged();
    }
}
