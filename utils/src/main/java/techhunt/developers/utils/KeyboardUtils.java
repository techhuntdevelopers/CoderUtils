package techhunt.developers.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public final class KeyboardUtils {

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showSoftInput(@NonNull Activity activity) {
        if (!isSoftInputVisible(activity)) {
            toggleSoftInput();
        }
    }

    public static void showSoftInput(@NonNull final View view) {
        showSoftInput(view, 0);
    }

    public static void showSoftInput(@NonNull final View view, final int flags) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, flags, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN
                        || resultCode == InputMethodManager.RESULT_HIDDEN) {
                    toggleSoftInput();
                }
            }
        });
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideSoftInput(@NonNull final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            View decorView = activity.getWindow().getDecorView();
            View focusView = decorView.findViewWithTag("keyboardTagView");
            if (focusView == null) {
                view = new EditText(activity);
                view.setTag("keyboardTagView");
                ((ViewGroup) decorView).addView(view, 0, 0);
            } else {
                view = focusView;
            }
            view.requestFocus();
        }
        hideSoftInput(view);
    }

    public static void hideSoftInput(@NonNull final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(0, 0);
    }

    private static int sDecorViewDelta = 0;

    public static boolean isSoftInputVisible(@NonNull final Activity activity) {
        return getDecorViewInvisibleHeight(activity.getWindow()) > 0;
    }

    private static int getDecorViewInvisibleHeight(@NonNull final Window window) {
        final View decorView = window.getDecorView();
        if (decorView == null) return 0;
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        Log.d("KeyboardUtils", "getDecorViewInvisibleHeight: "
                + (decorView.getBottom() - outRect.bottom));
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= getNavBarHeight() + getStatusBarHeight()) {
            sDecorViewDelta = delta;
            return 0;
        }
        return delta - sDecorViewDelta;
    }

    public static void registerSoftInputChangedListener(@NonNull final Activity activity,
                                                        @NonNull final OnSoftInputChangedListener listener) {
        registerSoftInputChangedListener(activity.getWindow(), listener);
    }

    public static void registerSoftInputChangedListener(@NonNull final Window window,
                                                        @NonNull final OnSoftInputChangedListener listener) {
        final int flags = window.getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final FrameLayout contentView = window.findViewById(android.R.id.content);
        final int[] decorViewInvisibleHeightPre = {getDecorViewInvisibleHeight(window)};
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = getDecorViewInvisibleHeight(window);
                        if (decorViewInvisibleHeightPre[0] != height) {
                            listener.onSoftInputChanged(height);
                            decorViewInvisibleHeightPre[0] = height;
                        }
                    }
                });
    }

    public static void fixAndroidBug5497(@NonNull final Activity activity) {
        fixAndroidBug5497(activity.getWindow());
    }

    public static void fixAndroidBug5497(@NonNull final Window window) {
        final FrameLayout contentView = window.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final int paddingBottom = contentViewChild.getPaddingBottom();
        final int[] contentViewInvisibleHeightPre5497 = {getContentViewInvisibleHeight(window)};
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = getContentViewInvisibleHeight(window);
                        if (contentViewInvisibleHeightPre5497[0] != height) {
                            contentViewChild.setPadding(
                                    contentViewChild.getPaddingLeft(),
                                    contentViewChild.getPaddingTop(),
                                    contentViewChild.getPaddingRight(),
                                    paddingBottom + getDecorViewInvisibleHeight(window)
                            );
                            contentViewInvisibleHeightPre5497[0] = height;
                        }
                    }
                });
    }

    private static int getContentViewInvisibleHeight(final Window window) {
        final View contentView = window.findViewById(android.R.id.content);
        if (contentView == null) return 0;
        final Rect outRect = new Rect();
        contentView.getWindowVisibleDisplayFrame(outRect);
        Log.d("KeyboardUtils", "getContentViewInvisibleHeight: "
                + (contentView.getBottom() - outRect.bottom));
        int delta = Math.abs(contentView.getBottom() - outRect.bottom);
        if (delta <= getStatusBarHeight() + getNavBarHeight()) {
            return 0;
        }
        return delta;
    }

    public static void fixSoftInputLeaks(@NonNull final Activity activity) {
        fixSoftInputLeaks(activity.getWindow());
    }

    public static void fixSoftInputLeaks(@NonNull final Window window) {
        Utils.fixSoftInputLeaks(window);
    }

    private static int getStatusBarHeight() {
        Resources resources = Utils.getApp().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    private static int getNavBarHeight() {
        Resources res = Utils.getApp().getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    private static Activity getActivityByView(@NonNull View view) {
        return getActivityByContext(view.getContext());
    }

    private static Activity getActivityByContext(Context context) {
        if (context instanceof Activity) return (Activity) context;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int height);
    }
}