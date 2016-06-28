package smartbow.bss.com.androidcustomview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    @BindView(R.id.flipper)ViewFlipper flipper;

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

        getTextView("1. 大家好，我是孙福生。");
        getTextView("2. 欢迎大家关注我哦！");
        getTextView("3. GitHub帐号：sfsheng0322");
        getTextView("4. 新浪微博：孙福生微博");
        getTextView("界面.....5");
        getTextView("6. 微信公众号：孙福生");
        getTextView("界面.....7");
        getTextView("5. 个人博客：sunfusheng.com");

        leftInAnimation = AnimationUtils.loadAnimation(context,R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(context,R.anim.left_out);
        rightInanimation = AnimationUtils.loadAnimation(context,R.anim.right_in);
        rightOutanimation = AnimationUtils.loadAnimation(context,R.anim.right_out);


        flipper.setFlipInterval(2000);
        flipper.setInAnimation(leftInAnimation);
        flipper.setOutAnimation(leftOutAnimation);
        flipper.startFlipping();





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
    }
}
