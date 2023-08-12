package com.example.citymart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GroceryBillGenerate extends AppCompatActivity {

    ImageView imageView2;
    TextView tvGroceryBillDate;
    TextView tvGroceryBillTime;
    ListView lvGroceryPurchased;
    TextView tvGroceryGrandTotal;
    Button btGroceryPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_bill_generate);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvGroceryBillTime = findViewById(R.id.tvGroceryBillTime);
        tvGroceryBillDate = findViewById(R.id.tvGroceryBillDate);
        lvGroceryPurchased = findViewById(R.id.lvGroceryPurchsed);
        tvGroceryGrandTotal = findViewById(R.id.tvGroceryGrandTotal);
        btGroceryPayment = findViewById(R.id.btGroceryPayment);

        Intent ii = getIntent();
        int totalAmount = Integer.parseInt(ii.getStringExtra("totalAmount"));
        ArrayList<String> gBuyName = ii.getStringArrayListExtra("gBuyName");
        ArrayList<String> gBuyPrice = ii.getStringArrayListExtra("gBuyPrice");
        ArrayList<String> gBuyQty = ii.getStringArrayListExtra("gBuyQty");
        ArrayList<String> gBuyTotal = ii.getStringArrayListExtra("gBuyTotal");

        tvGroceryGrandTotal.setText("Rs. "+totalAmount);

        Date dd = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String myDate = sdf.format(dd);
        tvGroceryBillDate.setText(myDate);

        sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String myTime = sdf.format(dd);
        tvGroceryBillTime.setText(myTime);

        GroceryBillGenerateAdapter ga = new GroceryBillGenerateAdapter(getApplicationContext(),R.layout.bill_design,gBuyName,gBuyPrice,gBuyQty,gBuyTotal);
        lvGroceryPurchased.setAdapter(ga);


        btGroceryPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getApplicationContext(), PaymentActivity.class);
                ii.putExtra("finalAmount",""+totalAmount);
                startActivity(ii);
                overridePendingTransition(R.anim.slide_in_right_global, R.anim.slide_out_left_global);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left_global, R.anim.slide_out_right_global);
    }
}