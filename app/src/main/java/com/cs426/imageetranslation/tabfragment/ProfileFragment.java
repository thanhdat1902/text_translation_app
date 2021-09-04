package com.cs426.imageetranslation.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs426.imageetranslation.activity.changePassword.ChangePwdActivity;
import com.cs426.imageetranslation.activity.login.LoginActivity;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.helper.GlobalState;

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

        TextView name = (TextView)getView().findViewById(R.id.txtName);
        EditText phone = (EditText)getView().findViewById(R.id.txtFieldPhone);
        EditText gender = (EditText)getView().findViewById(R.id.txtFieldGender);
        EditText dob = (EditText)getView().findViewById(R.id.txtFieldDateOfBirth);
        EditText email = (EditText)getView().findViewById(R.id.txtFieldEmail);

        name.setText(GlobalState.name);
        phone.setText(GlobalState.phone);
        gender.setText(GlobalState.gender);
        email.setText(GlobalState.email);
        dob.setText(GlobalState.dob);
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
