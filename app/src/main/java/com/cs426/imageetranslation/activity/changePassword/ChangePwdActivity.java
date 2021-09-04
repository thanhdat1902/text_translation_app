package com.cs426.imageetranslation.activity.changePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.activity.image.GetImageTabsActivity;
import com.cs426.imageetranslation.activity.login.LoginTask;
import com.cs426.imageetranslation.activity.translation.TranslationTabsActivity;
import com.cs426.imageetranslation.helper.GlobalState;
import com.cs426.imageetranslation.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePwdActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        Button btnPwd = findViewById(R.id.btnConfirmChange);
        btnPwd.setOnClickListener(this);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnConfirmChange:{
                checkAndChangePassword();
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
            case R.id.btnCancel:{
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

    private void checkAndChangePassword() {
        String currentPassword = ((EditText)findViewById(R.id.txtFieldCurrentPassword)).getText().toString();
        Log.d("User",GlobalState.user.getPassword());
        Log.d("Cur",currentPassword);

        if(!GlobalState.validatePwd(currentPassword)){
            Toast.makeText(this, "Current password can not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            if (currentPassword.equals(GlobalState.user.getPassword())) {
                String newPassword = ((EditText) findViewById(R.id.txtFieldNewPassword)).getText().toString();
                String confirmPassword = ((EditText) findViewById(R.id.txtFieldConfirmPassword)).getText().toString();
                if (!GlobalState.validatePwd(newPassword) || !GlobalState.validatePwd(confirmPassword)) {
                    Toast.makeText(this, "Password can not be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirmPassword.equals(newPassword)) {
                    User user = GlobalState.user;
                    user.setPassword(newPassword);
                    new ChangePwdTask(this).execute(user);
                } else {
                    Toast.makeText(this, "New password and confirm password are different!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Wrong current password!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}