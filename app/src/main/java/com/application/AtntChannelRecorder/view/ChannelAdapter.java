package com.application.AtntChannelRecorder.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.AtntChannelRecorder.R;
import com.application.AtntChannelRecorder.viewmodel.ChannelModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    List<ChannelModel> mChannelModels;
    Fragment mFragment;

    ChannelAdapter(List<ChannelModel> channelModels, Fragment fragment) {
        mChannelModels = channelModels;
        mFragment = fragment;
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView startTime;
        ImageView moviePoster;
        Button button;
        ChannelViewHolder(View v) {
            super(v);
            titleView = v.findViewById(R.id.tv_row_title);
            startTime = v.findViewById(R.id.tv_row_start_time);
            button = v.findViewById(R.id.bt_row_record);
            moviePoster = v.findViewById(R.id.iv_movie_poster);
        }
    }


    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.channel_list_row, viewGroup, false);
        return new ChannelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder channelViewHolder, int i) {
        ChannelModel channelModel = mChannelModels.get(i);
        channelViewHolder.titleView.setText(channelModel.getTitle());
        channelViewHolder.startTime.setText(channelModel.getStartTime());
        Glide.with(mFragment)
                .load(channelModel.getPoster())
                .into(channelViewHolder.moviePoster);
    }

    @Override
    public int getItemCount() {
        if(mChannelModels == null) return 0;
        return mChannelModels.size();
    }

    public void setData(List<ChannelModel> channelModels) {
        mChannelModels = channelModels;
        notifyDataSetChanged();
    }
}
