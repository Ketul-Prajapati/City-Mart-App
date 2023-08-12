package com.example.citymart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    LinearLayout lvImg;
    Animation animation_in, animation_out;
    Button btnMainLogIn, btnMainShopGrocery;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lvImg = findViewById(R.id.lvImg);
        btnMainLogIn = findViewById(R.id.btnMainLogIn);
        btnMainShopGrocery = findViewById(R.id.btnMainShopGrocery);

        animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_main_page);
        lvImg.setAnimation(animation_in);

        btnMainLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(ii);
                overridePendingTransition(R.anim.slide_in_right_global, R.anim.slide_out_left_global);
            }
        });

        btnMainShopGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation_out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_main_page);
                lvImg.setAnimation(animation_out);
                lvImg.startAnimation(animation_out);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent ii = new Intent(getApplicationContext(), ShopGrocery.class);
                        startActivity(ii);
                        animation_out.setFillAfter(false);
                        overridePendingTransition(R.anim.slide_in_right_global, R.anim.slide_out_left_global);
                    }
                },3000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Exit");
        dialog.setMessage("Do you want to exit the app ?");
        dialog.setIcon(R.drawable.baseline_exit_to_app_24);
        dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.super.onBackPressed();
                finish();
            }
        });
        dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}