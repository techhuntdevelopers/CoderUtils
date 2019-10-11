package techhunt.developers.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {
    private Rect mRect;
    private Paint mPaint;

    @Override
    public int getLineCount() {
        return getHeight() / getLineHeight();
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFF000000);
    }

    /**
     * This is called to draw the LinedEditText object
     *
     * @param canvas The canvas on which the background is drawn.
     */

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight() * 10;
        int curHeight = 0;
        Rect r = mRect;
        Paint paint = mPaint;
        int baseline = getLineBounds(0, r);
        for (curHeight = baseline + 1; curHeight < height; curHeight += getLineHeight()) {
            canvas.drawLine(r.left, curHeight, r.right, curHeight, paint);
        }
        super.onDraw(canvas);
    }
}