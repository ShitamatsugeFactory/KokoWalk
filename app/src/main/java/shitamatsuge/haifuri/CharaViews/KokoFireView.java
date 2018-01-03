package shitamatsuge.haifuri.CharaViews;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import shitamatsuge.haifuri.MainActivity;
import shitamatsuge.haifuri.R;

public class KokoFireView extends CharaView {
    private String TAG = "CharaView";

    // ココちゃんのための特別な画像
    private ImageView mFireView;

    public KokoFireView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.koko_fire, this);

        mNormal = new FrameLayout[2];
        mWalk = new FrameLayout[6];
        mTouchUpper = new FrameLayout[2];
        mTouchDowner = new FrameLayout[2];
        mRandomAction = new FrameLayout[2];

        mBase = (FrameLayout)findViewById(R.id.parent);

        mNormal[0] = (FrameLayout)findViewById(R.id.normal);
        mNormal[1] = (FrameLayout)findViewById(R.id.normal_r);

        mWalk[0] = (FrameLayout)findViewById(R.id.walk0);
        mWalk[1] = (FrameLayout)findViewById(R.id.walk1);
        mWalk[2] = (FrameLayout)findViewById(R.id.walk2);
        mWalk[3] = (FrameLayout)findViewById(R.id.walk0_r);
        mWalk[4] = (FrameLayout)findViewById(R.id.walk1_r);
        mWalk[5] = (FrameLayout)findViewById(R.id.walk2_r);

        mTouchUpper[0] = (FrameLayout)findViewById(R.id.upper);
        mTouchUpper[1] = (FrameLayout)findViewById(R.id.upper_r);

        mTouchDowner[0] = (FrameLayout)findViewById(R.id.downer);
        mTouchDowner[1] = (FrameLayout)findViewById(R.id.downer_r);

        mRandomAction[0] = (FrameLayout)findViewById(R.id.random);
        mRandomAction[1] = (FrameLayout)findViewById(R.id.random_r);

        mFireView = new ImageView(getContext());
        mFireView = (ImageView) findViewById(R.id.fire_view);

        ((FrameLayout)findViewById(R.id.touch_up)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                upperAnimation(1200);
            }
        });
        ((FrameLayout)findViewById(R.id.touch_down)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                downerAnimation(800);
            }
        });
   }

   @Override
   public void walkAnimation(int ms) {
        if (!mLock) {
            super.walkAnimation(ms);
        }
   }

    @Override
    protected void setRandomActionImage() {
        int r = (int)(Math.random() * 13);
        switch (r) {
            case 0:
                mRandomAction[0].setBackgroundResource(R.drawable.shot1);
                mRandomAction[1].setBackgroundResource(R.drawable.shot1_r);
                break;
            case 1:
                mRandomAction[0].setBackgroundResource(R.drawable.naki1);
                mRandomAction[1].setBackgroundResource(R.drawable.naki1_r);
                break;
            case 2:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_sengen);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_sengen_r);
                break;
            case 3:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_sensei);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_sensei_r);
                break;
            case 4:
                mRandomAction[0].setBackgroundResource(R.drawable.kokotto_kokomi);
                mRandomAction[1].setBackgroundResource(R.drawable.kokotto_kokomi_r);
                break;
            case 5:
                mRandomAction[0].setBackgroundResource(R.drawable.kokotto_mi);
                mRandomAction[1].setBackgroundResource(R.drawable.kokotto_mi_r);
                break;
            case 6:
                mRandomAction[0].setBackgroundResource(R.drawable.kmoko_hai);
                mRandomAction[1].setBackgroundResource(R.drawable.kmoko_hai_r);
                break;
            case 7:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_kumori);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_kumori_r);
                break;
            case 8:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_zinb);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_zinb_r);
                break;
            case 9:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_zinbabue2);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_zinbabue2_r);
                break;
            case 10:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_uta);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_uta_r);
                break;
            case 11:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_denwa_naki);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_denwa_naki_r);
                break;
            case 12:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_zin2_naki);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_zin2_naki_r);
                break;
            default:
                mRandomAction[0].setBackgroundResource(R.drawable.naki1);
                mRandomAction[1].setBackgroundResource(R.drawable.naki1_r);
                break;
        }
    }

    int mFieldActionCounter = 0;
    private int mRandomActionCounter = 0;
    @Override
    public void nextAction(View v, float addX, float addY, int mSec) {
        mFieldActionCounter = Math.max(mFieldActionCounter - 1, 0);
        if (mLockUpperAction) {
            upperAnimation(mSec);
        } else if (mLockDownerAction) {
            downerAnimation(mSec);
        } else {
            double r = Math.random();
            if (r > 0.7) {
                mRandomActionNext = true;
            }
            Log.d(TAG, "mRandomActionCounter = " + mRandomActionCounter);
            if(!mLock) {
                if (mRandomActionNext && mRandomActionCounter == 0) {
                    mRandomActionCounter = 2;
                    randomAnimation(mSec);
                } else {
                    walk(v, addX, addY, mSec);
                }
            }
        }
    }

    public void fireAnimation(int mSec) {
        if(mSec < 0) {
            return ;
        }
        mLock = true;
        final int sec = mSec * 2;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLock = true;
                if(mHandler == null)mHandler = new Handler();
                mHandler.removeCallbacksAndMessages(null);
                mCurrentWalkIndex = 1;
                mFireView.setImageDrawable(getResources().getDrawable(mDirection == 0 ? R.drawable.koko_fire : R.drawable.koko_fire_r));
                mFireView.setVisibility(View.VISIBLE);
                mNormal[0].setVisibility(View.INVISIBLE);
                mNormal[1].setVisibility(View.INVISIBLE);
                mWalk[0].setVisibility(INVISIBLE);
                mWalk[1].setVisibility(INVISIBLE);
                mWalk[2].setVisibility(INVISIBLE);
                mWalk[3].setVisibility(INVISIBLE);
                mWalk[4].setVisibility(INVISIBLE);
                mWalk[5].setVisibility(INVISIBLE);
                int diff = 50;
                int currentSec = diff;
                MainActivity.mWashiButton.callOnClick();

                while (sec > currentSec) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mFireView.setImageDrawable(getResources().getDrawable(mDirection == 0 ? R.drawable.koko_fire : R.drawable.koko_fire_r));
                            mFireView.setVisibility(View.VISIBLE);
                            mNormal[0].setVisibility(View.INVISIBLE);
                            mNormal[1].setVisibility(View.INVISIBLE);
                        }
                    }, currentSec);
                    currentSec += diff;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLock = false;
                        mNormal[mDirection].setVisibility(View.VISIBLE);
                        mFireView.setVisibility(INVISIBLE);
                    }
                }, sec+10);
            }
        },10);
    }


}
