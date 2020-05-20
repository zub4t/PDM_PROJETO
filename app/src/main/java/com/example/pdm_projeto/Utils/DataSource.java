package com.example.pdm_projeto.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pdm_projeto.ImageDetails;
import com.example.pdm_projeto.MainActivity;
import com.example.pdm_projeto.R;
import com.example.pdm_projeto.model.HeaderTextItem;
import com.example.pdm_projeto.model.PostTextItem;
import com.example.pdm_projeto.model.PostVideoItem;
import com.example.pdm_projeto.model.TimelineItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataSource {


    public static void getCalories(Context context, final String url, final PostTextItem postTextItem) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            postTextItem.setPostText("Kcal Count: " + response.get("calories"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return hour24hrs + ":" + minutes;
    }

    public static List<TimelineItem> getTimeLineData(Context context) {
        List<TimelineItem> mdata = new ArrayList<>();
        HeaderTextItem itemHeader = new HeaderTextItem("Today");
        TimelineItem headerTimeLineItem = new TimelineItem(itemHeader);
        PostTextItem postTextItem = new PostTextItem("Kcal Count: 100", R.drawable.user1, getCurrentTime());
        getCalories(context, "http://pdmfcup.ddns.net:8084/PDM/webServicePDM?func=getToday", postTextItem);
        TimelineItem postTextTimeLineItem = new TimelineItem(postTextItem);
        itemHeader = new HeaderTextItem("This week");
        TimelineItem headerTimeLineItem2 = new TimelineItem(itemHeader);
        postTextItem = new PostTextItem("Kcal Count: 2000", R.drawable.user1, getCurrentTime());
        getCalories(context, "http://pdmfcup.ddns.net:8084/PDM/webServicePDM?func=getWeek", postTextItem);
        TimelineItem postTextTimeLineItem2 = new TimelineItem(postTextItem);
        mdata.add(headerTimeLineItem);
        mdata.add(postTextTimeLineItem);
        mdata.add(headerTimeLineItem2);
        mdata.add(postTextTimeLineItem2);
        return mdata;
    }
}
