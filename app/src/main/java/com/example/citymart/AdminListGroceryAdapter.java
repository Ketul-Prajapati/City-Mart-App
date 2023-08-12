package com.example.citymart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdminListGroceryAdapter extends ArrayAdapter<GroceryModel>
{
    Context cont;
    List<GroceryModel> gList;
    int layout; // anything in res folder are return int so we take layout as int here

    AdminListGroceryAdapter(Context cont, int layout, List<GroceryModel> gList)
    {
        super(cont,layout,gList);
        this.cont = cont;
        this.gList = gList;
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(cont); //  used to access xml file in non-activity class = LayoutInflater
        View view = inflater.inflate(layout,null,false);

        ImageView iv = view.findViewById(R.id.listViewImg);
        TextView tvName = view.findViewById(R.id.lvName);
        TextView tvPrice = view.findViewById(R.id.lvPrice);
        TextView tvStock = view.findViewById(R.id.lvStock);

        GroceryModel gr = gList.get(position);

        tvName.setText(gr.getName());
        tvPrice.setText(""+gr.getPrice()+" Rs.");
        tvStock.setText(""+gr.getStock()+" unit");

        Glide.with(cont)
                .load(gr.getImgUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        return view;
    }

}

