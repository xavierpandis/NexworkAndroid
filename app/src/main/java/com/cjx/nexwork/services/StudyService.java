package com.cjx.nexwork.services;

import com.cjx.nexwork.model.Study;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
            @Path("login") String login,
            @Header("Authorization") String Authorization
    );

    @GET("/api/estudios/{id}")
    Call<Study> getStudy(
            @Path("id") Long id,
            @Header("Authorization") String Authorization
    );
}
