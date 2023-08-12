package com.example.citymart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class AdminMain extends AppCompatActivity {

    Toolbar tbMain;
    FrameLayout adminMainFrameLayout;
    BottomNavigationView adminMainBottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tbMain = findViewById(R.id.tbMain);
        adminMainFrameLayout = findViewById(R.id.adminMainFrameLayout);
        adminMainBottomNavigationView = findViewById(R.id.adminMainBottomNavigationView);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        adminMainBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id==R.id.adminMainAddGrocerySelector)
                {
                    loadFrag(new AddGroceryFragment());
                }
                else if(id==R.id.adminMainListGrocerySelector)
                {
                    loadFrag(new ListGroceryFragment());
                }
                else
                {
                    loadFrag(new ListGroceryFragment());
                }
                return true;
            }
        });
        adminMainBottomNavigationView.setSelectedItemId(R.id.adminMainListGrocerySelector);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left_global, R.anim.slide_out_right_global);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFrag(Fragment fr)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.adminMainFrameLayout,fr);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AdminMain.this);
        dialog.setTitle("Log Out");
        dialog.setMessage("Do you want to logout the section ?");
        dialog.setIcon(R.drawable.baseline_logout_24);
        dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdminMain.super.onBackPressed();
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}