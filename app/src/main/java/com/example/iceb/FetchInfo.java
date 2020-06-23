package com.example.iceb;

import com.example.iceb.server.Controller;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FetchInfo {
    @GET("Service1.svc/DownloadTimeTable")
    Call<Controller> getTimetest(@Query("sem")Integer sem, @Query("section")String section);

    @GET("Service1.svc/GetAssignment")
    Call<Controller> getAssignment(@Query("sem")Integer sem, @Query("section")String section);

    @GET("Service1.svc/DownloadAssignment")
    Call<Controller> downloadAssignment(@Query("Title")String title, @Query("section")String section);

    @GET("Service1.svc/GetStudyMaterial")
    Call<Controller> getstudymaterial(@Query("sem")Integer sem, @Query("section")String section,@Query("subject")String subject);

    @GET("Service1.svc/GetStudyMaterialSubject")
    Call<Controller> getstudymaterialsubject(@Query("sem")Integer sem, @Query("section")String section);


    @GET("Service1.svc/DownloadStudyMaterial")
    Call<Controller> downloadStudyMaterial(@Query("Title")String title, @Query("section")String section);

    @GET("Service1.svc/GetCoursePlan")
    Call<Controller> getcourseplan(@Query("sem")Integer sem, @Query("section")String section);

    @GET("Service1.svc/DownloadCoursePlan")
    Call<Controller> downloadCoursePlan(@Query("Subject")String subject, @Query("section")String section);






}
