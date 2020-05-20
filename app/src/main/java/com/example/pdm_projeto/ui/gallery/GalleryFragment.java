package com.example.pdm_projeto.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class GalleryFragment extends Fragment {
    private HashMap<String,ImageContext> imageMap = new HashMap<>();

    public String getEmail(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String defaultValue = "";
        String login= sharedPreferences.getString("login", defaultValue);
        login = login.replace(".","");
        return login;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);
       final HashMap<ImageView, ImageContext> list = new HashMap<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projeto-pdm-17aad.firebaseio.com/");
        DatabaseReference mDatabaseRef = mDatabase.child("images").child(getEmail());
        mDatabaseRef.orderByKey();
        getEmail();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    Object value = ds.getValue();
                    HashMap<String, String> conexao = (HashMap<String, String>) value;
                    ImageContext imageContext = new ImageContext(conexao.get("description"), String.valueOf(conexao.get("ord")));
                    imageMap.put(key, imageContext);
                }
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://projeto-pdm-17aad.appspot.com");
                final ImageView[] image_list = new ImageView[100];
                int max_pos = 0;
                //List<ImageView> image_list = new ArrayList<>();
                for(String imagem : imageMap.keySet()){
                    final ImageView imageView = new ImageView(root.getContext());

                    String imageUri = "https://firebasestorage.googleapis.com/v0/b/projeto-pdm-17aad.appspot.com/o/images%2F"+ getEmail() +"%2F" + imagem + ".jpg?alt=media&token=1727c60b-68ce-421e-a8e2-d7907d654a22";
                    Picasso.with(root.getContext()).load(imageUri).resize(350 ,350).into(imageView);
                    imageView.setPadding(0, 0, 0, 0);
                    ImageContext imageContext = imageMap.get(imagem);
                    list.put(imageView, imageMap.get(imagem));
                    if(Integer.parseInt(imageContext.ord) > max_pos)
                        max_pos = Integer.parseInt(imageContext.ord);
                    image_list[Integer.parseInt(imageContext.ord)] = imageView;
                    //image_list.add(Integer.parseInt(imageContext.ord), imageView);
                }
                final List<ImageView> image_list_list = new ArrayList<>();
                DatabaseReference mDatabaseOrd = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projeto-pdm-17aad.firebaseio.com/");
                final DatabaseReference pushedDatabaseOrd = mDatabaseOrd.child("ord").child(getEmail());
                pushedDatabaseOrd.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String ord = "0";
                        if(dataSnapshot.child("ord").getValue(String.class) != null){
                            ord = dataSnapshot.child("ord").getValue(String.class);
                        }
                        for(int i = 0; i < Long.parseLong(ord); i++){
                            final int finalI = i;
                            if(image_list[i] != null){
                                image_list_list.add(image_list[i]);
                                image_list[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        openDialog(image_list[finalI], list.get(image_list[finalI]).description);
                                    }
                                });
                            }
                        }
                        ImageAdapter imageAdapter = new ImageAdapter(root.getContext(), image_list_list);
                        GridView  gridView = (GridView) root.findViewById(R.id.gridView);
                        gridView.setAdapter(imageAdapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabaseRef.addListenerForSingleValueEvent(eventListener);
        return root;
    }
    public void openDialog(ImageView v, String description){
        ImageDetails modal = new ImageDetails(v, description,false);
        modal.show(getActivity().getSupportFragmentManager(),"tag");
    }

}
