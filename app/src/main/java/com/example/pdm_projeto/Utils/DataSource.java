package com.example.pdm_projeto.Utils;

import com.example.pdm_projeto.R;
import com.example.pdm_projeto.model.HeaderTextItem;
import com.example.pdm_projeto.model.PostTextItem;
import com.example.pdm_projeto.model.PostVideoItem;
import com.example.pdm_projeto.model.TimelineItem;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataSource {
    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return hour24hrs + ":" + minutes;
    }
    public static List<TimelineItem> getTimeLineData() {
        List<TimelineItem> mdata = new ArrayList<>();
        HeaderTextItem itemHeader = new HeaderTextItem("Today");
        
        TimelineItem headerTimeLineItem = new TimelineItem(itemHeader);
        PostTextItem postTextItem = new PostTextItem("Kcal Count: 100", R.drawable.user1, getCurrentTime());
        TimelineItem postTextTimeLineItem = new TimelineItem(postTextItem);
        itemHeader = new HeaderTextItem("This week");
        TimelineItem headerTimeLineItem2 = new TimelineItem(itemHeader);
        postTextItem = new PostTextItem("Kcal Count: 2000", R.drawable.user1, getCurrentTime());
        TimelineItem postTextTimeLineItem2 = new TimelineItem(postTextItem);
        mdata.add(headerTimeLineItem);
        mdata.add(postTextTimeLineItem);
        mdata.add(headerTimeLineItem2);
        mdata.add(postTextTimeLineItem2);
        return mdata;
    }
}
