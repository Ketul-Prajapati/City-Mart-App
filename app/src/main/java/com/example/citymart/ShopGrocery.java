package com.example.citymart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopGrocery extends AppCompatActivity {

    Toolbar tbMain;
    ListView lvShopGrocery;
    TextView tvShopGroceryAmount;
    Button btnShopGroceryViewCart;
    DatabaseReference dbRef;
    ArrayList<GroceryModel> gList = new ArrayList<>();
    ShopGroceryAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_grocery);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tbMain = findViewById(R.id.tbMain);
        lvShopGrocery = findViewById(R.id.lvShopGrocery);
        tvShopGroceryAmount = findViewById(R.id.tvShopGroceryAmount);
        btnShopGroceryViewCart = findViewById(R.id.btnShopGroceryViewCart);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbRef = FirebaseDatabase.getInstance().getReference("grocery");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gList.clear();
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    GroceryModel g = snap.getValue(GroceryModel.class);
                    gList.add(g);
                }
                adapter = new ShopGroceryAdapter(getApplicationContext(),R.layout.shop_grocery_card,gList,tvShopGroceryAmount,btnShopGroceryViewCart);
                lvShopGrocery.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}