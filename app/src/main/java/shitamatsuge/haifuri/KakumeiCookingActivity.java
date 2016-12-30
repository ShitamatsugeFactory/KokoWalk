package shitamatsuge.haifuri;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class KakumeiCookingActivity extends NaginataActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = getIntent().getIntExtra("layout", R.layout.activity_cooking_attyan);
        mWaitTime = getIntent().getIntExtra("waitTime", 400);

        setContentView(layout);
        backGround = (FrameLayout) findViewById(R.id.backGround);
        mTimerText = (TextView) findViewById(R.id.clockView);
        mTimerText.setVisibility(View.GONE);
        setScore();

        mCharaUp = new ImageView[2];
        mCharaMiddle = new ImageView[2];
        mCharaNormal = new ImageView[2];

        mCharaUp[0] = (ImageView) findViewById(R.id.upper);
        mCharaUp[1] = (ImageView) findViewById(R.id.upper_r);

        mCharaMiddle[0] = (ImageView) findViewById(R.id.middle);
        mCharaMiddle[1] = (ImageView) findViewById(R.id.middle_r);

        mCharaNormal[0] = (ImageView) findViewById(R.id.def);
        mCharaNormal[1] = (ImageView) findViewById(R.id.def_r);

        mCharaDamage[1] = (ImageView) findViewById(R.id.damage);
        mCharaDamage[0] = (ImageView) findViewById(R.id.damage_r);
        damageButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFailed = true;
            }
        });
        damageButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFailed = true;
            }
        });
    }

    /*
     * call by super.onCreate()
     */
    @Override
    protected void suicaMaker(int time) {
        maxScoreView.setVisibility(View.INVISIBLE);
        ringoMaker(1);
    }

    int mRingoUsed;
    boolean mFailed = false;
    protected void ringoMaker(final int stage) {
        Log.d("test", "ringo : " + stage, new Throwable());
        mRingoUsed = 0;
        final int HIGH_SPEED = 800;
        final int SLOW_SPEED = 3000;
        final int MAX_RPS = 1000 / 8;//MAX_RINGO_PER_SECOND = 4; 最大で秒間 4個のリンゴ
        final int ringoCnt = (stage*2 + 1);
        final int speed = Math.max(HIGH_SPEED, SLOW_SPEED - stage * 300);
        final int diff = Math.max(MAX_RPS, 2000 / (stage / 2 + 1));
        int startTime = 0;

        /* STAGE_Xの画像を表示 , できれば画像を出したほうが良い */
        mScoreView.setText("Stage " + stage);
        startTime += 0;

        /* スタンバイ用の画像を表示 */
        mTimerText.setText("STAGE : " + stage + " Ready.");
        mTimerText.setVisibility(View.VISIBLE);
        if (TimerHandler == null) {
            TimerHandler = new Handler();
        }
        TimerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTimerText.setText("STAGE : " + stage + " Ready..");
            }
        }, 1000);
        TimerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTimerText.setText("STAGE : " + stage + " Ready...");
            }
        },2000);
        TimerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTimerText.setText("GO");
            }
        },3000);
        TimerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTimerText.setVisibility(View.INVISIBLE);
            }
        }, 3500);

        startTime += 3000;

        if (suicaHandler == null){
            suicaHandler = new Handler();
        }

        /* RingoMakeのLoop設定 */
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (Math.random() > 0.2f) {
                    if (mRingoUsed < ringoCnt) {
                        mRingoUsed++;
                        Log.d("ringo", "mRingoUsed = " + mRingoUsed);
                        addRingo(speed);
                        suicaHandler.postDelayed(this, diff);
                    } else {
                        /* 一度も失敗していなければ次のステージへ */
                        suicaHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!mFailed) {
                                ringoMaker(stage + 1);
                                } else {
                                    mScoreView.setText("Stage " + stage + " failed");
                                    mTimerText.setText("Stage " + stage + " failed");
                                    mTimerText.setVisibility(View.VISIBLE);
                                }
                            }
                        }, 2000);
                    }
                } else {
                    suicaHandler.postDelayed(this, diff);
                }
            }
        };

        /* start , Stage Xの宣言とスタンバイ用の画像表示の終了後に8秒待ってからリンゴを射出し始める */
        suicaHandler.postDelayed(runnable, startTime + 1000);
    }

    protected void addRingo(int speed) {
        int timer = speed  ;
        float startX, startY, endX, endY;

        startX = -buttons[0].getMeasuredWidth()*3;
        startY = (float)(backGround.getMeasuredHeight() * 0.4 + ((int)(Math.random() * 3) * backGround.getMeasuredHeight() * 0.16));
        endX = backGround.getMeasuredWidth() * 1.1f;
        endY = startY;

        Button b1 = buttons[0];
        Button b2 = buttons[0 + 1];
        Button  bDame = damageButtons[0];
        final Ringo ringo = new Ringo(getBaseContext(),null,startX, startY, endX, endY, timer, b1, b2, bDame, mPointBase);
        backGround.addView(ringo);

        TimerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                backGround.removeView(ringo);
            }
        }, timer * 2);
    }
}
