package smartbow.bss.com.androidcustomview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbow.bss.com.androidcustomview.animator.MyAnimaView;

public class AnimatorActivity extends AppCompatActivity  {



    @BindView(R.id.rootView)RelativeLayout rootView;

   @BindView(R.id.circleView)
   MyAnimaView animaView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);
    }

    public void paowuxian(View view){
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0,0));
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                pointF.x = 200*fraction*3;
                pointF.y = 0.5f*200*(fraction*3)*(fraction*3);//1/2gt2// 0~3
                return pointF;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF)animation.getAnimatedValue();
                animaView.setX(pointF.x);
                animaView.setY(pointF.y);

            }
        });
    }

}
