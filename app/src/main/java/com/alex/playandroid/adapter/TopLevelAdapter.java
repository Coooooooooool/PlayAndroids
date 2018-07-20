package com.alex.playandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.activity.HierarchyActivity;
import com.alex.playandroid.bean.Article;
import com.alex.playandroid.bean.KnowledgeHierarchy;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2018/6/14.
 */

public class TopLevelAdapter extends RecyclerView.Adapter<TopLevelAdapter.TopLevelViewHolder> implements View.OnClickListener {

    private Context context;
    private List<KnowledgeHierarchy> list;
    private OnItemClickListener mItemClickListener;

    public TopLevelAdapter(Context context, List<KnowledgeHierarchy> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public TopLevelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_knowledge_hierarchy,null);
        view.setOnClickListener(this);
        TopLevelViewHolder articleViewHolder = new TopLevelViewHolder(view);
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(final TopLevelViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.tvTopLevel.setText(list.get(position).getName());
        KnowledgeHierarchy knowledgeHierarchy = list.get(position);
        List<KnowledgeHierarchy> children = knowledgeHierarchy.getChildren();
        List<String> childrenNames = new ArrayList<>();
        for(int i = 0;i<children.size();i++){
            childrenNames.add(children.get(i).getName());
        }

        holder.flowLayout.setAdapter(new TagAdapter<String>(childrenNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_flow_tv,
                        holder.flowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        holder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(context,HierarchyActivity.class);
                intent.putExtra("children",knowledgeHierarchy);
                intent.putExtra("select",position);
                context.startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    class TopLevelViewHolder extends RecyclerView.ViewHolder{

        TextView tvTopLevel;
        RecyclerView rvSubclass;
        TagFlowLayout flowLayout;

        public TopLevelViewHolder(View itemView) {
            super(itemView);
            tvTopLevel = itemView.findViewById(R.id.tv_top_leave);
            rvSubclass = itemView.findViewById(R.id.rv_subclass);
            flowLayout = itemView.findViewById(R.id.flow_layout);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}
