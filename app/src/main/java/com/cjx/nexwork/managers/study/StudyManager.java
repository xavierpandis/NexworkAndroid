package com.cjx.nexwork.managers.study;

import android.content.Context;
import android.util.Log;

import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.services.StudyService;
import com.cjx.nexwork.util.CustomProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xavi on 08/02/2017.
 */

public class StudyManager {
    private static StudyManager ourInstance;
    private List<Study> studies;
    private Retrofit retrofit;
    private Context context;
    private StudyService studyService;
    private Study study;

    private StudyManager(Context cntxt) {
        context = cntxt;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.getInstance(context).get("app.baseUrl"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        studyService = retrofit.create(StudyService.class);
    }

    public static StudyManager getInstance(Context cntxt) {
        if (ourInstance == null) {
            ourInstance = new StudyManager(cntxt);
        }

        ourInstance.context = cntxt;

        return ourInstance;
    }

    public synchronized void getAllPlayers(final StudyCallback studyCallback) {
        Call<List<Study>> call = studyService.getAllStudies(UserLoginManager.getInstance(context).getUsername() ,UserLoginManager.getInstance(context).getBearerToken());

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
        Call<Study> call = studyService.getStudy(id , UserLoginManager.getInstance(context).getBearerToken());
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
