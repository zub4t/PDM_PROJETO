package com.example.pdm_projeto;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.pdm_projeto.ui.gallery.Util;

public class ImageDetails extends AppCompatDialogFragment {

    ImageView imageview;
    String description;
    LayoutInflater inflater;
    boolean show=false;
    View view;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);

         inflater = getActivity().getLayoutInflater();
         view =  inflater.inflate(R.layout.image_details,null);
       ImageView mainImage =  view.findViewById(R.id.imgDetails);
         mainImage.setImageDrawable(Util.resize(imageview.getDrawable(),getResources(),630,630));

        mainImage.setPadding(0,0,0,0);

        //Getting name of food int the photo title
        String descriptionName = "";
        int i;
        for (i = 0; i < this.description.length(); i++) {
            if (this.description.charAt(i) != '\n') {
                descriptionName += this.description.charAt(i);
            }else{
                break;
            }
        }
        this.description = this.description.substring(i);

        TextView text =  view.findViewById(R.id.textDetails);
        text.setText(this.description);
        if(show){
            view.findViewById(R.id.loading).setVisibility(View.VISIBLE);

        }

        Log.d("img ",mainImage.getDrawable().toString());
        TextView title = new TextView(getActivity().getBaseContext());

        // You Can Customise your Title here
        title.setText(descriptionName);
        title.setBackgroundResource(R.color.colorPrimary);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);


        builder.setView(view).setCustomTitle(title)
        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        return builder.create();
    }

    public ImageDetails(ImageView view, String description,Boolean show){
        this.description = description;
        this.imageview = view ;
        this.show = show;
    }
}
