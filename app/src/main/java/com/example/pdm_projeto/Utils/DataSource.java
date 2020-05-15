package com.example.pdm_projeto.Utils;

import com.example.pdm_projeto.R;
import com.example.pdm_projeto.model.HeaderTextItem;
import com.example.pdm_projeto.model.PostTextItem;
import com.example.pdm_projeto.model.PostVideoItem;
import com.example.pdm_projeto.model.TimelineItem;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static List<TimelineItem> getTimeLineData() {
        List<TimelineItem> mdata = new ArrayList<>();
        HeaderTextItem itemHeader = new HeaderTextItem("yesterday");
        TimelineItem headerTimeLineItem = new TimelineItem(itemHeader);
        PostTextItem postTextItem = new PostTextItem("this is a simple text", R.drawable.user1, "10:10");
        TimelineItem postTextTimeLineItem = new TimelineItem(postTextItem);
        PostVideoItem postVideoItem = new PostVideoItem("", R.drawable.user2, "9:20");
        TimelineItem postVideoTimeLine = new TimelineItem(postVideoItem);
        mdata.add(headerTimeLineItem);
        mdata.add(postTextTimeLineItem);
        mdata.add(postVideoTimeLine);

        return mdata;


    }
}
