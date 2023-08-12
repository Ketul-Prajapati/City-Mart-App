package com.example.citymart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListGroceryFragment extends Fragment {

    ListView lvAdminListGrocery;
    ArrayList<GroceryModel> list = new ArrayList();
    DatabaseReference dbRef;
    public ListGroceryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_grocery, container, false);

        lvAdminListGrocery = view.findViewById(R.id.lvAdminListGrocery);

        dbRef = FirebaseDatabase.getInstance().getReference("grocery");

        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for(DataSnapshot snap : snapshot.getChildren())
                {
                    GroceryModel gr = snap.getValue(GroceryModel.class);
                    list.add(gr);
                }

//                ArrayAdapter<GroceryModel> ar = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_activated_1,list);
                AdminListGroceryAdapter asga = new AdminListGroceryAdapter(view.getContext(),R.layout.admin_show_grocery_design,list);
                lvAdminListGrocery.setAdapter(asga);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(view.getContext(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        lvAdminListGrocery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent ii = new Intent(view.getContext(), AdminUpdateDelete.class);

                ii.putExtra("list",list);
                ii.putExtra("position",""+position);
                startActivity(ii);
                getActivity().overridePendingTransition(R.anim.slide_in_right_global, R.anim.slide_out_left_global);
            }
        });

        return view;
    }
}