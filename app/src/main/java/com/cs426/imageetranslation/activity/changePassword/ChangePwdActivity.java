package com.cs426.imageetranslation.activity.changePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.activity.image.GetImageTabsActivity;
import com.cs426.imageetranslation.activity.translation.TranslationTabsActivity;
import com.cs426.imageetranslation.helper.GlobalState;

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
                Intent intent;
                if(GlobalState.tabScreens == 0) {
                    intent = new Intent(this, GetImageTabsActivity.class);
                    intent.putExtra("profile",1);
                    startActivity(intent);
                }
                else if(GlobalState.tabScreens == 1) {
                    intent = new Intent(this, TranslationTabsActivity.class);
                    intent.putExtra("profile",1);
                    startActivity(intent);
                }
                break;
            }

        }
    }
}