package smartbow.bss.com.androidcustomview.animator;

import android.animation.TypeEvaluator;

/**
 * 作者: lijiakui on 16/7/4 08:45.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
public class PointEvaluator implements TypeEvaluator<Float> {
    @Override
    public Float evaluate(float fraction, Float startPoint, Float endPoint) {
        float x = startPoint+fraction*(endPoint - startPoint);
        return x;
    }
}
