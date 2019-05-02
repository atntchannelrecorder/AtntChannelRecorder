package com.application.AtntChannelRecorder.user.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.application.AtntChannelRecorder.R;

import io.reactivex.disposables.CompositeDisposable;

public class RecordingActivity extends AppCompatActivity {

    CompositeDisposable mCompositeDisposable;

    public static Intent getMyIntent(Context context) {
        return new Intent(context, RecordingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Recordings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mCompositeDisposable = new CompositeDisposable();
        RecyclerView recyclerView = findViewById(R.id.rv_recordings);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
