package com.example.citymart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ShopGroceryAdapter extends ArrayAdapter<GroceryModel>
{
    Context cont;
    int design;
    ArrayList<GroceryModel> gList;
    TextView tvShopGroceryAmount;
    Button btnShopGroceryViewCart;
    ArrayList<String> gBuyName = new ArrayList<String>();
    ArrayList<String> gBuyPrice = new ArrayList<String>();
    ArrayList<String> gBuyQty = new ArrayList<String>();
    ArrayList<String> gBuyTotal = new ArrayList<String>();
    int gPrice[],gStock[],gTotal[];
    int amount=0,totalAmount=0;

    public ShopGroceryAdapter(Context cont,int design,ArrayList<GroceryModel> gList,TextView tvShopGroceryAmount,Button btnShopGroceryViewCart)
    {
        super(cont,design,gList);
        this.cont = cont;
        this.design = design;
        this.gList = gList;
        this.tvShopGroceryAmount = tvShopGroceryAmount;
        this.btnShopGroceryViewCart = btnShopGroceryViewCart;

        gPrice = new int[gList.size()];
        gStock = new int[gList.size()];
        gTotal = new int[gList.size()];

        for(int i=0;i<gList.size();i++)
        {
            gPrice[i] = gList.get(i).getPrice();
            gTotal[i] = 0;
            gBuyName.add("");
            gBuyPrice.add("");
            gBuyQty.add("");
            gBuyTotal.add("");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(cont);
        View view = inflater.inflate(design,null,false);

        GroceryModel gr = gList.get(position);

        ImageView iv = view.findViewById(R.id.ivShopGroceryCardImage);
        TextView tvName = view.findViewById(R.id.tvShopGroceryCardName);
        TextView tvPrice = view.findViewById(R.id.tvShopGroceryCardPrice);

        TextView tvCount = view.findViewById(R.id.tvShopGroceryCardCount);
        Button btPlus = view.findViewById(R.id.btnShopGroceryCardPlus);
        Button btMinus = view.findViewById(R.id.btnShopGroceryCardMinus);

        tvName.setText(gr.getName());
        tvPrice.setText(""+(gr.getPrice()+" Rs."));
        tvCount.setText("0");

        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                totalAmount = 0;
                gStock[position]++;
                tvCount.setText(""+gStock[position]);
                amount = gPrice[position] * gStock[position];
                gTotal[position] = amount;

                for(int i=0;i<gTotal.length;i++)
                {
                    totalAmount = totalAmount + gTotal[i];
                }

                tvShopGroceryAmount.setText("Rs. "+totalAmount);

                gBuyName.set(position,gList.get(position).getName());
                gBuyPrice.set(position,""+gPrice[position]);
                gBuyQty.set(position,""+gStock[position]);
                gBuyTotal.set(position,""+gStock[position] * gPrice[position]);

            }
        });

        btMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                totalAmount = 0;
                if(gStock[position]>0) {
                    gStock[position]--;
                }
                tvCount.setText(""+gStock[position]);
                amount = gPrice[position] * gStock[position];
                gTotal[position] = amount;

                for(int i=0;i<gTotal.length;i++)
                {
                    totalAmount = totalAmount + gTotal[i];
                }

                tvShopGroceryAmount.setText("Rs. "+totalAmount);

                gBuyName.set(position,gList.get(position).getName());
                gBuyPrice.set(position,""+gPrice[position]);
                gBuyQty.set(position,""+gStock[position]);
                gBuyTotal.set(position,""+gStock[position] * gPrice[position]);

            }
        });

        btnShopGroceryViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ii = new Intent(cont, GroceryBillGenerate.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ii.putExtra("totalAmount",""+totalAmount);
                ii.putStringArrayListExtra("gBuyName",gBuyName);
                ii.putStringArrayListExtra("gBuyPrice",gBuyPrice);
                ii.putStringArrayListExtra("gBuyQty",gBuyQty);
                ii.putStringArrayListExtra("gBuyTotal",gBuyTotal);
                cont.startActivity(ii);
            }
        });

        Glide.with(cont)
                .load(gr.getImgUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        return view;
    }
}
