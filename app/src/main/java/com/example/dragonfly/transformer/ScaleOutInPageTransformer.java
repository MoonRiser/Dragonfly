package com.example.dragonfly.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

public class ScaleOutInPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {


//
        float offset = 24 * position;
        page.setTranslationX(offset);


        final float SCALE_MAX = 0.8f;
        final float ALPHA_MAX = 0.5f;
        final float ELEVATION_MAX = 0.2f;

        float scale = (position < 0)
                ? ((1 - SCALE_MAX) * position + 1)
                : ((SCALE_MAX - 1) * position + 1);
        float alpha = (position < 0)
                ? ((1 - ALPHA_MAX) * position + 1)
                : ((ALPHA_MAX - 1) * position + 1);
        float elevation = (position < 0)
                ? ((1 - ELEVATION_MAX) * position + 1)
                : (ELEVATION_MAX - 1) * position + 1;
        if (position < 0) {
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight() / 2f);
        } else {
            page.setPivotX(0);
            page.setPivotY(page.getHeight() / 2f);
        }
        page.setScaleX(scale);
        page.setScaleY(scale);
        // page.setAlpha(Math.abs(alpha));
        ViewCompat.setElevation(page, elevation * 32);
        page.setTranslationZ(elevation * 32);


    }
}
