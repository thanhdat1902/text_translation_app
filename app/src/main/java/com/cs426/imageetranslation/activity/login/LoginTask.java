package com.cs426.imageetranslation.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.activity.slider.SliderActivity;
import com.cs426.imageetranslation.helper.GlobalState;
import com.cs426.imageetranslation.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTask extends AsyncTask<User, Integer, Void> {
    private Context mContext;
    private String password;
    public LoginTask (Context context){
        mContext = context;
    }
    private ProgressDialog dialog;

    @Override
    protected Void doInBackground(User... params) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, mContext.getResources().getString(R.string.server_url)+"/users",null,
                response -> {
                    User user = params[0];
                    JSONObject tmp= null;
                    password = user.getPassword();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = response.getJSONObject(i);
                            String phone = jsonObject.getString("phone");
                            if(jsonObject.getString("phone").equals(user.getPhone()) ) {
                                tmp= jsonObject;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(tmp!=null) {
                        User current = new User(tmp);
                        if (password.equals(current.getPassword())) {
                            GlobalState.user = current;
                            Intent intent = new Intent(mContext, SliderActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "Wrong phone or password. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if( this.dialog.isShowing()) {
                        this.dialog.dismiss();
                    }
                }, error -> {
            error.printStackTrace();
        });

        queue.add(stringRequest);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setMax(values[0]);
        dialog.setProgress(values[1]);
    }
}
