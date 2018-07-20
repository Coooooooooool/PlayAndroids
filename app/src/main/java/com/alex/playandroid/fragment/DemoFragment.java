package com.alex.playandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.activity.ProjectListActivity;
import com.alex.playandroid.bean.ProjectType;
import com.alex.playandroid.net.api.ArticleApi;
import com.alex.playandroid.net.callback.NetCallback;
import com.alex.playandroid.net.response.NetResponse;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends Fragment {


    private View view;
    private TagFlowLayout flowLayout;

    private List<ProjectType> projectList;

    public DemoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_demo, container, false);

        flowLayout = view.findViewById(R.id.tag_layout);

        requestProjectType();

        flowLayout.setOnTagClickListener((view1, position, parent) -> {
            Intent intent = new Intent(getActivity(), ProjectListActivity.class);
            intent.putExtra("id",projectList.get(position).getId());
            intent.putExtra("name",projectList.get(position).getName());
            startActivity(intent);
            return true;
        });

        return view;
    }

    private void requestProjectType(){
        ArticleApi.getInstance().requestProjectType().enqueue(new NetCallback<NetResponse<List<ProjectType>>>() {
            @Override
            public void onSuccess(NetResponse<List<ProjectType>> data, String msg) {
                projectList = data.getData();

                flowLayout.setAdapter(new TagAdapter<ProjectType>(projectList) {
                    @Override
                    public View getView(FlowLayout parent, int position, ProjectType projectType) {
                        TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_flow_tag,
                                flowLayout, false);
                        tv.setText(projectType.getName());
                        return tv;
                    }

                });
            }
        });
    }

}
