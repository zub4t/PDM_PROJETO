package com.example.pdm_projeto;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AlertDialogLayout;

public class ImageDetails extends AppCompatDialogFragment {

    ImageView imageview;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.image_details,null);
       ImageView mainImage =  view.findViewById(R.id.imgDetails);
         mainImage.setImageDrawable(imageview.getDrawable());
        TextView text =  view.findViewById(R.id.textDetails);
        text.setText("ola");
        Log.d("img ",mainImage.getDrawable().toString());


        builder.setView(view).setTitle("Detalhes da Imagem")
        .setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
    public ImageDetails(ImageView view){
        this.imageview = view ;
    }
}
