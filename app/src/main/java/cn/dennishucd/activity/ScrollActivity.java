package cn.dennishucd.activity;

import cn.dennishucd.R;
import cn.dennishucd.anim.RotateScrollAnimation;
import cn.dennishucd.anim.RotateScrollAnimation.InterpolatedTimeListener;
import cn.dennishucd.uibase.MyScrollView;
import cn.dennishucd.uibase.MyScrollView.onTurnListener;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ScrollActivity extends Activity implements onTurnListener,
		InterpolatedTimeListener {
	MyScrollView myScrollView;
	ListView lv_chat;
	ImageView imageView;
	ImageView imageicon;
	View line;


	private int current_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa);
		setTitle("jjhappyforever...");
		imageView = (ImageView) findViewById(R.id.image);
		imageicon = (ImageView) findViewById(R.id.imageview1);
		myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
		line = (View) findViewById(R.id.line);
		myScrollView.setTurnListener(this);
		myScrollView.setImageView(imageView);
		myScrollView.setLine(line);

	}

	@Override
	public void onTurn() {
		RotateScrollAnimation animation = new RotateScrollAnimation();
		animation.setFillAfter(true);
		animation.setInterpolatedTimeListener(this);
		imageicon.startAnimation(animation);

		//current_id = current_id < drawable_id.length - 1 ? ++current_id : 0;

	}

	@Override
	public void interpolatedTime(float interpolatedTime) {
		// 监听到翻转进度过半时，更新图片内容
		if (interpolatedTime > 0.5f) {
			//Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				//	drawable_id[current_id]);
			//imageicon.setImageBitmap(bitmap);
		}

	}

}
