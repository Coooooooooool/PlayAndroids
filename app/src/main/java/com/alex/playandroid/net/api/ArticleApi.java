package com.alex.playandroid.net.api;

import com.alex.playandroid.bean.Banner;
import com.alex.playandroid.bean.Buzzwords;
import com.alex.playandroid.bean.HomeArticle;
import com.alex.playandroid.bean.KnowledgeHierarchy;
import com.alex.playandroid.bean.ProjectList;
import com.alex.playandroid.bean.ProjectType;
import com.alex.playandroid.bean.User;
import com.alex.playandroid.net.Configure;
import com.alex.playandroid.net.RetrofitClient;
import com.alex.playandroid.net.response.NetResponse;
import com.alex.playandroid.net.service.ArticleService;

import java.util.List;
import java.util.WeakHashMap;

import retrofit2.Call;


/**
 * Created by Alex on 2018/6/14.
 */

public class ArticleApi {

    private static ArticleApi articleApi;
    private ArticleService articleService;

    public static ArticleApi getInstance() {
        if (articleApi == null) {
            synchronized (ArticleApi.class) {
                if (articleApi == null) {
                    articleApi = new ArticleApi();
                }
            }
        }
        return articleApi;
    }
    private ArticleApi() {
        articleService = RetrofitClient.getInstance().create(ArticleService.class);
    }


    public Call<NetResponse<HomeArticle>> requestHomeArticle(int page) {
        return articleService.requestHomeArticle(Configure.BASE_URL+"article/list/"+page+"/json");
    }


    public Call<NetResponse<List<Banner>>> requestHomeBanner() {
        return articleService.requestHomeBanner();
    }

    public Call<NetResponse<List<KnowledgeHierarchy>>> requestKnowledgeHierarchy() {
        return articleService.requestKnowledgeHierarchy();
    }

    public Call<NetResponse<HomeArticle>> requestArticleByHierarchy(int page,int cid) {
        return articleService.requestArticleByHierarchy(Configure.BASE_URL+"article/list/"+page+"/json?cid="+cid);
    }

    public Call<NetResponse<List<ProjectType>>> requestProjectType() {
        return articleService.requestProjectType();
    }

    public Call<NetResponse<ProjectList>> requestProjectList(int page,int id) {
        return articleService.requestProjectList(Configure.BASE_URL+"article/list/"+page+"/json?cid="+id);
    }

    public Call<NetResponse<User>> login(WeakHashMap<String, String> params){
        return articleService.login(params);
    }

    public Call<String> register(WeakHashMap<String, String> params){
        return articleService.register(params);
    }

    public Call<NetResponse<HomeArticle>> search(int page,String k){
        WeakHashMap<String, String> params = new WeakHashMap<>();
        params.put("k",k);
        return articleService.search(Configure.BASE_URL+"article/query/"+page+"/json",params);
    }

    public Call<NetResponse<List<Buzzwords>>> requestBuzzwords() {
        return articleService.requestBuzzwords();
    }

}
