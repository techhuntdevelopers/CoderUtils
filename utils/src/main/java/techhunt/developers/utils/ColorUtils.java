package techhunt.developers.utils;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public final class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(Utils.getApp(), id);
    }

    public static int setAlphaComponent(@ColorInt final int color,
                                        @IntRange(from = 0x0, to = 0xFF) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    public static int setAlphaComponent(@ColorInt int color,
                                        @FloatRange(from = 0, to = 1) float alpha) {
        return (color & 0x00ffffff) | ((int) (alpha * 255.0f + 0.5f) << 24);
    }

    public static int setRedComponent(@ColorInt int color,
                                      @IntRange(from = 0x0, to = 0xFF) int red) {
        return (color & 0xff00ffff) | (red << 16);
    }

    public static int setRedComponent(@ColorInt int color,
                                      @FloatRange(from = 0, to = 1) float red) {
        return (color & 0xff00ffff) | ((int) (red * 255.0f + 0.5f) << 16);
    }

    public static int setGreenComponent(@ColorInt int color,
                                        @IntRange(from = 0x0, to = 0xFF) int green) {
        return (color & 0xffff00ff) | (green << 8);
    }

    public static int setGreenComponent(@ColorInt int color,
                                        @FloatRange(from = 0, to = 1) float green) {
        return (color & 0xffff00ff) | ((int) (green * 255.0f + 0.5f) << 8);
    }

    public static int setBlueComponent(@ColorInt int color,
                                       @IntRange(from = 0x0, to = 0xFF) int blue) {
        return (color & 0xffffff00) | blue;
    }

    public static int setBlueComponent(@ColorInt int color,
                                       @FloatRange(from = 0, to = 1) float blue) {
        return (color & 0xffffff00) | (int) (blue * 255.0f + 0.5f);
    }


    public static int string2Int(@NonNull String colorString) {
        return Color.parseColor(colorString);
    }


    public static String int2RgbString(@ColorInt int colorInt) {
        colorInt = colorInt & 0x00ffffff;
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        return "#" + color;
    }

    public static String int2ArgbString(@ColorInt final int colorInt) {
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        while (color.length() < 8) {
            color = "f" + color;
        }
        return "#" + color;
    }

    public static int getRandomColor() {
        return getRandomColor(true);
    }

    public static int getRandomColor(final boolean supportAlpha) {
        int high = supportAlpha ? (int) (Math.random() * 0x100) << 24 : 0xFF000000;
        return high | (int) (Math.random() * 0x1000000);
    }
}