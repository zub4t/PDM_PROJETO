package com.example.pdm_projeto.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pdm_projeto.R;
import com.example.pdm_projeto.model.PostVideoItem;
import com.example.pdm_projeto.model.TimelineItem;

public class PostVideoViewHolder extends BaseViewHolder {
    private TextView videoTime;
    private ImageView imgUser;

    public PostVideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoTime = itemView.findViewById(R.id.post_video_time);
        imgUser = itemView.findViewById(R.id.post_video_img);
    }

    @Override
    void setData(TimelineItem item) {
        PostVideoItem postVideoItem = item.getPostVideoItem();
        videoTime.setText(postVideoItem.getTime());
        Glide.with(super.itemView.getContext()).load(postVideoItem.getUserImg()).into(imgUser);
    }
}
