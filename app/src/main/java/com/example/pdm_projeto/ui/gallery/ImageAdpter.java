package com.example.pdm_projeto.ui.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pdm_projeto.ImageDetails;

import java.util.List;

public class ImageAdpter extends BaseAdapter {
    private Context mContext  = null;
    private  List<ImageView> list =  null;
    public ImageAdpter(Context context, List<ImageView> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image = list.get(position);
        return image;
    }


}
