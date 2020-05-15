package com.example.pdm_projeto.model;

public class PostVideoItem {
    private String videoURL;
    private int userImg;
    private String time;

    public PostVideoItem(String videoURL, int userImg, String time) {
        this.videoURL = videoURL;
        this.userImg = userImg;
        this.time = time;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getUserImg() {
        return userImg;
    }

    public void setUserImg(int userImg) {
        this.userImg = userImg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
