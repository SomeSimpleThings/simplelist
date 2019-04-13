package com.somethingsimple.simplelist.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SimpleNotesApi {

    @GET("/todos/{id}")
    public Call<NotePojo> getNoteWithID(@Path("id") int id);

    @GET("/todos")
    public Call<List<NotePojo>> getAllNotes();

    @GET("/todos")
    public Call<List<NotePojo>> getNoteOfUser(@Query("userId") int id);

    @POST("/todos")
    public Call<NotePojo> postData(@Body NotePojo data);

}
