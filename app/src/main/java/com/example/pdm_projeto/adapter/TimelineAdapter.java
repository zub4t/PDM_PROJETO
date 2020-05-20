package com.example.pdm_projeto.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    public String getEmailWithDot(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        String defaultValue = "";
        String login= sharedPreferences.getString("login", defaultValue);
        return login;
    }
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
        PostTextViewHolder item = null;
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
                item =   new PostTextViewHolder(view);
                DataSource.getCalories(view.getContext(),"http://pdmfcup.ddns.net:8084/PDM/webServicePDM?func=getToday&email=" + getEmailWithDot(),item.getTxtPost());
                return item;
            case (Constant.ITEM_HEADER_TEXT_VIEWTYPE_WEEK):
                view = LayoutInflater.from(mContext).inflate(R.layout.item_post_text,parent,false);
                item = new PostTextViewHolder(view);
                DataSource.getCalories(view.getContext(),"http://pdmfcup.ddns.net:8084/PDM/webServicePDM?func=getWeek&email=" + getEmailWithDot(),item.getTxtPost());
                return item;
            case (Constant.ITEM_HEADER_TEXT_VIEWTYPE_MONTH):
                view = LayoutInflater.from(mContext).inflate(R.layout.item_post_text,parent,false);
                item = new PostTextViewHolder(view);
                DataSource.getCalories(view.getContext(),"http://pdmfcup.ddns.net:8084/PDM/webServicePDM?func=getMonth&email=" + getEmailWithDot(),item.getTxtPost());
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
