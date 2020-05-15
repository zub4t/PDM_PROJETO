package com.example.pdm_projeto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {
    TextView singup;
    Button singin;
    TextView nick;
    TextView pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        singup = findViewById(R.id.singup);
        singin  = findViewById(R.id.submit);
        nick = findViewById(R.id.nickname);
        pass = findViewById(R.id.password);

        Log.e("teste", String.valueOf(pass.getText().equals("admin")));
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("teste",nick.getText().toString());
                Log.d("teste", String.valueOf((nick.getText().toString()).equals("admin")));
                if((nick.getText().toString()).equals("admin") && (pass.getText().toString()).equals("admin")){
                    Intent intent;
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }
            }
        });


        singup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);

            }
        });
    }

}
