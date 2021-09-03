package com.cs426.imageetranslation.activity.chooseLanguage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.activity.image.GetImageTabsActivity;
import com.cs426.imageetranslation.activity.login.LoginActivity;
import com.cs426.imageetranslation.activity.translation.TranslationTabsActivity;
import com.cs426.imageetranslation.helper.GlobalState;

public class ChooseLanguageActivity extends AppCompatActivity implements View.OnClickListener  {
    ImageButton btnClose;
    LinearLayout listBtn;
    EditText search;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_choose_language);
        Intent intent = getIntent();
        type = intent.getIntExtra("type",-1);
        listBtn = (LinearLayout) findViewById(R.id.listLanguage);
        btnClose = (ImageButton) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);
        search = (EditText) findViewById(R.id.searchLanguages);
        createListLanguage();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                for(int index = 0; index < GlobalState.countryCode.length;index++){
                    Button tmp = findViewById(index);
                    if(!tmp.getText().toString().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        tmp.setVisibility(View.GONE);
                    }
                    else{
                        tmp.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        String t = "BE";
//        for(int i = 0; i < GlobalState.countryCode.length;i++){
//            Button tmp = findViewById(i);
//            if(!tmp.getText().toString().contains(t.toLowerCase())){
//                tmp.setVisibility(View.GONE);
//            }
//        }
    }

    private void createListLanguage() {
        for(int i = 0; i < GlobalState.countryCode.length;i++){
            Button btn = createButton(i,GlobalState.countryName[i]);
            btn.setOnClickListener(this);
            addBtnToView(btn);
        }
    }

    private void addBtnToView(Button btn) {
        listBtn.addView(btn);
    }

    private Button createButton(int id, String s) {
        Button res;
        res = new Button(this);
        res.setId(id);
        res.setText(s);
        res.setBackgroundResource(R.color.white);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 16, 16, 0);
        res.setLayoutParams(params);
        setDrawable(res,id);
        return  res;
    }

    private void setDrawable(Button res, int id) {
        Drawable img = this.getResources().getDrawable(GlobalState.myImageList[id]);
        img.setBounds(0, 0, 80, 80);
        res.setPadding(20,0,50,0);
        res.setCompoundDrawables(img, null, null, null);
    }

    @Override
    public void onClick(View v) {
        if(type == 0) {
            GlobalState.selectedFrom = v.getId();
            startActivity(new Intent(this, TranslationTabsActivity.class));
        }
        else if(type == 1){
            GlobalState.selectedTo= v.getId();
            startActivity(new Intent(this, TranslationTabsActivity.class));
        }
        else if(v.getId() == R.id.btnClose){
            startActivity(new Intent(this, TranslationTabsActivity.class));
        }
    }

}
