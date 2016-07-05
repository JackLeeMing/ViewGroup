package smartbow.bss.com.androidcustomview.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import smartbow.bss.com.androidcustomview.R;

/**
 * Created by chenupt@gmail.com on 11/20/14.
 * Description : custom layout to draw bezier
 */
public class BezierView extends FrameLayout {

    // 默认定点圆半径
    public static final float DEFAULT_RADIUS = 50;

    private Paint paint;
    private Path path;

    // 手势坐标
    float x = 300;
    float y = 300;

    // 锚点坐标
    float anchorX = 200;
    float anchorY = 300;

    // 起点坐标
    float startX = 100;
    float startY = 100;

    // 定点圆半径
    float radius = DEFAULT_RADIUS;

    // 判断动画是否开始
    boolean isAnimStart;
    // 判断是否开始拖动
    boolean isTouch;

    ImageView exploredImageView;
    ImageView tipImageView;

    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        exploredImageView = new ImageView(getContext());
        exploredImageView.setLayoutParams(params);
        exploredImageView.setImageResource(R.drawable.tip_anim);
        exploredImageView.setVisibility(View.INVISIBLE);

        tipImageView = new ImageView(getContext());
        tipImageView.setLayoutParams(params);
        tipImageView.setImageResource(R.drawable.skin_tips_newmessage_ninetynine);

        addView(tipImageView);
        addView(exploredImageView);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        exploredImageView.setX(startX - exploredImageView.getWidth()/2);
        exploredImageView.setY(startY - exploredImageView.getHeight()/2);

        tipImageView.setX(startX - tipImageView.getWidth()/2);
        tipImageView.setY(startY - tipImageView.getHeight()/2);

        super.onLayout(changed, left, top, right, bottom);
    }



    private void calculate(){
        float distance = (float) Math.sqrt(Math.pow(y-startY, 2) + Math.pow(x-startX, 2));
        radius = DEFAULT_RADIUS - distance/30;//

        if(radius < 8){
            isAnimStart = true;

            exploredImageView.setVisibility(View.VISIBLE);
            exploredImageView.setImageResource(R.drawable.tip_anim);
            ((AnimationDrawable) exploredImageView.getDrawable()).stop();
            ((AnimationDrawable) exploredImageView.getDrawable()).start();

            tipImageView.setVisibility(View.GONE);
        }

        if (radius >25){
            radius = 25;
        }

        // 根据角度算出四边形的四个点
        float offsetX = (float) (radius*Math.sin(Math.atan((y - startY) / (x - startX))));
        float offsetY = (float) (radius*Math.cos(Math.atan((y - startY) / (x - startX))));

        float x1 = startX - offsetX;
        float y1 = startY + offsetY;

        float x2 = x - offsetX;
        float y2 = y + offsetY;

        float x3 = x + offsetX;
        float y3 = y - offsetY;

        float x4 = startX + offsetX;
        float y4 = startY - offsetY;

        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x3, y3);
        path.quadTo(anchorX, anchorY, x4, y4);
        path.lineTo(x1, y1);

        // 更改图标的位置
        tipImageView.setX(x - tipImageView.getWidth()/2);
        tipImageView.setY(y - tipImageView.getHeight()/2);
    }


    public void reset(){
        radius = DEFAULT_RADIUS;
        isAnimStart = false;
        exploredImageView.setVisibility(View.INVISIBLE);
        tipImageView.setImageResource(R.drawable.skin_tips_newmessage_ninetynine);
        tipImageView.setVisibility(View.VISIBLE);
        isTouch = false;
        invalidate();

    }
    @Override
    protected void onDraw(Canvas canvas){
        if(isAnimStart || !isTouch){
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.ADD);
        }else{
            calculate();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.ADD);
            canvas.drawPath(path, paint);
            canvas.drawCircle(startX, startY, radius, paint);
            canvas.drawCircle(x, y, radius, paint);
        }
        super.onDraw(canvas);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            // 判断触摸点是否在tipImageView中
            Rect rect = new Rect();
            int[] location = new int[2];
            tipImageView.getDrawingRect(rect);//占地面积
            tipImageView.getLocationOnScreen(location);//位置
            rect.left = location[0];
            rect.top = location[1];
            rect.right = (int) (rect.right*1.5 + location[0]);
            rect.bottom = (int) (rect.bottom*1.5 + location[1]);

            if (rect.contains((int)event.getRawX(), (int)event.getRawY())){
                isTouch = true;
            }

        }else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
            isTouch = false;
            ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<PointF>() {
                @Override
                public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                    return new PointF(startValue.x+fraction*(endValue.x-startValue.x),startValue.y+fraction*(endValue.y-startValue.y));
                }
            },new PointF(x,y),new PointF(startX ,startY));
            valueAnimator.setDuration(2000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    PointF pointF = (PointF)animation.getAnimatedValue();
                    tipImageView.setX(pointF.x);
                    tipImageView.setY(pointF.y);//放回原来的位置
                    x = pointF.x;
                    y = pointF.y;
                    anchorX =  Math.abs(startX+(pointF.x - startX)/2);//新的锚点 x
                    anchorY =  Math.abs(startY+(pointF.y - startY)/2);//新的锚点 y
                    isAnimStart = false;
                    isTouch = true;
                    invalidate();

                }
            });

            valueAnimator.setInterpolator(new BounceInterpolator());
            valueAnimator.start();
        }

        invalidate();
        if(isAnimStart){
            return super.onTouchEvent(event);
        }
        anchorX =  Math.abs(startX+(event.getX() - startX)/2);//新的锚点 x
        anchorY =  Math.abs(startY+(event.getY() - startY)/2);//新的锚点 y
        x =  event.getX();
        y =  event.getY();
        return true;
    }


}
