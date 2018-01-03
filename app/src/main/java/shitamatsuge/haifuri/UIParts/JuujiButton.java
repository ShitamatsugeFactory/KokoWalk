package shitamatsuge.haifuri.UIParts;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class JuujiButton extends FrameLayout {
    public interface JuujiListener {
        public Position onTouchRelativePosition(Position position);
        public void onReleaseEvent();
    }

    final String TAG = "JuujiButton";
    float mLeft, mRight, mTop, mBottom;
    float mCenterX, mCenterY;
    private JuujiListener mListener;

    public static class Position{
        public float x;
        public float y;
        public Position(float _x, float _y) {
            x = _x;
            y = _y;
        }
    }

    public JuujiButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(JuujiListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouch , " + ev);
        if (mListener != null) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                mListener.onReleaseEvent();
            } else {
                if (mCenterX == 0) {
                    mLeft = this.getLeft();
                    mRight = this.getRight();
                    mTop = this.getTop();
                    mBottom = this.getBottom();
                    mCenterX = (mRight - mLeft) / 2;
                    mCenterY = (mBottom - mTop) / 2;
                    Log.d(TAG, "start : " + mLeft + ", " + mRight + ", " + mTop + ", " + mBottom);
                }
                Log.d(TAG, "touch : " + ev.getX() + " , " + ev.getY() + " , " + mCenterX + " , " + mCenterY);
                float x = ev.getX() - mCenterX;
                float y = ev.getY() - mCenterY;
                float r = (float) Math.sqrt(x * x + y * y);
                mListener.onTouchRelativePosition(new Position(x / r, y / r));
            }
        }
        return true;
    }
}
