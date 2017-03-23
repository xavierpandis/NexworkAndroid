package com.cjx.nexwork.managers.work;

import android.content.Context;
import android.util.Log;

import com.cjx.nexwork.managers.BaseManager;
import com.cjx.nexwork.managers.TokenStoreManager;
import com.cjx.nexwork.managers.UserLoginManager;
import com.cjx.nexwork.managers.study.StudyCallback;
import com.cjx.nexwork.managers.study.StudyDetailCallback;
import com.cjx.nexwork.managers.study.StudyManager;
import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.model.Work;
import com.cjx.nexwork.model.dto.WorkDTO;
import com.cjx.nexwork.services.StudyService;
import com.cjx.nexwork.services.WorkService;
import com.cjx.nexwork.util.CustomProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jotabono on 8/2/17.
 */

public class WorkManager extends BaseManager {

    private static WorkManager ourInstance;
    private ArrayList<Work> works = new ArrayList<>();
    private WorkService workService;
    private Work work;

    private WorkManager() {
        workService = retrofit.create(WorkService.class);
    }

    public static WorkManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new WorkManager();
        }

        return ourInstance;
    }

    public synchronized void getWorksUser(final WorkCallback workCallback) {
        Call<List<WorkDTO>> call = workService.getWorksUser(TokenStoreManager.getInstance().getUsername());
        works = new ArrayList<>();
        call.enqueue(new Callback<List<WorkDTO>>() {
            @Override
            public void onResponse(Call<List<WorkDTO>> call, Response<List<WorkDTO>> response) {
                List<WorkDTO> workDTO = response.body();

                if(workDTO != null) {

                    for (WorkDTO workdto : workDTO) {
                        works.add(workdto.getWork());
                    }

                    int code = response.code();

                    if (code == 200 || code == 201) {
                        if (works != null) {
                            workCallback.onSuccess(works);
                        }
                    } else {
                        workCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<WorkDTO>> call, Throwable t) {
                Log.e("StudyManager->", "getAllStudies()->ERROR: " + t);

                workCallback.onFailure(t);
            }
        });
    }

    public synchronized void getDetailWork(Long id, final WorkDetailCallback workDetailCallback) {
        Call<Work> call = workService.getWork(id);
        call.enqueue(new Callback<Work>() {
            @Override
            public void onResponse(Call<Work> call, Response<Work> response) {
                work = response.body();

                int code = response.code();

                if(code == 200 || code == 201){
                    workDetailCallback.onSuccess(work);
                }
                else{
                    workDetailCallback.onFailure(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<Work> call, Throwable t) {
                Log.e("StudyManager ", " performtaks->call.enqueue->onResponse err: " + t.toString());
                workDetailCallback.onFailure(t);
            }

        });
    }
}
