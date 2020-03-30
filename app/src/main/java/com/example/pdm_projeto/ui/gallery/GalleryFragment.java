package com.example.pdm_projeto.ui.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pdm_projeto.R;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                LinearLayout linearLayout1 = root.findViewById(R.id.linearLayout1);
                for(int i = 0; i < 20; i++){
                    ImageView image = new ImageView(getContext());


                    String imageUri = "http://pdmfcup.ddns.net:8084/PDM/images/City2.jpg";

                   // Picasso.with(getContext()).load(imageUri).into(image);


                    Picasso.with(getContext()).load(imageUri)
                            .placeholder(R.drawable.teste2)
                            .error(R.drawable.teste);


                    linearLayout1.addView(image,472,292);
                }

            }
        });
        return root;
    }
}
