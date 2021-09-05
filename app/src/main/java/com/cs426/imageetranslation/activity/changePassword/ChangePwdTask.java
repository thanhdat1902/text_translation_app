package com.cs426.imageetranslation.activity.changePassword;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.activity.image.GetImageTabsActivity;
import com.cs426.imageetranslation.activity.login.LoginActivity;
import com.cs426.imageetranslation.activity.slider.SliderActivity;
import com.cs426.imageetranslation.activity.translation.TranslationTabsActivity;
import com.cs426.imageetranslation.helper.GlobalState;
import com.cs426.imageetranslation.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePwdTask extends AsyncTask<User, Integer, Void> {
    private Context mContext;
    private String password;
    public ChangePwdTask (Context context){
        mContext = context;
    }
    private ProgressDialog dialog;

    @Override
    protected Void doInBackground(User... params) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JSONObject jsonObject = params[0].UserToJSON();
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, mContext.getResources().getString(R.string.server_url)+"/users/" + params[0].getId(),jsonObject,
                response -> {
                    Toast.makeText(mContext, "Change password successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    if(GlobalState.tabScreens == 0) {
                        intent = new Intent(mContext, GetImageTabsActivity.class);
                        intent.putExtra("profile",1);
                        mContext.startActivity(intent);
                    }
                    else if(GlobalState.tabScreens == 1) {
                        intent = new Intent(mContext, TranslationTabsActivity.class);
                        intent.putExtra("profile",1);
                        mContext.startActivity(intent);
                    }

                }, error -> {
            error.printStackTrace();
        });

        queue.add(stringRequest);
        return null;
    }
}
