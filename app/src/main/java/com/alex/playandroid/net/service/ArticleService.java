package com.alex.playandroid.net.service;

import com.alex.playandroid.bean.Banner;
import com.alex.playandroid.bean.Buzzwords;
import com.alex.playandroid.bean.HomeArticle;
import com.alex.playandroid.bean.KnowledgeHierarchy;
import com.alex.playandroid.bean.ProjectList;
import com.alex.playandroid.bean.ProjectType;
import com.alex.playandroid.bean.User;
import com.alex.playandroid.net.response.NetResponse;

import java.util.List;
import java.util.WeakHashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Alex on 2018/6/14.
 */

public interface ArticleService{


    @GET
    Call<NetResponse<HomeArticle>> requestHomeArticle(@Url String url);

    @GET("banner/json")
    Call<NetResponse<List<Banner>>> requestHomeBanner();

    @GET("tree/json")
    Call<NetResponse<List<KnowledgeHierarchy>>> requestKnowledgeHierarchy();

    @GET
    Call<NetResponse<HomeArticle>> requestArticleByHierarchy(@Url String url);

    @GET("project/tree/json")
    Call<NetResponse<List<ProjectType>>> requestProjectType();

    @GET
    Call<NetResponse<ProjectList>> requestProjectList(@Url String url);

    @FormUrlEncoded
    @POST("user/login")
    Call<NetResponse<User>> login(@FieldMap WeakHashMap<String, String> params);

    @FormUrlEncoded
    @POST("user/register")
    Call<String> register(@FieldMap WeakHashMap<String, String> params);

    @FormUrlEncoded
    @POST
    Call<NetResponse<HomeArticle>> search(@Url String url, @FieldMap WeakHashMap<String, String> params);

    @GET("hotkey/json")
    Call<NetResponse<List<Buzzwords>>> requestBuzzwords();
}
