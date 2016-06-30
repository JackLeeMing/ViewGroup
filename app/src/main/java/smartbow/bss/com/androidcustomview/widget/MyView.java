package smartbow.bss.com.androidcustomview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import smartbow.bss.com.androidcustomview.R;
import smartbow.bss.com.androidcustomview.utils.TLog;

/**
 * 作者: lijiakui on 16/6/29 13:45.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
public class MyView extends View {

    private String text;
    private TextPaint paint;
    private Paint rectPaint;
    private float textSize;

    private int textColor;
    private Paint.Align align  = Paint.Align.LEFT;

    Rect rect;
    Rect rect2;
    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,context,defStyleAttr,0);
    }

    @TargetApi(21)
    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs,context,defStyleAttr,defStyleRes);
    }

    private void init(AttributeSet attrs,Context context,int defStyleAttr,int defStyleRes){
        if (attrs != null){
            int id = attrs.getAttributeResourceValue(null,"Text",0);
            text = context.getResources().getText(id).toString();

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView,defStyleAttr,defStyleRes);

            if (typedArray.hasValue(R.styleable.MyView_textShow)){
                text = typedArray.getString(R.styleable.MyView_textShow);
            }

            float size;
            if (typedArray.hasValue(R.styleable.MyView_textSize)){
                size = typedArray.getDimensionPixelSize(R.styleable.MyView_textSize,16);
            }else{
                size = 16;
            }

            textSize = size;// px2sp(context,size);

            if (typedArray.hasValue(R.styleable.MyView_textColor)){
                textColor = typedArray.getColor(R.styleable.MyView_textColor,Color.RED);
            }else{
                textColor = Color.WHITE;
            }

            if (typedArray.hasValue(R.styleable.MyView_gravity)) {

                int gravityType = typedArray.getInt(R.styleable.MyView_gravity, 0);
                switch (gravityType) {
                    case 0:
                        align = Paint.Align.LEFT;
                        break;
                    case 1:
                        align = Paint.Align.CENTER;
                        break;
                    case 2:
                        align = Paint.Align.RIGHT;
                        break;
                    default:
                        align = Paint.Align.LEFT;
                        break;
                }
            }

            typedArray.recycle();
        }

        if (TextUtils.isEmpty(text)){
            text = "测试数据";
        }

        paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(align);

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
        rect2 = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        TLog.error(text);

    }


int count = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        count ++;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int lineWidth;
        int lineHeight;
        int height=0;
        int widthUsed = getPaddingLeft()+getPaddingRight();
        int heightUsed = getPaddingBottom()+getPaddingTop();


        int noticeLength = text.length();
        int limit =  (int)((widthSize-getPaddingLeft()-getPaddingRight())/ textSize);
        if (noticeLength <= limit) {
            paint.getTextBounds(text,0,text.length(),rect);
            lineHeight = rect.height()+5;
            height = lineHeight+5;
            lineWidth = rect.width();
            width = lineWidth;

        } else {
            int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
            TLog.error(count+" size: "+size);
            for (int i = 0; i < size; i++) {
                int startIndex = i * limit;
                int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
                String s = text.substring(startIndex,endIndex);
                paint.getTextBounds(s,0,s.length(),rect);
                lineHeight = rect.height()+5;
                lineWidth = rect.width()+5;
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }
        }

        height+= heightUsed;
        width+= widthUsed;

        setMeasuredDimension(
                widthMode == MeasureSpec.EXACTLY ? widthSize:width,
                heightMode == MeasureSpec.EXACTLY?heightSize:height);

    }

    int x,y;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {


        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawText(text,);
        if (paint.getTextAlign() == Paint.Align.CENTER) {
            x = getWidth() / 2 + getPaddingLeft();
        }

        if (paint.getTextAlign() == Paint.Align.LEFT){
            x = getPaddingLeft()+5;
        }

        if (paint.getTextAlign() == Paint.Align.RIGHT){
            x = getWidth()-getPaddingRight()-5;
        }

        y =  rect.height()+getPaddingTop() - Math.abs(rect.bottom);

           // canvas.drawText();


        canvas.drawLine(0,y,getWidth(),y,paint);
        int noticeLength = text.length();
        int dpW = getWidth()-getPaddingRight()-getPaddingLeft();
        int limit =  (int)(dpW/textSize);

        TLog.error("limit: "+ limit+" width: "+getWidth());

        if (noticeLength < limit) {
            canvas.drawText(text,x,y, paint);//短了就绘制
        } else {
            int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
            for (int i = 0; i < size; i++) {
                int startIndex = i * limit;
                int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
                String s = text.substring(startIndex,endIndex);
                canvas.drawText(s,x,y+rect.height()*i,paint);
            }
        }

        canvas.save();

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        canvas.drawLine(x,0,x,getHeight(),paint);

        rect2.left = getPaddingLeft();
        rect2.top = getPaddingTop();
        rect2.bottom = getHeight()-getPaddingBottom();
        rect2.right = getWidth()-getPaddingRight();

        canvas.drawRect(rect2,rectPaint);

        canvas.restore();
    }

}
