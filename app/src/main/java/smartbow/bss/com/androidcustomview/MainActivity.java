package smartbow.bss.com.androidcustomview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbow.bss.com.androidcustomview.utils.TLog;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    @BindView(R.id.flipper)ViewFlipper flipper;

    @BindView(R.id.flipper2)ViewFlipper flipper2;

    Animation leftInAnimation;
    Animation leftOutAnimation;
    Animation rightInanimation;
    Animation rightOutanimation;

    GestureDetector gestureDetector;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        context = this;

        gestureDetector = new GestureDetector(context,this);
        leftInAnimation = AnimationUtils.loadAnimation(context,R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(context,R.anim.left_out);
        rightInanimation = AnimationUtils.loadAnimation(context,R.anim.right_in);
        rightOutanimation = AnimationUtils.loadAnimation(context,R.anim.right_out);

        flipper.setFlipInterval(2000);
        flipper.setInAnimation(leftInAnimation);
        flipper.setOutAnimation(leftOutAnimation);

        flipper2.setFlipInterval(1000);
        flipper2.setInAnimation(rightInanimation);
        flipper2.setOutAnimation(rightOutanimation);

        startStr(null);

    }


    @Override
    protected void onStart() {
        super.onStart();
        flipper.startFlipping();
        flipper2.startFlipping();
    }

    private int textSize = 16;


    public void startList(View view){

        getTextView("1. 大家好，我是孙福生。");
        getTextView("2. 欢迎大家关注我哦！");
        getTextView("3. GitHub帐号：sfsheng0322");
        getTextView("4. 新浪微博：孙福生微博");
        getTextView("界面.....5");
        getTextView("6. 微信公众号：孙福生");
        getTextView("界面.....7");
        getTextView("5. 个人博客：sunfusheng.com");


        flipper.startFlipping();
    }

    public void startStr(View view){

        TLog.error("btn 2");

        final String notice = getString(R.string.marquee_texts);
        if (TextUtils.isEmpty(notice)) return;
        flipper2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                flipper2.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                startWithFixedWidth(notice, flipper2.getWidth());
            }
        });

    }

    // 根据宽度和公告字符串启动轮播
    private List<String> notices = new ArrayList<>();


    private void startWithFixedWidth(String notice, int width) {

        TLog.error(notice);
        int noticeLength = notice.length();
        int dpW = px2dip(context, width);
        int limit = dpW / textSize;
        if (dpW == 0) {
            throw new RuntimeException("Please set MarqueeView width !");
        }

        TLog.error("dpW: "+dpW);

        if (noticeLength <= limit) {
            notices.add(notice);
        } else {
            int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
            for (int i = 0; i < size; i++) {
                int startIndex = i * limit;
                int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
                notices.add(notice.substring(startIndex, endIndex));
            }
        }

        start();
    }

    // 启动轮播
    public boolean start() {

        TLog.error(notices.toString());
        if (notices == null || notices.size() == 0) return false;
        flipper2.removeAllViews();

        for (int i = 0; i < notices.size(); i++) {
            final TextView textView = createTextView(notices.get(i), i);
           flipper2.addView(textView);
        }

        if (notices.size() > 1) {
            flipper2.startFlipping();
        }
        return true;
    }

    // 创建ViewFlipper下的TextView
    private TextView createTextView(String text, int position) {
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER|Gravity.START);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(textSize);
        tv.setSingleLine(true);
        tv.setTag(position);
        return tv;
    }

    // 将px值转换为dip或dp值，保证尺寸大小不变
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    private void getTextView(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER|Gravity.START);
        textView.setTextSize(16);
        textView.setTextColor(Color.WHITE);
        textView.setSingleLine(true);
        flipper.addView(textView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        if (motionEvent.getX() - motionEvent1.getX() >120){
            flipper.setInAnimation(leftInAnimation);
            flipper.setOutAnimation(leftOutAnimation);
            flipper.showNext();
            return  true;
        }else if(motionEvent.getX() - motionEvent1.getX() <-120){
            flipper.setInAnimation(rightInanimation);
            flipper.setOutAnimation(rightOutanimation);
            flipper.showPrevious();
            return true;
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        flipper.stopFlipping();
        flipper2.stopFlipping();
    }
}
