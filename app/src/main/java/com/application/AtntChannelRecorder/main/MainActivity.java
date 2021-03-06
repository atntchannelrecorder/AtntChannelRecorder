package com.application.AtntChannelRecorder.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.application.AtntChannelRecorder.R;
import com.application.AtntChannelRecorder.channel.view.ChannelFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends AppCompatActivity {

    CompositeDisposable mCompositeDisposable;
    MainViewModel mViewModel;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = findViewById(R.id.vp_channel_pager);
        pager.setOffscreenPageLimit(2);
        ChannelPagerAdapter pagerAdapter = new ChannelPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);
        ConstraintLayout recordingNotifyLayout = findViewById(R.id.cl_recording_notification);
        Context context = this;
        TextView tvRecordingNotify = findViewById(R.id.tv_recording_notification);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recordingNotifyLayout.setVisibility(View.INVISIBLE);
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(
                mViewModel.getRecordingNotificationFlowable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith((new DisposableSubscriber<String>() {
                            @Override
                            public void onNext(String recordingNotify) {
                                if(recordingNotify.equals("EMPTY")) {
                                    Log.d(TAG, "Setting to invisible");
                                    recordingNotifyLayout.setVisibility(View.INVISIBLE);
                                }
                                else {
                                    Log.d(TAG, "Setting to visible");
                                    recordingNotifyLayout.setVisibility(View.VISIBLE);
                                    tvRecordingNotify.setText(recordingNotify);
                                }
                            }
                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }))
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_refresh:
                mViewModel.refreshRepo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ChannelPagerAdapter extends FragmentPagerAdapter {
        public ChannelPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("channel_number", 1);
                    ChannelFragment fragobj1 = new ChannelFragment();
                    fragobj1.setArguments(bundle1);
                    return fragobj1;
                case 1:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("channel_number", 2);
                    ChannelFragment fragobj2 = new ChannelFragment();
                    fragobj2.setArguments(bundle2);
                    return fragobj2;
                default:
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("channel_number", 3);
                    ChannelFragment fragobj3 = new ChannelFragment();
                    fragobj3.setArguments(bundle3);
                    return fragobj3;
            }
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Channel 1";
                case 1:
                    return "Channel 2";
                default:
                    return "Channel 3";

            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
