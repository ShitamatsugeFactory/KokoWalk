package shitamatsuge.haifuri;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import shitamatsuge.haifuri.CharaViews.CharaView;
import shitamatsuge.haifuri.CharaViews.KokoFireView;
import shitamatsuge.haifuri.CharaViews.KokoView;
import shitamatsuge.haifuri.UIParts.JuujiButton;

public class SakakuraActivity extends Activity {
    String mVersion = "3.1.1";
    FrameLayout mBackGround;
    float mBackGroundHeight;
    float mBackGroundWidth;
    JuujiButton mJuujiButton;
    KokoFireView mKoko;
    KokoView mKokoHontaiView;
    Runnable mKokoWalkRunnable;
    Handler mKokoWalkHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sakakura);
        mBackGround = (FrameLayout)findViewById(R.id.parentFrameLayout);
        mKoko = new KokoFireView(this, null);
        mJuujiButton = (JuujiButton)findViewById(R.id.juujiButton);
        mJuujiButton.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mJuujiButton.init(new JuujiButton.JuujiListener() {
            @Override
            public JuujiButton.Position onTouchRelativePosition(final JuujiButton.Position position) {
                mKokoWalkHandler.removeCallbacksAndMessages(null);
                mKokoWalkRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test_move(pre)", position.x + " : " + position.y);
                        Log.d("test_move(pre)", mKokoHontaiView.getMeasuredWidth() + " : " + mKokoHontaiView.getMeasuredHeight());
                        float x = position.x * mKokoHontaiView.getMeasuredWidth() / 20 + mKoko.getmCurrentX();
                        float y = position.y * mKokoHontaiView.getMeasuredWidth() / 20 + mKoko.getmCurrentY();
                        Log.d("test_move(pre)", x + " : " + y);
                        float w = mKokoHontaiView.getMeasuredWidth();
                        float h = mKokoHontaiView.getMeasuredHeight();
                        x = Math.max(-10 + w - mKoko.getMeasuredWidth(), x);
                        y = Math.max(mBackGround.getMeasuredHeight() * 1 / 2, y);
                        x = Math.min(mBackGroundWidth - (w - mKoko.getMeasuredWidth()) + 10, x);
                        y = Math.min(mBackGroundHeight - h / 3, y);
                        Log.d("test_move(pos)", x + " : " + y + " , " + mKokoHontaiView.getmCurrentX() + " , " + mKokoHontaiView.getmCurrentY() + " , back(w, h) = " + mBackGroundWidth + " , " + mBackGroundHeight);
                        Log.d("test_move(pos)", "================");
                        mKoko.walkForSakakura(mKoko, x, y, 50);
                        mKokoWalkHandler.postDelayed(mKokoWalkRunnable, 50);
                    }
                };
                mKokoWalkHandler.post(mKokoWalkRunnable);
                return null;
            }

            @Override
            public void onReleaseEvent() {
                mKokoWalkHandler.removeCallbacksAndMessages(null);
            }
        });
        mBackGround.addView(mKoko);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mKokoHontaiView = (KokoView) mKoko.findViewById(R.id.hontai_view);
        mBackGroundWidth = mBackGround.getMeasuredWidth();
        mBackGroundHeight = mBackGround.getMeasuredHeight();
        float height = mBackGroundHeight / 3;// 108 : 210
        float outerWidth = 610f;
        float innerWidth = 108f;
        mKoko.setLayoutParams(new FrameLayout.LayoutParams((int)(outerWidth * height / 210), (int)height));
        mKoko.findViewById(R.id.fire_view).setLayoutParams(new FrameLayout.LayoutParams((int)(outerWidth * height / 210), (int)height));
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int)(innerWidth * height / 210), (int)height);
        lp.leftMargin = (int)((outerWidth - 108) * height / 210) / 2;
        mKokoHontaiView.setLayoutParams(lp);
        mKokoHontaiView.findViewById(R.id.parent).setLayoutParams(new LinearLayout.LayoutParams((int)(innerWidth * height / 210), (int)height));
        mKoko.walk(mKoko, (mBackGroundWidth - outerWidth) / 2 , mBackGroundHeight * 2 / 3, 10);
        mKoko.postDelayed(new Runnable() {
            @Override
            public void run() {
                mKoko.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }


    public void onAttackButton0(View v) {
        Log.d("button0", "clicked");
        mKoko.downerAnimation(500);
    }

    public void onAttackButton1(View v) {
        Log.d("button1", "clicked");
        mKoko.fireAnimation(1000);
    }



}
