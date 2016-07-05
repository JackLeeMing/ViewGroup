package smartbow.bss.com.androidcustomview.animator;

import android.animation.TimeInterpolator;

/**
 * 作者: lijiakui on 16/7/4 10:45.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
public class DecelerateAccelerateInterpolator implements TimeInterpolator {
    float result;
    @Override
    public float getInterpolation(float input) {
        if (input <= 0.5){
            result = (float)(Math.sin(Math.PI*input))/2;
        }else {
            result = (float)(2-Math.sin(Math.PI*input))/2;
        }
        return result;
    }
}
