package com.cs426.imageetranslation.activity.changePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.cs426.imageetranslation.activity.login.User;
import com.cs426.imageetranslation.activity.slider.SliderActivity;
import com.cs426.imageetranslation.activity.translation.TranslationTabsActivity;
import com.cs426.imageetranslation.helper.GlobalState;

import org.json.JSONException;
import org.json.JSONObject;

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

        }
    }

    private void checkAndChangePassword() {
        String currentPassword = findViewById(R.id.txtFieldCurrentPassword).toString();

        if (currentPassword.equals(GlobalState.password)) {
            String newPassword = findViewById(R.id.txtFieldNewPassword).toString();
            String confirmPassword = findViewById(R.id.txtFieldConfirmPassword).toString();

            if (confirmPassword.equals(newPassword)) {
                RequestQueue queue = Volley.newRequestQueue(this);
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, this.getResources().getString(R.string.server_url) + "/users", null,
                        response -> {
                            try {
                                //check the response on API
                                if (response.length() != 0) {
                                    JSONObject result = response.getJSONObject(0);
                                    //compare to get the current user on API
                                    if (result.getString("phone").equals(GlobalState.phone)) {
                                        //create request to change password on API
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("password", newPassword);

                                        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, this.getResources().getString(R.string.server_url) + "/users", jsonObject, res -> {
                                            Log.d("POST", "Success");
                                        }, error -> {
                                            error.printStackTrace();
                                        });
                                        queue.add(request2);
                                    } else {
                                        Toast.makeText(this, "Phone number not found!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "Empty response!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
                    error.printStackTrace();
                });
                queue.add(request);
                Toast.makeText(this, "Change password success!", Toast.LENGTH_SHORT);
            }
            else {
                Toast.makeText(this, "New password and confirm password are different!", Toast.LENGTH_SHORT);
            }
        }else {
            Toast.makeText(this, "Wrong current password!", Toast.LENGTH_SHORT);
        }
    }
}