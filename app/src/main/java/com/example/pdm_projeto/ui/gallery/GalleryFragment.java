package com.example.pdm_projeto.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pdm_projeto.ImageDetails;
import com.example.pdm_projeto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class GalleryFragment extends Fragment {
    private HashMap<String,String> imageMap = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);
       final HashMap<ImageView, String> list = new HashMap<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projeto-pdm-17aad.firebaseio.com/");
        DatabaseReference mDatabaseRef = mDatabase.child("imagens");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    Object value = ds.getValue();
                    HashMap<String, String> conexao = (HashMap<String, String>) value;
                    String descricao = conexao.get("descricao");
                    imageMap.put(key, descricao);
                }
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://projeto-pdm-17aad.appspot.com");
                for(String imagem : imageMap.keySet()){
                    final ImageView imageView = new ImageView(root.getContext());
                    String imageUri = "https://firebasestorage.googleapis.com/v0/b/projeto-pdm-17aad.appspot.com/o/images%2Fteste%2F" + imagem + ".jpg?alt=media&token=1727c60b-68ce-421e-a8e2-d7907d654a22";
                    Picasso.with(root.getContext()).load(imageUri).resize(350 ,350).into(imageView);
                    imageView.setPadding(0, 0, 0, 0);
                    list.put(imageView, imageMap.get(imagem));
                }
                for(final ImageView image : list.keySet()){
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openDialog(image, list.get(image));
                        }
                    });
                }
                List<ImageView> keys = new ArrayList<>(list.keySet());
                ImageAdapter imageAdapter = new ImageAdapter(root.getContext(), keys);
                GridView  gridView = (GridView) root.findViewById(R.id.gridView);
                gridView.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabaseRef.addListenerForSingleValueEvent(eventListener);
        return root;
    }
    public void openDialog(ImageView v, String description){
        ImageDetails modal = new ImageDetails(v, description);
        modal.show(getActivity().getSupportFragmentManager(),"tag");
    }

}
