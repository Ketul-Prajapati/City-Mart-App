package com.example.citymart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent ii = getIntent();
        int finalAmount = Integer.parseInt(ii.getStringExtra("finalAmount"));

        Checkout check = new Checkout();
        check.setKeyID("rzp_test_Os8JQauutPzTpv");

        JSONObject json = new JSONObject();

        try
        {
            json.put("name","KETUL PRAJAPATI");
            json.put("description","ENJOY GROCERY");
            json.put("theme.color","#0093DD");
            json.put("currency","INR");
            json.put("amount",(finalAmount*100));
            json.put("prefill.contact","8780751046");
            json.put("prefill.email","ketul.prajapati104@gmail.com");
            check.open(this,json);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"ERROR : "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(),"PAYMENT SUCCEED!!",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),"PAYMENT ERROR!!"+s,Toast.LENGTH_LONG).show();
        finish();
    }
}