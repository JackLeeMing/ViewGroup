package smartbow.bss.com.androidcustomview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者: lijiakui on 16/6/30 09:01.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
public class TextV extends TextView {

    private Paint rectPaint;
    private Rect rect;
    public TextV(Context context) {
       this(context,null);
    }

    public TextV(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextV(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @TargetApi(21)
    public TextV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeCap(Paint.Cap.ROUND);
        rectPaint.setStrokeJoin(Paint.Join.ROUND);
        rectPaint.setStrokeWidth(2);
        rectPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setTextAlign(Paint.Align.CENTER);
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rect.left  = getPaddingLeft();
        rect.top = getPaddingTop();
        rect.right = getWidth() - getPaddingRight();
        rect.bottom = getHeight() - getPaddingBottom();

        canvas.drawRect(rect,rectPaint);
    }
}
