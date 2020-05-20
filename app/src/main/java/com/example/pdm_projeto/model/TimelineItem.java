package com.example.pdm_projeto.model;

import com.example.pdm_projeto.Utils.Constant;

public class TimelineItem {
    private HeaderTextItem headerTextItem;
    private PostTextItem postTextItem;
    private PostVideoItem postVideoItem;
    private int viewType;

    public TimelineItem(HeaderTextItem headerTextItem) {
        this.headerTextItem = headerTextItem;
        viewType = Constant.ITEM_HEADER_TEXT_VIEWTYPE;
    }

    public TimelineItem(PostTextItem postTextItem) {
        this.postTextItem = postTextItem;
        viewType = Constant.ITEM_POST_TEXT_VIEWTYPE;

    }
    public TimelineItem(PostTextItem postTextItem,int type) {
        this.postTextItem = postTextItem;
        viewType = type;

    }

    public TimelineItem(PostVideoItem postVideoItem) {
        this.postVideoItem = postVideoItem;
        viewType = Constant.ITEM_POST_VIDEO_VIEWTYPE;

    }

    public HeaderTextItem getHeaderTextItem() {
        return headerTextItem;
    }

    public void setHeaderTextItem(HeaderTextItem headerTextItem) {
        this.headerTextItem = headerTextItem;
    }

    public PostTextItem getPostTextItem() {
        return postTextItem;
    }

    public void setPostTextItem(PostTextItem postTextItem) {
        this.postTextItem = postTextItem;
    }

    public PostVideoItem getPostVideoItem() {
        return postVideoItem;
    }

    public void setPostVideoItem(PostVideoItem postVideoItem) {
        this.postVideoItem = postVideoItem;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
