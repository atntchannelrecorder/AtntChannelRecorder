package com.application.AtntChannelRecorder.user.repository;


import android.util.Log;

import com.application.AtntChannelRecorder.channel.repository.ProgramPojo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepo {

    public final static String TAG = "UserRepo";
    private static UserRepo sChannelRepo = null;
    private Flowable<UserPojo> mUserPojoFlowable;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;
    String USER_COLLECTION = "user";
    private UserPojo mUserPojo;
    UserService mUserService;

    public static UserRepo getInstance() {
        if(sChannelRepo == null) {
            sChannelRepo = new UserRepo();
        }
        return sChannelRepo;
    }

    //Initiate rxJava flow
    private UserRepo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-atnt-channel-recorder.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mUserService = retrofit.create(UserService.class);
        userId = "only_user";
        Log.d(TAG, "starting up flowable");
        mUserPojoFlowable = Flowable.create(emitter -> {
            db.collection(USER_COLLECTION)
                .document(userId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (snapshot != null && snapshot.exists()) {
                            Log.d("MainActivity", "USERREPO: emitting");
                            UserPojo userPojo = snapshot.toObject(UserPojo.class);
                            mUserPojo = userPojo;
                            emitter.onNext(userPojo);
                        }
                    }
                });
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<UserPojo> getUserPojoFlowable() {
        return mUserPojoFlowable;
    }

    public Single<String> addToRecording(int channelNumber, ProgramPojo programPojo) {
        return mUserService.bookRecording(channelNumber, programPojo.getId());
    }

    public Single<String> removeRecording(ProgramPojo programPojo) {
        return mUserService.removeRecording(programPojo.getId());
    }

}
