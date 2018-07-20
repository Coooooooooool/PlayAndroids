package com.alex.playandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.activity.ProjectActivity;
import com.alex.playandroid.activity.ProjectListActivity;
import com.alex.playandroid.activity.WebActivity;
import com.alex.playandroid.bean.Project;
import com.alex.playandroid.bean.ProjectList;
import com.alex.playandroid.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Alex on 2018/6/19.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>{


    private Context context;
    private ProjectList projectList;
    private OnItemClickListener mItemClickListener;

    public ProjectAdapter(Context context,ProjectList projectList){
        this.context = context;
        this.projectList = projectList;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project,null);
        ProjectViewHolder viewHolder = new ProjectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Project project = projectList.getDatas().get(position);
        holder.tvName.setText(project.getTitle());
        holder.tvDesc.setText(project.getDesc());
        Glide.with(context).load(project.getEnvelopePic()).asBitmap().into(holder.ivBg);
       // Glide.with(context).load(project.getEnvelopePic()).into(holder.cardView);

        holder.ivBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("点击","触发onClick");
                Intent intent = new Intent(context,WebActivity.class);
                intent.putExtra("title",project.getTitle());
                intent.putExtra("url",project.getLink());
                Pair<View, String> p1 = Pair.create((View)holder.ivBg, "img_view_1");
                Pair<View, String> p2 = Pair.create((View)holder.tvName, "title_1");
                Pair<View, String> p3 = Pair.create((View)holder.tvDesc, "tv_bottom");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, p1,p2,p3);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return projectList.getDatas().size();
    }


    class ProjectViewHolder extends RecyclerView.ViewHolder{

        TextView tvName,tvDesc;
        ImageView ivBg;
        CardView cardView;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            ivBg = itemView.findViewById(R.id.iv_bg);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}
