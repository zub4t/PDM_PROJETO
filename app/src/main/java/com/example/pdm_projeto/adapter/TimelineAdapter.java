package com.example.pdm_projeto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdm_projeto.R;
import com.example.pdm_projeto.Utils.Constant;
import com.example.pdm_projeto.Utils.DataSource;
import com.example.pdm_projeto.model.TimelineItem;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<TimelineItem> mdata;

    public TimelineAdapter(Context mContext, List<TimelineItem> mdata) {
        this.mContext = mContext;
        this.mdata = mdata;
    }

    @Override
    public int getItemViewType(int position) {
        return mdata.get(position).getViewType();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case (Constant.ITEM_HEADER_TEXT_VIEWTYPE):
                view = LayoutInflater.from(mContext).inflate(R.layout.item_header,parent,false);
                return new HeaderTextViewHolder(view);
            case (Constant.ITEM_POST_TEXT_VIEWTYPE):
                view = LayoutInflater.from(mContext).inflate(R.layout.item_post_text,parent,false);
                return new PostTextViewHolder(view);
            case (Constant.ITEM_POST_VIDEO_VIEWTYPE):
                view = LayoutInflater.from(mContext).inflate(R.layout.item_post_video,parent,false);
                return new PostVideoViewHolder(view);
            case (Constant.ITEM_HEADER_TEXT_VIEWTYPE_TODAY):
                view = LayoutInflater.from(mContext).inflate(R.layout.item_post_text,parent,false);
                PostTextViewHolder item =   new PostTextViewHolder(view);
                DataSource.getCalories(view.getContext(),"http://pdmfcup.ddns.net:8084/PDM/webServicePDM?func=getToday",item.getTxtPost());
                return item;
            default: throw  new IllegalArgumentException();

        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setData(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        if (mdata != null)
            return mdata.size();
        else return 0;
    }
}
