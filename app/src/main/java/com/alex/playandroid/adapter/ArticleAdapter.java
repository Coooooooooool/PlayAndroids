package com.alex.playandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.bean.Article;

import java.util.List;

/**
 * Created by Alex on 2018/6/14.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Article> list;
    private OnItemClickListener mItemClickListener;

    public ArticleAdapter(Context context, List<Article> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article,null);
        view.setOnClickListener(this);
        ArticleViewHolder articleViewHolder = new ArticleViewHolder(view);
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvAuthor.setText(list.get(position).getAuthor());
        holder.tvTime.setText(list.get(position).getNiceDate());
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

    class ArticleViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvAuthor,tvType,tvTime;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_article_title);
            tvAuthor = itemView.findViewById(R.id.tv_article_author);
            tvTime = itemView.findViewById(R.id.tv_article_time);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}
