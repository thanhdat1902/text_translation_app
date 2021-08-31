package com.cs426.imageetranslation.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs426.imageetranslation.ChangePwdActivity;
import com.cs426.imageetranslation.LoginActivity;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.TranslationTabsActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    public ProfileFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.activity_profile_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnChangePwd = (Button) getView().findViewById(R.id.btnChangePassword);
        btnChangePwd.setOnClickListener(this);
        Button btnLogOut = (Button) getView().findViewById(R.id.btnLogout);
        btnLogOut.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnChangePassword:{
                startActivity(new Intent(getActivity(), ChangePwdActivity.class));
                break;
            }
            case R.id.btnLogout: {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            }
        }

    }
}
