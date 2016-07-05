package smartbow.bss.com.androidcustomview.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * 作者: lijiakui on 16/7/4 08:49.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
public class MyAnimaView extends View {

    public static final float RADIUS = 20f;

    private Point currentPoint;
    private Paint mPaint;

    private String color;

    public MyAnimaView(Context context) {
       this(context,null);
    }

    public MyAnimaView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyAnimaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public MyAnimaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);

    }

    public void setColor(String color){
        if (TextUtils.isEmpty(color) || !color.startsWith("#")){
            return;
        }

        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawLine(RADIUS,RADIUS,getWidth()-RADIUS,getHeight()-RADIUS,mPaint);
        /*
        if (currentPoint == null){
            currentPoint = new Point(RADIUS,RADIUS);
            drawCircle(canvas);
        }else{
            drawCircle(canvas);
        }
        */
        drawCircle(canvas);


        super.onDraw(canvas);
    }

    private void drawCircle(Canvas canvas){
        currentPoint = new Point(RADIUS,RADIUS);
        float x = currentPoint.getX();
        float y = currentPoint.getY();

        canvas.drawCircle(x,y,RADIUS,mPaint);
    }

    public void startAnimation(){
        ValueAnimator anim1 = ValueAnimator.ofObject(new PointEvaluator(),RADIUS,getWidth()-RADIUS);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint.setX((Float) animation.getAnimatedValue());
                invalidate();
               // postInvalidate();
            }
        });

        ValueAnimator anim2 = ValueAnimator.ofObject(new PointEvaluator(),RADIUS,getHeight()-RADIUS);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint.setY((Float) animation.getAnimatedValue());
                invalidate();
                // postInvalidate();
            }
        });

        anim1.setInterpolator(new DecelerateInterpolator());//
        anim2.setInterpolator(new BounceInterpolator());

        ObjectAnimator animator = ObjectAnimator.ofObject(this,"color",new ColorEvaluator(),"#000000","#FF00FF");
        AnimatorSet set = new AnimatorSet();
        set.play(anim1).with(anim2).with(animator);

        set.setDuration(10000);
        set.start();
    }
}
