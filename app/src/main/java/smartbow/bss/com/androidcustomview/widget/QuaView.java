package smartbow.bss.com.androidcustomview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者: lijiakui on 16/7/5 08:23.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
public class QuaView extends View {

    private Paint paint;
    private Path path;

    int startX;
    int startY;

    float endX;
    float endY;

    float midleX;
    float midleY;

    public QuaView(Context context) {
        this(context,null);
    }

    public QuaView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QuaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public QuaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startX = 5;
        startY = getHeight()/2;

        path.reset();
        path.moveTo(startX,startY);
        path.quadTo(midleX,midleY,endX,endY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_MOVE:
                endX =  event.getX();
                endY =  event.getY();

                midleX=  Math.abs(startX+(endX - startX)/2);//新的锚点 x
                midleY =  Math.abs(startY+(endY - startY)/2);//新的锚点 y
                break;
        }
        return true;
    }
}
