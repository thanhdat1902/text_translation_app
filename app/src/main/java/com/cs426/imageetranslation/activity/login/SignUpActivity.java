package com.cs426.imageetranslation.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.helper.GlobalState;
import com.cs426.imageetranslation.model.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name, dob, phone, email, gender, password, confirmPwd;
    Button signup,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText) findViewById(R.id.signup_txtFieldName);
        dob = (EditText) findViewById(R.id.signup_txtFieldDob);
        password = (EditText) findViewById(R.id.signup_txtFieldPassword);
        email = (EditText) findViewById(R.id.signup_txtFieldEmail);
        phone = (EditText) findViewById(R.id.signup_txtFieldPhone);
        confirmPwd = (EditText) findViewById(R.id.signup_txtFieldConfirmPassword);
        gender = (EditText) findViewById(R.id.signup_txtFieldGender);
        signup = (Button) findViewById(R.id.signup_btnSignup);
        signup.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.signup_btnCancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup_btnSignup:{
                String txtName = name.getText().toString();
                String txtDob = dob.getText().toString();
                String txtPwd = password.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPhone = phone.getText().toString();
                String txtConfirmPwd = confirmPwd.getText().toString();
                String txtGender = gender.getText().toString();
                if(!GlobalState.validatePwd(txtName) || !GlobalState.validatePwd(txtDob)
                || !GlobalState.validatePwd(txtPwd) || !GlobalState.validatePwd(txtConfirmPwd)
                || !GlobalState.validatePwd(txtEmail) || !GlobalState.validatePwd(txtGender)){
                    Toast.makeText(this, "Please fill enough information!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!txtGender.toLowerCase().equals("male") && !txtGender.toLowerCase().equals("female")){
                    Toast.makeText(this, "Gender must be male or female!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtPwd.equals(txtConfirmPwd)) {
                    User user = new User(txtName, txtPhone, txtPwd, txtDob, txtGender, txtEmail);
                    new SignUpTask(this).execute(user);
                }
                else{
                    Toast.makeText(this, "Incorrect confirm password!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.signup_btnCancel:{
                startActivity(new Intent(this, LoginActivity.class));
                break;
            }
        }
    }
}
