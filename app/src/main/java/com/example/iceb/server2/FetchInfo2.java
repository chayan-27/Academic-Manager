package com.example.iceb.server2;

import com.example.iceb.server.APKcl;
import com.example.iceb.server.Controller;
import com.example.iceb.server.Poll;
import com.example.iceb.server.UserFile;

import java.io.File;
import java.util.List;

import dagger.multibindings.StringKey;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface FetchInfo2 {
    @GET("Service1.svc/DownloadTimeTable")
    Call<Controller> getTimetest(@Query("sem") Integer sem, @Query("section") String section);

    @GET("Service1.svc/GetAssignment")
    Call<Controller> getAssignment(@Query("sem") Integer sem, @Query("section") String section);

    @GET("Service1.svc/DownloadAssignment")
    Call<Controller> downloadAssignment(@Query("Title") String title, @Query("section") String section);

    @GET("Service1.svc/GetStudyMaterial")
    Call<Controller> getstudymaterial(@Query("sem") Integer sem, @Query("section") String section, @Query("subject") String subject);

    @GET("Service1.svc/GetStudyMaterialSubject")
    Call<Controller> getstudymaterialsubject(@Query("sem") Integer sem, @Query("section") String section);


    @GET("Service1.svc/DownloadStudyMaterial")
    Call<Controller> downloadStudyMaterial(@Query("Title") String title, @Query("section") String section);

    @GET("Service1.svc/GetCoursePlan")
    Call<Controller> getcourseplan(@Query("sem") Integer sem, @Query("section") String section);

    @GET("Service1.svc/DownloadCoursePlan")
    Call<Controller> downloadCoursePlan(@Query("Subject") String subject, @Query("section") String section);

    @POST("Service1.svc/UploadUserFile")
    Call<Controller> sendassign(@Body UserFile userFile);

    @POST("Service1.svc/UnsubmitAssignment")
    Call<Controller> unsubassign(@Body UserFile userFile);

    @GET("Service1.svc/getpolllist")
    Call<Controller> getpolllist(@Query("sem") Integer sem, @Query("section") String section);

    @POST("Service1.svc/PollVote")
    Call<Controller> pollvote(@Body Poll poll);

    @POST("Service1.svc/downloadapk")
    Call<Controller> downloadapk(@Body APKcl apKcl);

    @GET("Service1.svc/viewpoll")
    Call<Controller> viewpoll(@Query("pid") Integer pid);




    @Multipart
    @POST("api/load/classroom")
    Call<CreateClass> getClassDetail(@Part("rollno") RequestBody rollno);

    @GET("api/customAdmin")
    Call<List<CustomAdmin>> getCustomAdmin();

    @GET("api/load/classroom")
    Call<List<CreateClass>> getClassID(@Query("rollno") String roll);

    @GET("api/load/classroom/timetable")
    Call<List<TimeTable1>> getTimetable(@Query("classroom_id") String classroom_id, @Query("semester") String semester);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @Multipart
    @POST("api/load/classroom/timetable")
    Call<TimeTable1> sendTimetable(@Part("classroom_id") RequestBody classroom_id, @Part("semester") RequestBody semester,@Part("upload_date")RequestBody upload_date ,@Part MultipartBody.Part file);


    @Multipart
    @POST("api/load/classroom/subjects")
    Call<AdminSubject> sendSubjects(@Part("classroom_id") RequestBody classroom_id, @Part("semester") RequestBody semester, @Part("subject_code") RequestBody subject_code);

    @GET("api/load/classroom/subjects")
    Call<List<AdminSubject>> getSubjects(@Query("classroom_id") String classroom_id, @Query("semester") String semester);

    @GET("api/load/classroom/subjects/materials")
    Call<List<SubjectResponse>> loadsubjectmaterial(@Query("subject_id") String subject_id);

    @Multipart
    @POST("api/load/classroom/subjects/materials")
    Call<SubjectResponse> sendMaterial(@Part("subject_id") RequestBody subject_id, @Part("topic") RequestBody topic, @Part("upload_date") RequestBody upload_date, @Part MultipartBody.Part file);

    @GET("api/load/classroom/subjects/assignassignment")
    Call<List<AdminAssignment>> getAssignments(@Query("subject_id") String subject_id);

    @Multipart
    @POST("api/load/classroom/subjects/assignassignment/submitassignment")
    Call<SubmitAssignment> sendmyassignment(@Part("assigned_assignment_id") RequestBody assigned_assignment_id, @Part("rollno") RequestBody rollno, @Part("submission_timestamp") RequestBody submission_timestamp, @Part MultipartBody.Part file);

    @Multipart
    @POST("api/load/classroom/subjects/assignassignment")
    Call<AdminAssignment> assignAssignment(@Part("subject_id") RequestBody subject_id, @Part("topic") RequestBody topic, @Part("upload_date") RequestBody upload_date, @Part("deadline") RequestBody deadline, @Part MultipartBody.Part file);

    @GET("api/load/classroom/subjects/assignassignment/submitassignment")
    Call<List<AssignmentRe>> getResponsesofAssign(@Query("assignment_id") String assignment_id);

    @GET("api/load/classroom/poll")
    Call<List<AdminPollUP>> getPollforClassroom(@Query("classroom_id") String classroom_id);

    @Multipart
    @POST("api/load/classroom/poll/responses")
    Call<PollRespond> submitmyPollResponse(@Part("poll_id") RequestBody poll_id, @Part("rollno") RequestBody rollno, @Part("response") RequestBody response);

    @GET("api/load/classroom/poll/responses")
    Call<PollResults> checkresultsforpoll(@Query("poll_id")String poll_id);

    @Multipart
    @POST("api/load/classroom/poll")
    Call<AdminPollUP> uploadPollNow(@Part("classroom_id") RequestBody classroom_id, @Part("title") RequestBody title, @Part("option1") RequestBody option1,@Part("option2") RequestBody option2,@Part("option3") RequestBody option3,@Part("option4") RequestBody option4,@Part("option5") RequestBody option5 ,@Part("deadline") RequestBody deadline);

    @Headers({"Content-Type:application/json", "Authorization:key=AAAAD769VQE:APA91bFCa2pGcpTdsL6IDAqjMygejd_u7FkRfF3jSBh9oYvVa8OosEkUAo7JDQAxCtc_iZCev623xq5k6Nk0JnBVgAHZ4siHBIztLU9g_ZaFZa4-ImcbOwjEJZEz7_e_Q3JMXnJ3dxn2"})
    @POST("fcm/send")
    Call<Mess> sendnoti(@Body Notif notif);
}
