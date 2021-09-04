package com.cs426.imageetranslation.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandleClickLogin();
        HandleClickSignup();
    }

    private void HandleClickSignup() {
        TextView signup = findViewById(R.id.txtClickSignUp);
        signup.setOnClickListener(this);
    }

    private void HandleClickLogin() {
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin: {
                String phone = ((EditText) findViewById(R.id.txtFieldLogin)).getText().toString();
                String password = ((EditText) findViewById(R.id.txtFieldPassword)).getText().toString();
                User user = new User(phone, password);
                new LoginTask(this).execute(user);
                break;
            }
            case R.id.txtClickSignUp:{
                startActivity(new Intent(this,SignUpActivity.class));
            }
        }
    }
}