package com.alex.playandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.playandroid.R;
import com.alex.playandroid.activity.HierarchyActivity;
import com.alex.playandroid.adapter.TopLevelAdapter;
import com.alex.playandroid.bean.KnowledgeHierarchy;
import com.alex.playandroid.net.api.ArticleApi;
import com.alex.playandroid.net.callback.NetCallback;
import com.alex.playandroid.net.response.NetResponse;
import com.alex.playandroid.view.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment {

    private View view;

    private RecyclerView rvKnowledgeHierarchy;
    private List<KnowledgeHierarchy> knowledgeHierarchyList;
    private ArrayList<KnowledgeHierarchy> childrenList;

    private TopLevelAdapter topLevelAdapter;

    public TypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_type, container, false);

        knowledgeHierarchyList = new ArrayList<>();
        childrenList = new ArrayList<>();

        initRecyclerView();

        requestKnowledgeHierarchy();

        return view;
    }

    private void initRecyclerView() {
        rvKnowledgeHierarchy = view.findViewById(R.id.rv_knowledge_hierarchy);
        initItemDecoration();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvKnowledgeHierarchy.setLayoutManager(manager);
    }

    private void initItemDecoration(){
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,20);//底部间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,20);//左间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,20);//右间距
        rvKnowledgeHierarchy.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

    }


    private void requestKnowledgeHierarchy() {
        ArticleApi.getInstance().requestKnowledgeHierarchy().enqueue(new NetCallback<NetResponse<List<KnowledgeHierarchy>>>() {
            @Override
            public void onSuccess(NetResponse<List<KnowledgeHierarchy>> data, String msg) {
                knowledgeHierarchyList = data.getData();
                topLevelAdapter = new TopLevelAdapter(getActivity(),knowledgeHierarchyList);
                rvKnowledgeHierarchy.setAdapter(topLevelAdapter);
                setItemClickListener();
            }
        });
    }

    private void setItemClickListener() {
        topLevelAdapter.setItemClickListener(position -> {
            KnowledgeHierarchy knowledgeHierarchy = knowledgeHierarchyList.get(position);
            Intent intent = new Intent(getActivity(),HierarchyActivity.class);
            intent.putExtra("children",knowledgeHierarchy);
            startActivity(intent);
        });
    }


}
