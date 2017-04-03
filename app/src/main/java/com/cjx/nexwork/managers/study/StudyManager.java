package com.cjx.nexwork.managers.study;

import android.util.Log;

import com.cjx.nexwork.managers.BaseManager;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.services.StudyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xavi on 08/02/2017.
 */

public class StudyManager extends BaseManager{
    private static StudyManager ourInstance;
    private List<Study> studies;
    private StudyService studyService;
    private Study study;

    private StudyManager() {
        studyService = retrofit.create(StudyService.class);
    }

    public static StudyManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new StudyManager();
        }

        return ourInstance;
    }

    public synchronized void getUserWorks(final StudyCallback studyCallback) {
        Call<List<Study>> call = studyService.getAllStudies(TokenStoreManager.getInstance().getUsername());

        call.enqueue(new Callback<List<Study>>() {
            @Override
            public void onResponse(Call<List<Study>> call, Response<List<Study>> response) {
                studies = response.body();

                int code = response.code();

                if(code == 200 || code == 201){
                    if(studies != null){
                        studyCallback.onSuccess(studies);
                    }
                }
                else{
                    studyCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Study>> call, Throwable t) {
                Log.e("StudyManager->", "getAllStudies()->ERROR: " + t);

                studyCallback.onFailure(t);
            }
        });
    }

    public synchronized void getDetailStudy(Long id, final StudyDetailCallback studyDetailCallback) {
        Call<Study> call = studyService.getStudy(id);
        call.enqueue(new Callback<Study>() {
            @Override
            public void onResponse(Call<Study> call, Response<Study> response) {
                study = response.body();

                int code = response.code();

                if(code == 200 || code == 201){
                    studyDetailCallback.onSuccess(study);
                }
                else{
                    studyDetailCallback.onFailure(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<Study> call, Throwable t) {
                Log.e("StudyManager ", " performtaks->call.enqueue->onResponse err: " + t.toString());
                studyDetailCallback.onFailure(t);
            }

        });
    }
}
