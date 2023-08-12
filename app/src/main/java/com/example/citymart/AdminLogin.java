package com.example.citymart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminLogin extends AppCompatActivity {

    final public String MAIL="ketul@gmail.com",PASSWORD="kp@1510";
    int flag=0;
    EditText etAdminLoginMail,etAdminLoginPassword;
    TextView forgotPwd;
    ImageView showHidePwd;
    Toolbar tbMain;
    Button btnAdminLoginLogin,btnAdminLoginReset;

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tbMain = findViewById(R.id.tbMain);
        showHidePwd = findViewById(R.id.showHidePwd);
        forgotPwd = findViewById(R.id.forgotPwd);
        etAdminLoginMail = findViewById(R.id.etAdminLoginMail);
        etAdminLoginPassword = findViewById(R.id.etAdminLoginPassword);
        btnAdminLoginLogin = findViewById(R.id.btnAdminLoginLogin);
        btnAdminLoginReset = findViewById(R.id.btnAdminLoginReset);

        setSupportActionBar(tbMain);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Password Forgotted!!",Toast.LENGTH_LONG).show();
            }
        });

        showHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0)
                {
                    etAdminLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag=1;
                }
                else
                {
                    etAdminLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag=0;
                }
            }
        });

        btnAdminLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = etAdminLoginMail.getText().toString();
                String pass = etAdminLoginPassword.getText().toString();

                if(mail.equals("") || pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fill the required fields!!",Toast.LENGTH_LONG).show();
                }
                else if(mail.equals(MAIL) && pass.equals(PASSWORD))
                {
                    Intent ii = new Intent(getApplicationContext(), AdminMain.class);
                    etAdminLoginMail.setText("");
                    etAdminLoginPassword.setText("");
                    startActivity(ii);
                    overridePendingTransition(R.anim.slide_in_right_global,R.anim.slide_out_left_global);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Username/Password is wrong!!",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAdminLoginReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAdminLoginMail.setText("");
                etAdminLoginPassword.setText("");
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        etAdminLoginMail.setText("");
        etAdminLoginPassword.setText("");
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