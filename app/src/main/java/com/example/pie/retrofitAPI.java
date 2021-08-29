package com.example.pie;
import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.Call;
public interface retrofitAPI {
    @GET("/getParentPercentage/{period}")
    Call <Double> getPercentageOfNewParents(@Path("period")  int period);

    @GET("/getParent/{period}")
    Call <Integer>  getNewActiveParent(@Path("period") int period);

    @GET("/getActivity/{period}")
    Call <Integer>  Activity(@Path("period") int period);

    @GET("/getNewKids/{period}")
    Call <Integer>  NewKids(@Path("period") int period);

    @GET("/getActivityPercentage/{period}")
    Call <Double> getPercentageOfActivities(@Path("period")  int period);

    @GET("/getKidPercentage/{period}")
    Call <Double>  getKidPercentage(@Path("period") int period);

    @GET("/TotalKidsPerCategor/{period}")
    Call <HashMap<String, Integer>> TotalKidsPeCategor(@Path("period") int period);

    @GET("/TotalKidsPerCategorForBar/{period}")
    Call <HashMap<Integer, HashMap<String, Integer >>> TotalKidsPerCategorForBar(@Path("period") int period);

}
