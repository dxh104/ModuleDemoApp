package com.administer.example.common_base.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by XHD on 2019/03/18
 */
public class DensityUtils {
    /**
     * dp转px
     */
    public static int convertDpToPixel(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    /**
     * px转dp
     */
    public static float convertPixelToDp(Context context, float px) {
        return (px / context.getResources().getDisplayMetrics().density);
    }

    /**
     * sp转px
     */
    public static int convertSpToPixel(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    /**
     * px转sp
     */
    public static float convertPixelToSp(Context context, float px) {
        return (px / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
