package com.alex.playandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alex.playandroid.R;
import com.alex.playandroid.adapter.ProjectAdapter;
import com.alex.playandroid.bean.Project;
import com.alex.playandroid.bean.ProjectList;
import com.alex.playandroid.net.api.ArticleApi;
import com.alex.playandroid.net.callback.NetCallback;
import com.alex.playandroid.net.response.NetResponse;
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager;

import java.util.List;

public class ProjectListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvProject;

    private ProjectAdapter projectAdapter;

    private ProjectList projectList;
    private List<Project> list;
    private int page = 0;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        id = getIntent().getIntExtra("id",0);

        initToolbar();

        initRvProject();

        requestProjectByType();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorMain));
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initRvProject() {
        rvProject = (RecyclerView) findViewById(R.id.rv_project);
        SkidRightLayoutManager mSkidRightLayoutManager= new SkidRightLayoutManager(1.8f, 0.4f);//1.5f, 0.85f)
        rvProject.setLayoutManager(mSkidRightLayoutManager);
    }

    private void requestProjectByType(){
        ArticleApi.getInstance().requestProjectList(page,id).enqueue(new NetCallback<NetResponse<ProjectList>>() {
            @Override
            public void onSuccess(NetResponse<ProjectList> data, String msg) {
                projectList = data.getData();
                list = data.getData().getDatas();
                projectAdapter = new ProjectAdapter(ProjectListActivity.this,projectList);
                rvProject.setAdapter(projectAdapter);
//                projectAdapter.setItemClickListener(new ProjectAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        Intent intent = new Intent(ProjectListActivity.this,ProjectActivity.class);
//                        intent.putExtra("title",list.get(position).getTitle());
//                        intent.putExtra("link",list.get(position).getLink());
//                        startActivity(intent);
//                    }
//                });
            }
        });
    }

}
