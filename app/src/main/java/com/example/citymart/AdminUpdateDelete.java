package com.example.citymart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdminUpdateDelete extends AppCompatActivity {

    Toolbar tbMain;
    ImageView ivAdminUpdateDelete;
    Uri imgPath;
    String gId,gName,imgUrl;
    EditText etAdminUpdateDeleteName,etAdminUpdateDeletePrice,etAdminUpdateDeleteStock,etAdminUpdateDeleteUnit;
    Button btnAdminUpdateDeleteChangeImage,btnAdminUpdateDeleteUpdateItem,btnAdminUpdateDeleteDelete;
    StorageReference gStore;
    DatabaseReference dbRef;

    ArrayList<GroceryModel> gList = new ArrayList<GroceryModel>();
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_delete);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tbMain = findViewById(R.id.tbMain);
        ivAdminUpdateDelete = findViewById(R.id.ivAdminUpdateDelete);
        etAdminUpdateDeleteName = findViewById(R.id.etAdminUpdateDeleteName);
        etAdminUpdateDeletePrice = findViewById(R.id.etAdminUpdateDeletePrice);
        etAdminUpdateDeleteStock = findViewById(R.id.etAdminUpdateDeleteStock);
        etAdminUpdateDeleteUnit = findViewById(R.id.etAdminUpdateDeleteUnit);
        btnAdminUpdateDeleteChangeImage = findViewById(R.id.btnAdminUpdateDeleteChangeImage);
        btnAdminUpdateDeleteUpdateItem = findViewById(R.id.btnAdminUpdateDeleteUpdateItem);
        btnAdminUpdateDeleteDelete = findViewById(R.id.btnAdminUpdateDeleteDelete);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        gStore = FirebaseStorage.getInstance().getReferenceFromUrl("gs://city-mart-d79e1.appspot.com/images");
        dbRef = FirebaseDatabase.getInstance().getReference("grocery");

        Intent ii = getIntent();
        gList = (ArrayList<GroceryModel>) ii.getSerializableExtra("list"); // used to get ArrayList passed using intent
        position = Integer.parseInt(ii.getStringExtra("position"));

        etAdminUpdateDeleteName.setText(gList.get(position).getName());
        etAdminUpdateDeletePrice.setText(""+gList.get(position).getPrice());
        etAdminUpdateDeleteStock.setText(""+ gList.get(position).getStock());
        etAdminUpdateDeleteUnit.setText(gList.get(position).getUnit());

        imgUrl = gList.get(position).getImgUrl();
        Glide.with(getApplicationContext())
                .load(imgUrl)
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivAdminUpdateDelete);

        gId = gList.get(position).getgId();
        gName = gList.get(position).getName();
        gStore = gStore.child(gName);

        btnAdminUpdateDeleteUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etAdminUpdateDeleteName.getText().toString();
                String unit = etAdminUpdateDeleteUnit.getText().toString();
                int stock = Integer.parseInt(etAdminUpdateDeleteStock.getText().toString());
                int price = Integer.parseInt(etAdminUpdateDeletePrice.getText().toString());


                GroceryModel gr = new GroceryModel(gId,imgUrl,name,unit,price,stock);
                dbRef.child(gId).setValue(gr);

                Toast.makeText(getApplicationContext(),"Grocery Updated Successfully!!",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

        btnAdminUpdateDeleteDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminUpdateDelete.this);
                dialog.setTitle("Confirm");
                dialog.setMessage("Do you want to delete this item ?");
                dialog.setIcon(R.drawable.baseline_question_mark_24);
                dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                 });
                dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Query delQ = dbRef.orderByChild("gId").equalTo(gId);
                        delQ.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                            snap.getRef().removeValue(); // deletes record in real time database
//                            gStore = gStore.child(gName);

                            gStore.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Grocery Deleted Successfully!!",Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                }
                            });
                            break;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                        });
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        btnAdminUpdateDeleteChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,45);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            if(requestCode==45)
            {
                imgPath = data.getData();
                ivAdminUpdateDelete.setImageURI(imgPath);
                imgUrl = imgPath.toString();
            }
        }
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