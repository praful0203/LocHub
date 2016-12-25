package com.praful.lochub;

import android.graphics.Typeface;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by prafu on 16-10-2016.
 */

public class mytypeface extends TypefaceSpan {
    Typeface typeface;
    public mytypeface(String family,Typeface typeface) {
        super(family);
        this.typeface=typeface;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setTypeface(typeface);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        paint.setTypeface(typeface);
    }
}