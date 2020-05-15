package com.example.pdm_projeto.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdm_projeto.model.TimelineItem;

public abstract  class BaseViewHolder extends RecyclerView.ViewHolder {

    abstract  void setData(TimelineItem item);
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
