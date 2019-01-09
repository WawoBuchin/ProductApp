package com.example.eliabrian.productapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    AppCompatButton btnViewProducts, btnCreateProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnViewProducts = (AppCompatButton)findViewById(R.id.btnViewProducts);
        btnCreateProducts = (AppCompatButton)findViewById(R.id.btnCreateProducts);
        btnViewProducts.setOnClickListener(this);
        btnCreateProducts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnViewProducts:
                Intent i = new Intent(getApplicationContext(), AllProductActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btnCreateProducts:
                i = new Intent(getApplicationContext(), NewProductActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
