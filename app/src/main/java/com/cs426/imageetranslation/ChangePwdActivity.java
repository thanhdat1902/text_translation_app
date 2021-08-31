package com.cs426.imageetranslation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePwdActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        Button btnPwd = findViewById(R.id.btnConfirmChange);
        btnPwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConfirmChange:{
                startActivity(new Intent(this, GetImageTabsActivity.class));
                break;
            }

        }
    }
}