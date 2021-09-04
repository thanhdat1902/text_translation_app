package com.cs426.imageetranslation.helper;

import com.cs426.imageetranslation.R;
import com.cs426.imageetranslation.model.User;

public class GlobalState {
    public static String fullText;
    public static User user;


    public static String[] countryCode = new String[]{"af","ar","be","bg","bn","ca","cs","cy","da","de","el","en","eo","es","et",
            "fa","fi","fr","ga","gl","gu","he","hi","hr","ht","hu","id","is","it","ja","ka","kn",
            "ko","lt","lv","mk","mr","ms","mt","nl","no","pl","pt","ro","ru","sk","sl","sq","sv","sw",
            "ta","te","th","tl","tr","uk","ur","vi","zh"};

    public static String[] countryName = new String[]{"Afrikaans","Arabic","Belarusian","Bulgarian","Bengali",
            "Catalan","Czech","Welsh","Danish","German","Greek","English","Esperanto","Spanish","Estonian",
            "Persian","Finnish","French","Irish","Galician","Gujarati","Hebrew","Hindi","Croatian","Haitian",
            "Hungarian","Indonesian","Icelandic","Italian","Japanese","Georgian","Kannada", "Korean",
            "Lithuanian","Latvian","Macedonian","Marathi","Malay","Maltese","Dutch","Norwegian",
            "Polish","Portuguese","Romanian","Russian","Slovak","Slovenian","Albanian","Swedish","Swahili",
            "Tamil","Telugu","Thai","Tagalog","Turkish","Ukrainian","Urdu","Vietnamese","Chinese"};

    public static String[] invalidFrom = new String[]{"Arabic","Belarusian","Bulgarian","Bengali","Greek","Persian",
            "Irish","Gujarati","Hebrew","Hindi","Indonesian","Icelandic","Japanese","Georgian","Kannada","Korean",
            "Macedonian","Marathi","Malay","Russian","Tamil","Telugu","Thai","Ukrainian","Urdu","Vietnamese","Chinese"
    };

    public static int[] myImageList = new int[]{R.drawable.af,R.drawable.ar,R.drawable.be,R.drawable.bg,R.drawable.bn,R.drawable.ca,R.drawable.cs,
            R.drawable.cy,R.drawable.da,R.drawable.de,R.drawable.el,R.drawable.en,R.drawable.eo,R.drawable.es,R.drawable.et,
            R.drawable.fa,R.drawable.fi,R.drawable.fr,R.drawable.ga,R.drawable.gl,R.drawable.gu,R.drawable.he,R.drawable.hi,
            R.drawable.hr,R.drawable.ht,R.drawable.hu,R.drawable.id,R.drawable.is,R.drawable.it,R.drawable.ja,R.drawable.ka,R.drawable.kn,
            R.drawable.ko,R.drawable.lt,R.drawable.lv,R.drawable.mk,R.drawable.mr,R.drawable.ms,R.drawable.mt,R.drawable.nl,R.drawable.no,
            R.drawable.pl,R.drawable.pt,R.drawable.ro,R.drawable.ru,R.drawable.sk,R.drawable.sl,R.drawable.sq,R.drawable.sv,R.drawable.sw,
            R.drawable.ta,R.drawable.te,R.drawable.th,R.drawable.tl,R.drawable.tr,R.drawable.uk,R.drawable.ur,R.drawable.vi,R.drawable.zh};

    public static int selectedFrom = 11;

    public static int selectedTo = 57;

    public static int tabScreens = 0;

    public static boolean validatePwd(String pwd){
        if(pwd.equals("")){
            return false;
        }
        return true;
    }
    public static int toBCP14(String code) {
        for(int i =0; i<countryCode.length; i++) {
            if(code.equals(countryCode[i])) {
                return i;
            }
        }
        return -1;
    }
}
