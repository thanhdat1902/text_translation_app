package com.cs426.imageetranslation.tabfragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs426.imageetranslation.GetImageTabsActivity;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.TranslationTabsActivity;

public class TranslationFragment extends Fragment implements View.OnClickListener {
    Button btnTakeNewImage;
    public TranslationFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.activity_translation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTakeNewImage = (Button) getView().findViewById(R.id.btnTakeNewPhoto);
        btnTakeNewImage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTakeNewPhoto: {
                startActivity(new Intent(getActivity(), GetImageTabsActivity.class));
                break;
            }
        }
    }
}
