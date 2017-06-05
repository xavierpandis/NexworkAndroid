package com.cjx.nexwork.services;

import com.cjx.nexwork.model.Study;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Xavi on 08/02/2017.
 */

public interface StudyService {
    @GET("/api/estudios/user/{login}")
    Call<List<Study>> getAllStudies(
            /**
             * "Bearer [space ]token"
             */
            @Path("login") String login
    );

    @GET("/api/estudios/{id}")
    Call<Study> getStudy(
            @Path("id") Long id
    );

    @PUT("/api/estudios")
    Call<Study> editStudy(@Body Study body);
}
