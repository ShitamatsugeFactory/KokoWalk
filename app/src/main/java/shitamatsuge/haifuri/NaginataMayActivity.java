package shitamatsuge.haifuri;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class NaginataMayActivity extends NaginataActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = getIntent().getIntExtra("layout",R.layout.activity_naginata_may);
        mWaitTime = getIntent().getIntExtra("waitTime",400);
        if (layout == R.layout.activity_naginata_koko_hard) {
            //mAutoMode = false;
            mHard = true;
            mDiffLimit = 1980;
            mPointBase *= 2;
        }
        if (layout == R.layout.activity_naginata_doitsujin) {
            //mAutoMode = false;
            mDoitsu = true;
            mDiffLimit = 1999;
            mPointBase *= 4;
        }
        setContentView(layout);
        backGround = (FrameLayout)findViewById(R.id.backGround);
        mTimerText = (TextView)findViewById(R.id.clockView);
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

        mCharaDamage[1] = (ImageView)findViewById(R.id.damage);
        mCharaDamage[0] = (ImageView)findViewById(R.id.damage_r);
    }
}
