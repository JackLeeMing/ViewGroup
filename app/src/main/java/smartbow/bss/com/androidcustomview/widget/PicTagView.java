package smartbow.bss.com.androidcustomview.widget;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import smartbow.bss.com.androidcustomview.R;

/**
 * 作者: lijiakui on 16/6/28 11:35.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
public class PicTagView extends RelativeLayout implements TextView.OnEditorActionListener {

    private Context context;
    private TextView textTV;
    private EditText textEdit;

    private View tagView;



    public enum TagStatus{
        Normal,Edit
    }

    public enum Direction{
        Left,Right
    }

    private Direction direction = Direction.Left;
    private InputMethodManager inputMethodManager;

    private static final int ViewWidth = 80;
    private static final int ViewHeight = 50;




    public PicTagView(Context context,Direction direction) {
        super(context);
        this.context = context;
        this.direction = direction;
        initViews();
        initEvents();

    }

    private void initViews(){
        LayoutInflater.from(context).inflate( R.layout.pic_tag_layout,this,true);
        textTV = (TextView) findViewById(R.id.tvPictureTagLabel);
        textEdit = (EditText) findViewById(R.id.etPictureTagLabel);
        tagView = findViewById(R.id.loTag);

        inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        directionChange();

    }

    private void initEvents(){
        textEdit.setOnEditorActionListener(this);

    }

    private void directionChange(){
        switch (direction){
            case Left:
                tagView.setBackgroundResource(R.drawable.bg_picturetagview_tagview_left);
                break;
            case Right:
                tagView.setBackgroundResource(R.drawable.bg_picturetagview_tagview_right);
                break;
        }
    }

    public void setStatus(TagStatus status){
        switch (status){
            case Normal:
                textTV.setVisibility(VISIBLE);
                textEdit.clearFocus();
                textTV.setText(textEdit.getText());
                textEdit.setVisibility(GONE);
                inputMethodManager.hideSoftInputFromWindow(textEdit.getWindowToken(),0);
                break;

            case Edit:
                textTV.setVisibility(GONE);
                textEdit.setVisibility(VISIBLE);
                textEdit.requestFocus();
                inputMethodManager.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        setStatus(TagStatus.Normal);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View parent = (View) getParent();
        int halfParentWidth = (int) (parent.getWidth() *0.5);
        //int center = (int) (l + getWidth()*0.5);

       // this.getLeft();

        if (getLeft() <= halfParentWidth){
            direction = Direction.Left;
        }else {
            direction = Direction.Right;
        }
        directionChange();
    }

    public static int getViewWidth(){
        return ViewWidth;
    }

    public static int getViewHeight(){
        return ViewHeight;
    }
}
