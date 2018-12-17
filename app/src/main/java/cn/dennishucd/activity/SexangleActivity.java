package cn.dennishucd.activity;

import cn.dennishucd.R;
import cn.dennishucd.uibase.SexangleImageViews;
import cn.dennishucd.uibase.SexangleImageViews.OnSexangleImageClickListener;
import cn.dennishucd.uibase.SexangleViewGroup;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SexangleActivity extends Activity implements OnClickListener {
	
	private SexangleViewGroup sexangleViewGroup;
	SexangleImageViews imageViews;
	private static final int ID=0x10000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sexangleViewGroup = (SexangleViewGroup) findViewById(R.id.sexangleView);
		//findViewById(R.id.btn_add).setOnClickListener(this);
		//findViewById(R.id.btn_remove).setOnClickListener(this);
		initView();
	}
	
	public void initView(){
		for(int i=0;i<7;i++){
			imageViews=new SexangleImageViews(this, i);
			imageViews.setId(ID+i);
			imageViews.setOnSexangleImageClick(listener);
			sexangleViewGroup.addView(imageViews);
		}
	}
	
	OnSexangleImageClickListener listener=new OnSexangleImageClickListener() {
		
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case ID:
				Toast.makeText(SexangleActivity.this,imageViews.setName(0), Toast.LENGTH_SHORT).show();
				break;
			case ID+1:
				Toast.makeText(SexangleActivity.this,imageViews.setName(1), Toast.LENGTH_SHORT).show();
				break;
			case ID+2:
				Toast.makeText(SexangleActivity.this,imageViews.setName(2), Toast.LENGTH_SHORT).show();
				break;
			case ID+3:
				Toast.makeText(SexangleActivity.this,imageViews.setName(3), Toast.LENGTH_SHORT).show();
				break;
			case ID+4:
				Toast.makeText(SexangleActivity.this,imageViews.setName(4), Toast.LENGTH_SHORT).show();
				break;
			case ID+5:
				Toast.makeText(SexangleActivity.this,imageViews.setName(5), Toast.LENGTH_SHORT).show();
				break;
			case ID+6:
				Toast.makeText(SexangleActivity.this,imageViews.setName(6), Toast.LENGTH_SHORT).show();
				break;
			}
			
		}
	};
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	/*int i=0;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			if(i<6){
				i++;
			}else{
				i=0;
			}
			//SexangleImageView view = new SexangleImageView(getApplicationContext());
			viewBean=new ViewBean();
			viewBean.setHome(i);
			viewBean.setColor(i);
			viewBean.setTexts(setName(i));
			viewBean.setTextsize(20);
			imageViews=new SexangleImageViews(this, viewBean);
			imageViews.setId(ID+i);
			imageViews.setOnSexangleImageClick(listener);
			sexangleViewGroup.addView(imageViews);
			//sexangleViewGroup.addView(view);
			
			break;

		case R.id.btn_remove:
			if(sexangleViewGroup.getChildCount()>0)
				sexangleViewGroup.removeViewAt(sexangleViewGroup.getChildCount() - 1);
			break;
		}
		
	}
	
	*/

}
