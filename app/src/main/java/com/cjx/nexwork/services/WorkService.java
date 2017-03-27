package com.cjx.nexwork.services;

import com.cjx.nexwork.model.Study;
import com.cjx.nexwork.model.Work;
import com.cjx.nexwork.model.dto.WorkDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jotabono on 8/2/17.
 */

public interface WorkService {
    @GET("/api/trabajos/usuario/{login}")
    Call<List<WorkDTO>> getWorksUser(
            /**
             * "Bearer [space ]token"
             */
            @Path("login") String login
    );

    @GET("/api/trabajos/{id}")
    Call<Work> getWork(
            @Path("id") Long id
    );

    @POST("/api/trabajos")
    Call<Work> postWork(
            @Body Work body
    );
}
