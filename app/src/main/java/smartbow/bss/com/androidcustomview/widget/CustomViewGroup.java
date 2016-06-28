package smartbow.bss.com.androidcustomview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者: lijiakui on 16/6/28 09:55.
 * 联系:JackLeeMing@outlook.com  Test
 */
public class CustomViewGroup extends ViewGroup {


    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*
        * 获得此ViewGroup的上级容器为其推荐的宽度和高度，以及计算模式
        * */
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //计算所有的childView的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        /**
         * 记录如果是wrap_content 是设置的宽和高
         * */
        int width;
        int height;

        int count = getChildCount();
        int childWidth ;
        int childHeight;
        MarginLayoutParams lp;

        //用于计算左边两个childView的高度
        int lHeight= getPaddingBottom() + getPaddingTop();
        //用于计算右边两个childView的高度
        int rHeight= getPaddingBottom() + getPaddingTop();
        //用于计算上边两个childView的宽度
        int tWidth = getPaddingLeft()+getPaddingRight();
        //用于计算下面两个childView的宽度，最终的宽度去二者之间的最大者
        int bWidth = getPaddingLeft()+getPaddingRight();
        for (int i=0; i<count; i++){
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            lp = (MarginLayoutParams) child.getLayoutParams();
            if (i == 0 || i==1){
                tWidth += childWidth +lp.leftMargin +lp.rightMargin;
            }

            if (i == 2 || i == 3){
                bWidth += childWidth + lp.leftMargin + lp.rightMargin;
            }

            if (i ==0 || i == 2){
                lHeight += childHeight + lp.topMargin+lp.bottomMargin;
            }

            if (i == 1 || i == 3){
                rHeight += childHeight + lp.topMargin+ lp.bottomMargin;
            }
        }

        width = Math.max(tWidth,bWidth);
        height = Math.max(lHeight,rHeight);

        /**
         * 如果是wrap_content 则取我们计算的值
         * 如果是尺寸明确的，直接去父容器推荐值
         * */
        setMeasuredDimension(
                (widthMode == MeasureSpec.EXACTLY)? widthSize:width,
                (heightMode == MeasureSpec.EXACTLY)? heightSize:height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int childWidth ;
        int childHeight;
        MarginLayoutParams lp;

        for (int i=0; i< count; i++){
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            lp = (MarginLayoutParams)child.getLayoutParams();
            int childLeft = 0;
            int childRight = 0;
            int childTop = 0;
            int childBottom = 0;
            switch (i){
                case 0:
                    childLeft = getPaddingLeft() + lp.leftMargin;
                    childTop = getPaddingTop() +lp.topMargin;
                    break;
                case 1:
                    childLeft = getWidth() - childWidth - lp.rightMargin  - getPaddingRight();
                    childTop = lp.topMargin+getPaddingTop();
                    break;
                case 2:
                    childLeft = lp.leftMargin + getPaddingLeft();
                    childTop = getHeight() - childHeight - lp.bottomMargin - getPaddingBottom();
                    break;
                case 3:
                    childLeft = getWidth() - childWidth  - lp.rightMargin - getPaddingRight();
                    childTop = getHeight() - childHeight - lp.bottomMargin - getPaddingBottom();
                    break;
            }

            childRight = childLeft+childWidth;
            childBottom = childTop + childHeight;

            child.layout(childLeft,childTop,childRight,childBottom);
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof MarginLayoutParams;
    }
}
