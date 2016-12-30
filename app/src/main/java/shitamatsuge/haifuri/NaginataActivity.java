package shitamatsuge.haifuri;

import android.app.Activity;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Timer;
import java.util.TimerTask;

public class NaginataActivity extends Activity {
    Runnable mRunnableUpperLeft;
    Runnable mRunnableMiddleLeft;
    Runnable mRunnableDefLeft;
    Runnable mRunnableUpperRight;
    Runnable mRunnableMiddleRight;
    Runnable mRunnableDefRight;
    Runnable mRunnableDamageLeft;
    Runnable mRunnableDamageRight;
    Handler mHandler;
    Handler mCharaHandler;

    Button[] buttons;
    ImageView [] mCharaUp;
    ImageView [] mCharaMiddle;
    ImageView [] mCharaNormal;

    Button[] damageButtons;
    ImageView [] mCharaDamage;

    boolean mAutoMode = true;

    protected long mScore;
    protected long maxScore;
    String mVersion = "2.0.0";

    int mDirection = 0;// 0 = <-
    int mWaitTime = 400;
    long mPointBase = 1100000000;
    TextView mScoreView;
    TextView maxScoreView;

    protected int TIME_LIMIT = 1000 * 120;

    boolean mHard = false;
    boolean mDoitsu = false;
    FrameLayout backGround;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naginata);

        backGround = (FrameLayout)findViewById(R.id.backGround);
        mTimerText = (TextView)findViewById(R.id.clockView);
        setScore();

        mCharaUp = new ImageView[2];
        mCharaMiddle = new ImageView[2];
        mCharaNormal = new ImageView[2];
        mCharaDamage = new ImageView[2];

        mCharaUp[0] = (ImageView)findViewById(R.id.upper);
        mCharaUp[1] = (ImageView)findViewById(R.id.upper_r);

        mCharaMiddle[0] = (ImageView)findViewById(R.id.middle);
        mCharaMiddle[1] = (ImageView)findViewById(R.id.middle_r);

        mCharaNormal[0] = (ImageView)findViewById(R.id.def);
        mCharaNormal[1] = (ImageView)findViewById(R.id.def_r);

        mCharaDamage[1] = (ImageView)findViewById(R.id.damage);
        mCharaDamage[0] = (ImageView)findViewById(R.id.damage_r);

        setButtons();
        suicaMaker(TIME_LIMIT);
    }

    SharedPreferences mPreference;
    public void setScore() {
        if (mDoitsu) {
            mPreference = getSharedPreferences("NAGINATA_DOITSU", MODE_PRIVATE);
        } else if(mHard) {
            mPreference = getSharedPreferences("NAGINATA_HARD", MODE_PRIVATE);
        } else {
            mPreference = getSharedPreferences("NAGINATA", MODE_PRIVATE);
        }
        maxScore = mPreference.getLong("maxScore", 0);
        mScoreView = (TextView)findViewById(R.id.socreView);
        maxScoreView = (TextView)findViewById(R.id.maxScoreView);
        if (mHard) {
            maxScoreView.setText(maxScore + " pt(ver:" + mVersion + " : HARD )");
        } else {
            maxScoreView.setText(maxScore + " pt(ver:" + mVersion + ")");
        }
    }

    Runnable suicaRunnable;
    boolean mRunning = false;
    int mCurrentTime = 0;
    int mLimitTime;
    Handler suicaHandler;
    Handler TimerHandler;
    TextView mTimerText;
    int mTimerCnt = 0;
    Runnable mClockRunnable;
    public int mDiffLimit = 1900;
    protected void suicaMaker(int time) {
        mScore = 0;
        mRunning = true;
        mLimitTime = time;
        final int diffTime = 2000;

        suicaRunnable = new Runnable() {
            @Override
            public void run() {
                addSuica();
                if (TIME_LIMIT - mTimerCnt < 0) {
                    mRunning = false;
                }
                if(mRunning) {
                    int offsetParam = mDoitsu ? 100 : 600;
                    long offset = Math.max(-mDiffLimit, - mScore / offsetParam);
                    suicaHandler.postDelayed(suicaRunnable, diffTime + offset);
                } else {
                    suicaHandler.removeCallbacksAndMessages(null);
                }
            }
        };
        suicaHandler = new Handler();
        suicaHandler.postDelayed(suicaRunnable, diffTime);


        mTimerText.setText("00:00.0");
        mTimerCnt = 0;

        mClockRunnable = new Runnable() {
            @Override
            public void run() {
                suicaHandler.post(new Runnable() {
                    public void run() {
                        mTimerCnt+=1000;
                        int t = TIME_LIMIT - mTimerCnt;
                        if(t >= 0) {
                            t /= 1000;
                        } else {
                            t = 0;
                        }
                        long mm = t / 60;
                        long ss = t % 60;
                        mTimerText.setText(String.format("%1$02d:%2$02d", mm, ss));
                    }
                });
                suicaHandler.postDelayed(mClockRunnable, 1000);
            }
        };
        suicaHandler.postDelayed(mClockRunnable, 0);
    }

    public void setButtons() {

        buttons = new Button[4];
        buttons[0] = (Button)findViewById(R.id.button);
        buttons[1] = (Button)findViewById(R.id.button2);
        buttons[2] = (Button)findViewById(R.id.button3);
        buttons[3] = (Button)findViewById(R.id.button4);

        mHandler = new Handler();
        mRunnableMiddleLeft = new Runnable() {
            @Override
            public void run() {
                mCharaMiddle[0].setVisibility(View.VISIBLE);
                mCharaMiddle[1].setVisibility(View.INVISIBLE);
                mCharaUp[0].setVisibility(View.INVISIBLE);
                mCharaUp[1].setVisibility(View.INVISIBLE);
                mCharaDamage[0].setVisibility(View.INVISIBLE);
                mCharaDamage[1].setVisibility(View.INVISIBLE);
                mCharaNormal[0].setVisibility(View.INVISIBLE);
                mCharaNormal[1].setVisibility(View.INVISIBLE);
                mHandler.postDelayed(mRunnableDefLeft,mWaitTime);
            }
        };
        mRunnableUpperLeft = new Runnable() {
            @Override
            public void run() {
                mCharaMiddle[0].setVisibility(View.INVISIBLE);
                mCharaMiddle[1].setVisibility(View.INVISIBLE);
                mCharaUp[0].setVisibility(View.VISIBLE);
                mCharaUp[1].setVisibility(View.INVISIBLE);
                mCharaDamage[0].setVisibility(View.INVISIBLE);
                mCharaDamage[1].setVisibility(View.INVISIBLE);
                mCharaNormal[0].setVisibility(View.INVISIBLE);
                mCharaNormal[1].setVisibility(View.INVISIBLE);
                mHandler.postDelayed(mRunnableDefLeft, mWaitTime);
            }
        };
        mRunnableDefLeft = new Runnable() {
            @Override
            public void run() {
                mCharaMiddle[0].setVisibility(View.INVISIBLE);
                mCharaMiddle[1].setVisibility(View.INVISIBLE);
                mCharaUp[0].setVisibility(View.INVISIBLE);
                mCharaUp[1].setVisibility(View.INVISIBLE);
                mCharaDamage[0].setVisibility(View.INVISIBLE);
                mCharaDamage[1].setVisibility(View.INVISIBLE);
                mCharaNormal[0].setVisibility(View.VISIBLE);
                mCharaNormal[1].setVisibility(View.INVISIBLE);
            }
        };
        mRunnableMiddleRight = new Runnable() {
            @Override
            public void run() {
                mCharaMiddle[0].setVisibility(View.INVISIBLE);
                mCharaMiddle[1].setVisibility(View.VISIBLE);
                mCharaUp[0].setVisibility(View.INVISIBLE);
                mCharaUp[1].setVisibility(View.INVISIBLE);
                mCharaDamage[0].setVisibility(View.INVISIBLE);
                mCharaDamage[1].setVisibility(View.INVISIBLE);
                mCharaNormal[0].setVisibility(View.INVISIBLE);
                mCharaNormal[1].setVisibility(View.INVISIBLE);
                mHandler.postDelayed(mRunnableDefRight, mWaitTime);
            }
        };
        mRunnableUpperRight = new Runnable() {
            @Override
            public void run() {
                mCharaMiddle[0].setVisibility(View.INVISIBLE);
                mCharaMiddle[1].setVisibility(View.INVISIBLE);
                mCharaUp[0].setVisibility(View.INVISIBLE);
                mCharaUp[1].setVisibility(View.VISIBLE);
                mCharaDamage[0].setVisibility(View.INVISIBLE);
                mCharaDamage[1].setVisibility(View.INVISIBLE);
                mCharaNormal[0].setVisibility(View.INVISIBLE);
                mCharaNormal[1].setVisibility(View.INVISIBLE);
                mHandler.postDelayed(mRunnableDefRight, mWaitTime);
            }
        };
        mRunnableDefRight = new Runnable() {
            @Override
            public void run() {
                mCharaMiddle[0].setVisibility(View.INVISIBLE);
                mCharaMiddle[1].setVisibility(View.INVISIBLE);
                mCharaUp[0].setVisibility(View.INVISIBLE);
                mCharaUp[1].setVisibility(View.INVISIBLE);
                mCharaDamage[0].setVisibility(View.INVISIBLE);
                mCharaDamage[1].setVisibility(View.INVISIBLE);
                mCharaNormal[0].setVisibility(View.INVISIBLE);
                mCharaNormal[1].setVisibility(View.VISIBLE);
            }
        };


        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(mRunnableDefLeft, 0);
                mHandler.postDelayed(mRunnableUpperLeft, 20);
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(mRunnableDefLeft, 0);
                mHandler.postDelayed(mRunnableMiddleLeft, 20);
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(mRunnableDefRight, 0);
                mHandler.postDelayed(mRunnableUpperRight, 20);
            }
        });
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(mRunnableDefRight, 0);
                mHandler.postDelayed(mRunnableMiddleRight, 20);
            }
        });


        mRunnableDamageRight = new Runnable() {
            @Override
            public void run() {
                mCharaMiddle[0].setVisibility(View.INVISIBLE);
                mCharaMiddle[1].setVisibility(View.INVISIBLE);
                mCharaUp[0].setVisibility(View.INVISIBLE);
                mCharaUp[1].setVisibility(View.INVISIBLE);
                mCharaDamage[0].setVisibility(View.VISIBLE);
                mCharaDamage[1].setVisibility(View.INVISIBLE);
                mCharaNormal[0].setVisibility(View.INVISIBLE);
                mCharaNormal[1].setVisibility(View.INVISIBLE);
                mHandler.postDelayed(mRunnableDefRight, mWaitTime * 3);
            }
        };
        mRunnableDamageLeft = new Runnable() {
            @Override
            public void run() {
                mCharaMiddle[0].setVisibility(View.INVISIBLE);
                mCharaMiddle[1].setVisibility(View.INVISIBLE);
                mCharaUp[0].setVisibility(View.INVISIBLE);
                mCharaUp[1].setVisibility(View.INVISIBLE);
                mCharaDamage[0].setVisibility(View.INVISIBLE);
                mCharaDamage[1].setVisibility(View.VISIBLE);
                mCharaNormal[0].setVisibility(View.INVISIBLE);
                mCharaNormal[1].setVisibility(View.INVISIBLE);
                mHandler.postDelayed(mRunnableDefLeft, mWaitTime * 3);
            }
        };

        damageButtons = new Button[2];
        damageButtons[0] = new Button(getBaseContext());
        damageButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(mRunnableDefLeft, 0);
                mHandler.postDelayed(mRunnableDamageLeft, 20);
            }
        });
        damageButtons[1] = new Button(getBaseContext());
        damageButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(mRunnableDefRight, 0);
                mHandler.postDelayed(mRunnableDamageRight, 20);
            }
        });
    }

    protected void addSuica() {
        int timer = (int)((Math.random() * 500) + 1400 + Math.max(mDoitsu ? -1350 : -1000,  - mScore / 700) )   ;
        int r =  (int)(Math.random() * 2);
        boolean isLeft = r == 0;
        float startX, startY, endX, endY;
        if(isLeft) {
            startX = -buttons[0].getMeasuredWidth();
            startY = (float)(Math.random()*300);
            endX = backGround.getMeasuredWidth() * 2 / 5;
            endY = backGround.getMeasuredHeight() * 2 / 3;
        } else {
            startX = backGround.getMeasuredWidth()*1.2f;
            startY = (float)(Math.random()*300);
            endX = backGround.getMeasuredWidth() * 2 / 5;
            endY = backGround.getMeasuredHeight() * 2 / 3;
        }

        Button  b1 = isLeft ? buttons[0] : buttons[2];
        Button b2 = isLeft ? buttons[0 + 1] : buttons[2 + 1];
        Button  bDame = isLeft ? damageButtons[0] : damageButtons[1];
        final Suica suica = new Suica(getBaseContext(),null,startX, startY, endX, endY, timer, b1, b2, bDame, mPointBase);
        backGround.addView(suica);

        if (mAutoMode) {
            suicaHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    suica.callOnClick();
                }
            }, (int)(timer * 0.7));
        }
        TimerHandler = new Handler();
        TimerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScore += suica.getScore();
                mScoreView.setText(mScore + " pt(ver:" + mVersion +")");
                Log.d("test", "new record ? . " + mScore + " , " + maxScore + " , " +  (mScore > maxScore));
                if (mScore > maxScore) {
                    maxScore = mScore;
                    if (mHard) {
                        maxScoreView.setText(maxScore + " pt(ver:" + mVersion + " : HARD )");
                    } else {
                        maxScoreView.setText(maxScore + " pt(ver:" + mVersion + ")");
                    }
                   if(mPreference == null) {
                       mPreference = getSharedPreferences("NAGINATA", MODE_ENABLE_WRITE_AHEAD_LOGGING);
                   }
                    SharedPreferences.Editor editor = mPreference.edit();
                    editor.putLong("maxScore", maxScore);
                    editor.apply();
                }
                backGround.removeView(suica);
            }
        },timer * 2);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mCharaHandler != null) {
            mCharaHandler.removeCallbacksAndMessages(null);
        }
        if (suicaHandler != null) {
            suicaHandler.removeCallbacksAndMessages(null);
        }
        if (TimerHandler != null) {
            TimerHandler.removeCallbacksAndMessages(null);
        }
    }
}
