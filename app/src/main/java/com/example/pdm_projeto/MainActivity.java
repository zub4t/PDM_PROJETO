package com.example.pdm_projeto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;

import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pdm_projeto.Utils.Constant;
import com.example.pdm_projeto.Utils.DataPart;
import com.example.pdm_projeto.Utils.VolleyMultipartRequest;
import com.example.pdm_projeto.ui.gallery.GalleryFragment;
import com.example.pdm_projeto.ui.gallery.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    ImageDetails modal;
    private AppBarConfiguration mAppBarConfiguration;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static String ord = "0";

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    public String getEmail(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String defaultValue = "";
        String login= sharedPreferences.getString("login", defaultValue);
        login = login.replace(".","");
        return login;
    }

    public String getEmailWithDot(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String defaultValue = "";
        String login= sharedPreferences.getString("login", defaultValue);
        return login;
    }

    public void saveOrd(String ord) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projeto-pdm-17aad.firebaseio.com/");

        DatabaseReference pushedDatabase = mDatabase.child("ord").child(getEmail());
        pushedDatabase.child("ord").setValue(ord);
    }

    public void openDialog(Bitmap v, String str,boolean show) {
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(v);
        modal = new ImageDetails(imageView, str, show);
        modal.show(this.getSupportFragmentManager(), "tag");

    }

    private void uploadFile(Bitmap bitmap, String file_name) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://projeto-pdm-17aad.appspot.com");
        StorageReference mountainImagesRef = storageRef.child("images/"+ getEmail() +"/" + file_name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        openDialog(bitmap, "Working on it",true);

        uploadBitmap(bitmap, file_name);
        byte[] data = baos.toByteArray();
        final String imageString = Base64.encodeToString(data, Base64.DEFAULT);

        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projeto-pdm-17aad.firebaseio.com/");

            final DatabaseReference pushedDatabase = mDatabase.child("images").child(getEmail()).push();

            final String imagem_id = pushedDatabase.getKey();
            DatabaseReference mDatabaseOrd = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projeto-pdm-17aad.firebaseio.com/");
            final DatabaseReference pushedDatabaseOrd = mDatabase.child("ord").child(getEmail());
            pushedDatabaseOrd.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("ord").getValue(String.class) != null){
                        ord = dataSnapshot.child("ord").getValue(String.class);
                    }
                    pushedDatabase.child("description").setValue("Working on it");
                    pushedDatabase.child("ord").setValue(String.valueOf(ord));
                    ord = String.valueOf(Long.parseLong(ord) + 1);
                    saveOrd(ord);
                    uploadFile(imageBitmap, imagem_id);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }*/
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //ini
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_signOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Needed to listen to the signOut navigation option
        navigationView.getMenu().findItem(R.id.nav_signOut).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_signOut) {
                    logout();
                }

                return true;
            }
        });

        updateNavHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap, final String id) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constant.SERVIDOR_URL + "?email=" + getEmailWithDot(),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projeto-pdm-17aad.firebaseio.com/");
                        DatabaseReference alterDatabase = mDatabase.child("images").child(getEmail()).child(id);
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            String food = obj.getString("food");
                            String food_description = obj.getString("food_description");
                            alterDatabase.child("description").setValue(food + "\n" + food_description);
                            modal.dismiss();

                            openDialog(bitmap, food+"\n"+food_description,false);


                        } catch (JSONException e) {
                            alterDatabase.child("description").setValue("Not a food");
                            modal.dismiss();
                            openDialog(bitmap, "Not a food",false);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_signOut) {
            logout();
        }

        return true;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("login","");
        editor.commit();

        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserEmail = headerView.findViewById(R.id.nav_userEmail);

        navUserEmail.setText(currentUser.getEmail());
    }
}


