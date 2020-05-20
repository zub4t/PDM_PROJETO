package com.example.pdm_projeto.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pdm_projeto.R;
import com.example.pdm_projeto.model.PostTextItem;
import com.example.pdm_projeto.model.TimelineItem;

public class PostTextViewHolder extends BaseViewHolder {
    private TextView txtPost,txtTime;
    private ImageView imgUser;
    public PostTextViewHolder(@NonNull View itemView) {
        super(itemView);
        txtPost = itemView.findViewById(R.id.post_text_content);
        txtTime = itemView.findViewById(R.id.post_text_time);
        imgUser = itemView.findViewById(R.id.post_text_img);
    }

    public TextView getTxtPost() {
        return txtPost;
    }

    public void setTxtPost(TextView txtPost) {
        this.txtPost = txtPost;
    }

    public TextView getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(TextView txtTime) {
        this.txtTime = txtTime;
    }

    public ImageView getImgUser() {
        return imgUser;
    }

    public void setImgUser(ImageView imgUser) {
        this.imgUser = imgUser;
    }

    @Override
    void setData(TimelineItem item) {
        PostTextItem post = item.getPostTextItem();
        txtPost.setText(post.getPostText());
        txtTime.setText(post.getTime());
        Glide.with(itemView.getContext()).load(post.getImgUser()).into(imgUser);
    }
}
