package com.example.pdm_projeto.ui.gallery;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pdm_projeto.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pl.droidsonroids.gif.GifImageView;

public class Util {
    public static void setDescription(String desc, Activity activity){
        final LayoutInflater factory = activity.getLayoutInflater();

        final View textEntryView = factory.inflate(R.layout.image_details, null);
        TextView description = (TextView)  textEntryView.findViewById(R.id.textDetails);
        description.setText(desc);
    }
    public static void showLoadingGif(boolean show, Activity activity){
        final LayoutInflater factory = activity.getLayoutInflater();

        final View textEntryView = factory.inflate(R.layout.image_details, null);
        GifImageView gif = (GifImageView)  textEntryView.findViewById(R.id.loading);
        if(show){
            gif.setVisibility(View.VISIBLE);
        } else {
            gif.setVisibility(View.INVISIBLE);
        }
        //tentar dar refresh talvez
/*
        ViewGroup vg = activity.findViewById(R.id.image_layout);
        vg.invalidate();*/

    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            Log.e("errot",e+"");
            return null;
        }
    }

    public static  Drawable resize(Drawable image, Resources r,int w , int h ) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, w, h, false);
        return new BitmapDrawable(r, bitmapResized);
    }
}
