package com.alex.playandroid.bean;

import java.util.List;

/**
 * Created by Alex on 2018/6/14.
 */

public class HomeArticle {

    private int curPage;
    private List<Article> datas;


    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<Article> getDatas() {
        return datas;
    }

    public void setDatas(List<Article> datas) {
        this.datas = datas;
    }
}
