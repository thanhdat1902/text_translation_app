package com.cs426.imageetranslation.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs426.imageetranslation.activity.chooseLanguage.ChooseLanguageActivity;
import com.cs426.imageetranslation.activity.image.GetImageTabsActivity;
import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.activity.login.LoginActivity;
import com.cs426.imageetranslation.helper.GlobalState;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import org.w3c.dom.Text;

import java.util.Locale;

public class TranslationFragment extends Fragment implements View.OnClickListener {
    Button btnTakeNewImage,btnTranslateText,btnTranslateFrom,btnTranslateTo;
    ImageButton btnExchange,btnTextToSpeech;
    TextView translateV;
    TextToSpeech tts;

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
        btnTranslateFrom = (Button) getView().findViewById(R.id.btnTranslateFrom);
        btnTranslateTo = (Button) getView().findViewById(R.id.btnTranslateTo);
        btnExchange = (ImageButton) getView().findViewById(R.id.btnExchange);
        btnTextToSpeech = (ImageButton) getView().findViewById(R.id.btnTextToSpeech);
        translateV = (TextView) getView().findViewById(R.id.textTranslated);

        btnTranslateFrom.setText(GlobalState.countryName[GlobalState.selectedFrom]);
        btnTranslateTo.setText(GlobalState.countryName[GlobalState.selectedTo]);
        btnTextToSpeech.setVisibility(View.INVISIBLE);

        fullText = GlobalState.fullText;
        btnTakeNewImage.setOnClickListener(this);
        btnTranslateText.setOnClickListener(this);
        btnTranslateFrom.setOnClickListener(this);
        btnTranslateTo.setOnClickListener(this);
        btnExchange.setOnClickListener(this);
        btnTextToSpeech.setOnClickListener(this);


        tts=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(new Locale(GlobalState.countryCode[GlobalState.selectedTo]));
                    if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        ConvertTextToSpeech();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }
    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        String text = translateV.getText().toString();
        if(text==null||"".equals(text))
        {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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
            case R.id.btnTranslateFrom:{
                Intent language = new Intent(getActivity(), ChooseLanguageActivity.class);
                language.putExtra("type",0);
                startActivity(language);
                break;
            }
            case R.id.btnTranslateTo:{
                Intent language = new Intent(getActivity(), ChooseLanguageActivity.class);
                language.putExtra("type",1);
                startActivity(language);
                break;
            }
            case R.id.btnExchange:{
                int tmp = GlobalState.selectedFrom;
                GlobalState.selectedFrom = GlobalState.selectedTo;
                GlobalState.selectedTo = tmp;
                btnTranslateFrom.setText(GlobalState.countryName[GlobalState.selectedFrom]);
                btnTranslateTo.setText(GlobalState.countryName[GlobalState.selectedTo]);
                break;
            }
            case R.id.btnTextToSpeech: {

                break;
            }
        }
    }

    private void translateTextFromImage() {
        // Create an English-German translator:
        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(GlobalState.selectedFrom)
                        .setTargetLanguage(GlobalState.selectedTo)
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
                                                        btnTextToSpeech.setVisibility(View.VISIBLE);
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
