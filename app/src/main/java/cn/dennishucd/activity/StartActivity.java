package cn.dennishucd.activity;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import cn.dennishucd.FFmpeg4AndroidActivity;
import cn.dennishucd.R;
import cn.dennishucd.cache.BaseActivity;
import cn.dennishucd.chart.LineChartBuilder;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

/**
 * 启动引导页
 * 
 * @author lqb
 * 
 */
public class StartActivity extends BaseActivity implements Runnable,AnimatorListener {
	 private static final int ANIM_COUNT = 4;
	 private FrameLayout mContainer;
	 private ImageView mView;
	 private Random mRandom = new Random();
	 private int mIndex = 0;
	 private static final int[] PHOTOS = new int[] {R.drawable.photo1,
           R.drawable.photo2, R.drawable.photo3, R.drawable.photo4,
           R.drawable.photo5, R.drawable.photo6 };
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    mContainer = new FrameLayout(this);
        mContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        mView = createNewView();
        mContainer.addView(mView);
        setContentView(mContainer);
		//setContentView(R.layout.start_activity);
		// 启动一个线程
		new Thread(this).start();
	}

	public void run() {
		try {
			// 一秒后跳转到登录界面
			Thread.sleep(2500);
			startActivity(new Intent(StartActivity.this, LoginActivity.class));
			//startActivity(new Intent(StartActivity.this, ScrollActivity.class));
			//startActivity(new Intent(StartActivity.this, Grallery3DActivity.class));
			finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	 private ImageView createNewView() {
	        ImageView ret = new ImageView(this);
	        ret.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
	                LayoutParams.FILL_PARENT));
	        ret.setScaleType(ScaleType.FIT_XY);
	        ret.setImageResource(PHOTOS[mIndex]);
	        mIndex = (mIndex + 1 < PHOTOS.length) ? mIndex + 1 : 0;
	        return ret;
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();
	        nextAnimation();
	    }

	    private void nextAnimation() {
	        AnimatorSet anim = new AnimatorSet();
	        final int index = mRandom.nextInt(ANIM_COUNT);
	        switch (index) {
	        case 0:
	            anim.playTogether(
	                    ObjectAnimator.ofFloat(mView, "scaleX", 1.5f, 1f),
	                    ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1f));
	            break;
	        case 1:
	            anim.playTogether(ObjectAnimator.ofFloat(mView, "scaleX", 1, 1.5f),
	                    ObjectAnimator.ofFloat(mView, "scaleY", 1, 1.5f));
	            break;
	        case 2:
	            AnimatorProxy.wrap(mView).setScaleX(1.5f);
	            AnimatorProxy.wrap(mView).setScaleY(1.5f);
	            anim.playTogether(ObjectAnimator.ofFloat(mView, "translationY",
	                    80f, 0f));
	            break;
	        case 3:
	        default:
	            AnimatorProxy.wrap(mView).setScaleX(1.5f);
	            AnimatorProxy.wrap(mView).setScaleY(1.5f);
	            anim.playTogether(ObjectAnimator.ofFloat(mView, "translationX", 0f,
	                    40f));
	            break;
	        }
	        anim.setDuration(3000);
	        anim.addListener(this);
	        anim.start();
	    }

	    @Override
	    public void onAnimationCancel(Animator arg0) {
	    }

	    @Override
	    public void onAnimationEnd(Animator animator) {
	        mContainer.removeView(mView);
	        mView = createNewView();
	        mContainer.addView(mView);
	        nextAnimation();
	    }

	    @Override
	    public void onAnimationRepeat(Animator arg0) {
	    }

	    @Override
	    public void onAnimationStart(Animator arg0) {
	    }

}