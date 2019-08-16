package com.ufrbuild.mh4x0f.painelufrb.ui.activity.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.img_logo_splash)
    ImageView mImageLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        CommonUtils.getSupportActionBar(this);

        Animation fadEffect = AnimationUtils.loadAnimation(this, R.anim.effect_splash);
        //mTvDescription.startAnimation(fadEffect);
        mImageLogo.startAnimation(fadEffect);
        final Intent intent = new Intent(this, MainActivity.class);
        Thread timer  = new Thread() {
            @Override
            public void run() {
                super.run();
                try{
                    sleep(3000);
                }catch (InterruptedException e){

                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();
    }
}
