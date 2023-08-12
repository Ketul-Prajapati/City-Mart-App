package com.example.citymart;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class AddGroceryFragment extends Fragment {

    EditText etAdminAddGroceryName,etAdminAddGroceryPrice,etAdminAddGroceryStock,etAdminAddGroceryUnit;
    Button btnAdminAddGroceryAddImage,btnAdminAddGroceryAddItem,btnAdminAddGroceryCancel;
    ImageView ivAdminAddGrocery;
    String gId,name,unit;
    int stock,price;

    Uri imgPath;
    DatabaseReference dbRef;
    StorageReference mStore,pStore;

    public AddGroceryFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_grocery, container, false);

        ivAdminAddGrocery = view.findViewById(R.id.ivAdminAddGrocery);
        etAdminAddGroceryName = view.findViewById(R.id.etAdminAddGroceryName);
        etAdminAddGroceryPrice = view.findViewById(R.id.etAdminAddGroceryPrice);
        etAdminAddGroceryStock = view.findViewById(R.id.etAdminAddGroceryStock);
        etAdminAddGroceryUnit = view.findViewById(R.id.etAdminAddGroceryUnit);
        btnAdminAddGroceryAddImage = view.findViewById(R.id.btnAdminAddGroceryAddImage);
        btnAdminAddGroceryAddItem = view.findViewById(R.id.btnAdminAddGroceryAddItem);
        btnAdminAddGroceryCancel = view.findViewById(R.id.btnAdminAddGroceryCancel);

        btnAdminAddGroceryAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                // it is for to open the gallery on your device

                startActivityForResult(gallery,45);
                // 45 is the request code which can be anything
            }
        });

        btnAdminAddGroceryAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etAdminAddGroceryName.getText().toString();
                unit = etAdminAddGroceryUnit.getText().toString();
                stock = Integer.parseInt(etAdminAddGroceryStock.getText().toString());
                price = Integer.parseInt(etAdminAddGroceryPrice.getText().toString());

                dbRef = FirebaseDatabase.getInstance().getReference("grocery");
                mStore = FirebaseStorage.getInstance().getReferenceFromUrl("gs://city-mart-d79e1.appspot.com/images");

                gId = dbRef.push().getKey(); // create a root node in firebase database

                pStore = mStore.child(name); // will create image name in images folder in a firebase storage

                pStore.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pStore.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                GroceryModel gr = new GroceryModel(gId,uri.toString(),name,unit,price,stock);
                                dbRef.child(gId).setValue(gr);
                                Toast.makeText(view.getContext(),"Grocery Added Successfully!!",Toast.LENGTH_LONG).show();

                                etAdminAddGroceryName.setText("");
                                etAdminAddGroceryPrice.setText("");
                                etAdminAddGroceryStock.setText("");
                                etAdminAddGroceryUnit.setText("");
                                ivAdminAddGrocery.setImageDrawable(null);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(),"Image couldn't uploaded!!",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnAdminAddGroceryCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAdminAddGroceryName.setText("");
                etAdminAddGroceryPrice.setText("");
                etAdminAddGroceryStock.setText("");
                etAdminAddGroceryUnit.setText("");
                ivAdminAddGrocery.setImageDrawable(null);
            }
        });

        return view;
    }

    // this method is called when we select any image from gallery
    @Override
    public void onActivityResult(int reqCode,int resCode,Intent data)
    {
        if(reqCode==45 && resCode==RESULT_OK)
        {
            imgPath = data.getData();
            ivAdminAddGrocery.setImageURI(imgPath);
        }
    }
}