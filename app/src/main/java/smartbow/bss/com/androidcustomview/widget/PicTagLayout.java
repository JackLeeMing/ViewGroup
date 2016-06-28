package smartbow.bss.com.androidcustomview.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 作者: lijiakui on 16/6/28 11:35.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
@SuppressLint("NewApi")
public class PicTagLayout extends RelativeLayout implements View.OnTouchListener {
        private static final int  CLICKABLE_RANGE = 5;
      int startX =0;
    int startY = 0;
    int startTouchViewLeft=0;
    int startTouchViewTop = 0;
    private View touchView,clickView;


    public PicTagLayout(Context context) {
        this(context,null);
    }

    public PicTagLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PicTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(21)
    public PicTagLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView(){
        this.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchView = null;
                if (clickView != null){
                    ((PicTagView)clickView).setStatus(PicTagView.TagStatus.Normal);
                    clickView = null;
                }

                startX = (int)motionEvent.getX();
                startY = (int)motionEvent.getY();

                if (hasView(startX,startY)){
                    startTouchViewLeft = touchView.getLeft();
                    startTouchViewTop = touchView.getTop();
                }else{

                    addItem(startX,startY);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                moveView((int)motionEvent.getX(),(int)motionEvent.getY());
                break;

            case MotionEvent.ACTION_UP:
                int endX = (int)motionEvent.getX();
                int endY = (int)motionEvent.getY();
                if (touchView != null && Math.abs(endX - startX) < CLICKABLE_RANGE && Math.abs(endY - startY) <CLICKABLE_RANGE){
                        ((PicTagView)touchView).setStatus(PicTagView.TagStatus.Edit);
                        clickView = touchView;
                }
                touchView = null;
                break;
        }
        return true;
    }

    private void addItem(int x,int y){
        View view;
        RelativeLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (x > getWidth()*0.5){
            lp.leftMargin = x - PicTagView.getViewWidth();
            view = new PicTagView(getContext(), PicTagView.Direction.Right);
        }else{
            lp.leftMargin = x;
            view = new PicTagView(getContext(), PicTagView.Direction.Left);
        }

        lp.topMargin = y;

        if (lp.topMargin < 0) lp.topMargin = 0;

        else if ((lp.topMargin+PicTagView.getViewHeight()) > getHeight()){
            lp.topMargin = getHeight() - PicTagView.getViewHeight();
        }

        this.addView(view,lp);
    }

    private void moveView(int x,int y){
        if(touchView == null) return;
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.leftMargin = x - startX + startTouchViewLeft;
        params.topMargin = y - startY + startTouchViewTop;
        //限制子控件移动必须在视图范围内
        if(params.leftMargin<0||(params.leftMargin+touchView.getWidth())>getWidth())params.leftMargin = touchView.getLeft();
        if(params.topMargin<0||(params.topMargin+touchView.getHeight())>getHeight())params.topMargin = touchView.getTop();
        touchView.setLayoutParams(params);
    }

    private boolean hasView(int x,int y){

        for (int i = 0; i< getChildCount();i++){
            View child = getChildAt(i);
            int left = (int) child.getX();

            int top = (int) child.getY();

            int right = child.getRight();
            int bottom = child.getBottom();

            Rect rect = new Rect(left,top,right,bottom);
            if (rect.contains(x,y)){
                touchView = child;
                touchView.bringToFront();
                return true;
            }
        }

        touchView = null;
        return false;

    }



}
