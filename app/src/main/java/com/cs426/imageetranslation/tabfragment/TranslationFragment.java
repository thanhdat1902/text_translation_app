package com.cs426.imageetranslation.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs426.imageetranslation.activity.image.GetImageTabsActivity;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.helper.GlobalState;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import org.w3c.dom.Text;

public class TranslationFragment extends Fragment implements View.OnClickListener {
    Button btnTakeNewImage,btnTranslateText;
    TextView translateV;
    private String fullText;


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
        btnTranslateText = (Button) getView().findViewById(R.id.btnTranslate);
        translateV = (TextView) getView().findViewById(R.id.textTranslated);

        fullText = GlobalState.fullText;
        btnTakeNewImage.setOnClickListener(this);
        btnTranslateText.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTakeNewPhoto: {
                startActivity(new Intent(getActivity(), GetImageTabsActivity.class));
                break;
            }
            case R.id.btnTranslate: {
                ProgressBar pBar = (ProgressBar) getView().findViewById(R.id.progressbar);
                pBar.setVisibility(ProgressBar.VISIBLE);
                translateTextFromImage();
                break;
            }
        }
    }

    private void translateTextFromImage() {
        // Create an English-German translator:
        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.VI)
                        .build();
        final FirebaseTranslator englishVietnamTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);


        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        englishVietnamTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
                                englishVietnamTranslator.translate(fullText)
                                        .addOnSuccessListener(
                                                new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(@NonNull String translatedText) {
                                                        // Translation successful.
                                                        translateV.setText(translatedText);
                                                        ProgressBar pBar = (ProgressBar) getView().findViewById(R.id.progressbar);
                                                        pBar.setVisibility(ProgressBar.GONE);
                                                    }
                                                })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Error.
                                                        // ...
                                                    }
                                                });
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                Log.d("Message: ", "Cannot download model translation");
                            }
                        });
    }
}
