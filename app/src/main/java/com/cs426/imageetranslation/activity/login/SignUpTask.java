package com.cs426.imageetranslation.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.model.User;

import org.json.JSONObject;

public class SignUpTask extends AsyncTask<User, Integer, Void> {
    private Context mContext;
    public SignUpTask (Context context){
        mContext = context;
    }
    private ProgressDialog dialog;

    @Override
    protected Void doInBackground(User... params) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JSONObject jsonObject = params[0].UserToJSON();
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, mContext.getResources().getString(R.string.server_url)+"/users" ,jsonObject,
                response -> {
                    Toast.makeText(mContext, "Create account successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }, error -> {
            error.printStackTrace();
        });

        queue.add(stringRequest);
        return null;
    }
}