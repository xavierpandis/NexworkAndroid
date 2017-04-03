package com.cjx.nexwork.services;

import android.net.http.HttpResponseCache;

import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.model.Work;
import com.cjx.nexwork.model.dto.WorkDTO;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jotabono on 8/2/17.
 */

public interface WorkService {
    @GET("/api/trabajos/usuario/{login}")
    Call<List<WorkDTO>> getWorksUser(@Path("login") String login);

    @GET("/api/trabajos/{id}")
    Call<Work> getWork(@Path("id") Long id);

    @POST("/api/trabajos")
    Call<Work> postWork(@Body Work body);

    @GET("/api/_search/trabajos")
    Call<List<Work>> searchWorkByName(@Query("query") String query);

    @PUT("/api/trabajos")
    Call<Work> editWork(@Body Work body);
}
