package shitamatsuge.haifuri;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import shitamatsuge.haifuri.CharaViews.CharaView;
import shitamatsuge.haifuri.CharaViews.KokoView;
import shitamatsuge.haifuri.UIParts.JuujiButton;

public class SakakuraActivity extends Activity {
    String mVersion = "3.1.1";
    FrameLayout mBackGround;
    float mBackGroundHeight;
    float mBackGroundWidth;
    JuujiButton mJuujiButton;
    CharaView mKoko;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sakakura);
        mBackGround = (FrameLayout)findViewById(R.id.parentFrameLayout);
        mKoko = new KokoView(this, null);
        mJuujiButton = (JuujiButton)findViewById(R.id.juujiButton);
        mJuujiButton.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mJuujiButton.init(new JuujiButton.JuujiListener() {
            @Override
            public JuujiButton.Position onTouchRelativePosition(JuujiButton.Position position) {
                Log.d("test_move(pre)", position.x + " : " + position.y);
                Log.d("test_move(pre)", mKoko.getMeasuredWidth() + " : " + mKoko.getMeasuredHeight());
                // todo 1.連続で歩く場合に歩行モーションが途切れる、等速直線運動しない問題
                // todo 2.タッチ操作でドラッグしないとonTouchのmoveが呼ばれないのでonLongClickかreleaseイベントが呼ばれなければJuujiButtonのonTouch内でselfFuckさせる
                float x = position.x * mKoko.getMeasuredWidth() / 0.5f + mKoko.getmCurrentX();
                float y = position.y * mKoko.getMeasuredWidth() / 0.5f + mKoko.getmCurrentY();
                Log.d("test_move(pre)", x + " : " + y);
                x = Math.max(0, x);
                y = Math.max(mBackGroundHeight / 3, y);
                x = Math.min(mBackGroundWidth - mKoko.getMeasuredWidth(), x);
                y = Math.min(mBackGroundHeight - mKoko.getMeasuredHeight(), y);
                Log.d("test_move(pos)", x + " : " + y + " , " + mKoko.getmCurrentX() + " , " + mKoko.getmCurrentY());
                Log.d("test_move(pos)", "================");
                mKoko.walk(mKoko, x, y, 1000);
                return null;
            }
        });
        mBackGround.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mBackGround.addView(mKoko);
        mBackGroundWidth = mBackGround.getMeasuredWidth();
        mBackGroundHeight = mBackGround.getMeasuredHeight();
        float height = mBackGroundHeight / 4;// 108 : 210
        float width = height * (108 / 210.f);
        mKoko.setLayoutParams(new FrameLayout.LayoutParams((int)width, (int)height));
        (mKoko.findViewById(R.id.parent)).setLayoutParams(new LinearLayout.LayoutParams((int)width, (int)height));
        mKoko.walk(mKoko, (mBackGround.getMeasuredWidth() + mKoko.getMeasuredWidth()) / 2, (mBackGround.getMeasuredHeight() + mKoko.getMeasuredHeight())/ 2, 0);
        mKoko.setVisibility(View.VISIBLE);

    }


}
