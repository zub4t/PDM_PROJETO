package com.example.pdm_projeto.adapter;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.pdm_projeto.R;
import com.example.pdm_projeto.model.HeaderTextItem;
import com.example.pdm_projeto.model.TimelineItem;

public class HeaderTextViewHolder extends BaseViewHolder {
    private TextView tvHeader;
    public HeaderTextViewHolder(@NonNull View itemView) {
        super(itemView);
        tvHeader = itemView.findViewById(R.id.header_text);
    }

    @Override
    void setData(TimelineItem item) {
        HeaderTextItem headerTextItem = item.getHeaderTextItem();
        tvHeader.setText(headerTextItem.getHeadertext());
    }
}
