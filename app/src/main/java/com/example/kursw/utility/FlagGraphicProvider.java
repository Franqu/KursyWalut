package com.example.kursw.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class FlagGraphicProvider {

    public static Drawable drawableWithFlag(Context context, String flagName) {
        if(flagName.equals("XDR"))
            flagName = "USD"; // jako że nie istnieje flaga dla 'special drawing rights' zakładam tam flagę Stanów Zjednoczonych.
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(flagName.toLowerCase(), "drawable", context.getPackageName());
        return res.getDrawable(resourceId);
    }
}