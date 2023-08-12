package com.example.citymart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GroceryBillGenerateAdapter extends ArrayAdapter<String>
{
    Context cont;
    int design;
    ArrayList<String> gBuyName;
    ArrayList<String> gBuyPrice;
    ArrayList<String> gBuyQty;
    ArrayList<String> gBuyTotal;

    GroceryBillGenerateAdapter(Context cont,int design,ArrayList<String> gBuyName,ArrayList<String> gBuyPrice,ArrayList<String> gBuyQty,ArrayList<String> gBuyTotal)
    {
        super(cont,design,gBuyName);
        this.cont = cont;
        this.design = design;
        this.gBuyName = gBuyName;
        this.gBuyPrice = gBuyPrice;
        this.gBuyQty = gBuyQty;
        this.gBuyTotal = gBuyTotal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(cont);
        View view = inflater.inflate(design,null,false);

        TextView tvGroceryProductName = view.findViewById(R.id.tvGroceryProductName);
        TextView tvGroceryProductQty = view.findViewById(R.id.tvGroceryProductQty);
        TextView tvGroceryProductRate = view.findViewById(R.id.tvGroceryProductRate);
        TextView tvGroceryProductAmount = view.findViewById(R.id.tvGroceryProductAmount);

        tvGroceryProductName.setText(gBuyName.get(position));
        tvGroceryProductQty.setText(gBuyQty.get(position));
        tvGroceryProductRate.setText(gBuyPrice.get(position));
        tvGroceryProductAmount.setText(gBuyTotal.get(position));

        return view;
    }
}
