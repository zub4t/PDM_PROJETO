package com.example.pdm_projeto.ui.gallery;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pdm_projeto.ImageDetails;
import com.example.pdm_projeto.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);
       final List<ImageView> list = new ArrayList<>();
        ImageView imageView=null;
        for(int i = 0; i < 10;i++){
             imageView = new ImageView(root.getContext());
            String imageUri = "http://pdmfcup.ddns.net:8084/PDM/images/Water"+i+".jpg";
            Picasso.with(root.getContext()).load(imageUri).resize(500,300).into(imageView);
            list.add(imageView);
        }


        for(final ImageView image : list){

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText( getContext(),"algo", Toast.LENGTH_SHORT).show();
                    openDialog(image);
                }
            });
        }


        ImageAdpter imageAdpter = new ImageAdpter(root.getContext(), list);

      //  ArrayAdapter<ImageView> adapter = new ArrayAdapter<ImageView>(root.getContext(),android.R.layout.simple_list_item_1, list);
        GridView  gridView = (GridView) root.findViewById(R.id.gridView);
        gridView.setAdapter(imageAdpter);
        Log.e("tamanho da lista",list.size()+"");
        return root;
    }
    public void openDialog(ImageView v){
        ImageDetails modal = new ImageDetails(v);

        modal.show(getActivity().getSupportFragmentManager(),"tag");
    }

}
