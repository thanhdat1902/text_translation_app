package com.cs426.imageetranslation.activity.changePassword;

import android.content.Context;
import android.os.AsyncTask;

import com.cs426.imageetranslation.model.User;

public class ChangePwdTask extends AsyncTask<User, Integer, Void> {
    private Context mContext;
    private String password;
    public ChangePwdTask (Context context){
            mContext = context;
    }

    @Override
    protected Void doInBackground(User... users) {
        return null;
    }
}
