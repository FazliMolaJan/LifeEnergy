package cn.dennishucd.menu;

import java.util.ArrayList;
import java.util.List;

import cn.dennishucd.R;
import cn.dennishucd.activity.ChartBuilderActivity;
import cn.dennishucd.activity.LoginActivity;
import cn.dennishucd.activity.SexangleActivity;
import cn.dennishucd.cache.LifeApplication;
import cn.dennishucd.result.CornerCellResult;
import cn.dennishucd.uibase.CornerRowLayout;
import cn.dennishucd.uibase.OnRowClickListener;
import cn.dennishucd.uibase.FlipperLayout.OnOpenListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/**
 * 能量首页类
 * 
 * @author lqb
 * 
 */
public class Energy {
	private Context mContext;
	private LifeApplication mKXApplication;
	private View mEnergy;
	private Button mReturn;
	private CornerRowLayout mCornerx;
	private CornerRowLayout mCornery;
	private CornerRowLayout mCornerz;
	private OnOpenListener mOnOpenListener;

	public Energy(Context context, LifeApplication application) {
		mContext = context;
		mKXApplication = application;
		mEnergy = LayoutInflater.from(context).inflate(R.layout.energy, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
        
		mReturn = (Button) mEnergy.findViewById(R.id.energy_return);
		mCornerx = (CornerRowLayout)mEnergy.findViewById(R.id.CornerLayoutx);
		mCornery = (CornerRowLayout)mEnergy.findViewById(R.id.CornerLayouty);
		mCornerz = (CornerRowLayout)mEnergy.findViewById(R.id.CornerLayoutz);
	}

	private void setListener() {
		
		mReturn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
			
		});
		mCornerx.setOnRowClickListener(new OnRowClickListener(){

			@Override
			public void onRowClick(View v, int index) {
				// TODO Auto-generated method stub
				if(index ==0){
					Intent intent = new Intent();
					intent.setClass(mContext,ChartBuilderActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("chart", "dial");
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
				else if(index == 1){
					Intent intent = new Intent();
					intent.setClass(mContext,ChartBuilderActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("chart", "line");
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
				else if(index == 2){
					Intent intent = new Intent();
					intent.setClass(mContext,ChartBuilderActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("chart", "pie");
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
			}
		});
		
		mCornery.setOnRowClickListener(new OnRowClickListener(){

			@Override
			public void onRowClick(View v, int index) {
				// TODO Auto-generated method stub
				if(index ==0){
					Intent intent = new Intent();
					intent.setClass(mContext,ChartBuilderActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("chart", "bar");
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
				else if(index == 1){
					Intent intent = new Intent();
					intent.setClass(mContext,ChartBuilderActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("chart", "cubic");
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
				else if(index == 2){
					Intent intent = new Intent();
					intent.setClass(mContext,ChartBuilderActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("chart", "range");
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
			}
		});
		
		mCornerz.setOnRowClickListener(new OnRowClickListener(){

			@Override
			public void onRowClick(View v, int index) {
				// TODO Auto-generated method stub
				if(index ==0){
					Intent intent = new Intent();
					intent.setClass(mContext,SexangleActivity.class);
					mContext.startActivity(intent);
				}
			}
		});
	}

	private void init() {
		List<CornerCellResult> cells = new ArrayList<CornerCellResult>();
		cells.add(new CornerCellResult("总值", "积累的能量值", true));
		cells.add(new CornerCellResult("变化", "能量变化情况", true));	
		cells.add(new CornerCellResult("份额", "所占能量比重", true));	
		mCornerx.setCellList(cells);	
		mCornerx.setHeader("个人");
		mCornerx.setFooter("阳光");
	
		
		List<CornerCellResult> cellsx = new ArrayList<CornerCellResult>();
		cellsx.add(new CornerCellResult("分布", "好友能量分布", true));
		cellsx.add(new CornerCellResult("增长", "能量增长情况", true));
		cellsx.add(new CornerCellResult("波动", "好友能量波动", true));
		mCornery.setCellList(cellsx);
		mCornery.setHeader("好友");
		mCornery.setFooter("比拼");
		
		List<CornerCellResult> cellsz = new ArrayList<CornerCellResult>();
		cellsz.add(new CornerCellResult("特效", "视频酷炫效果", true));
		cellsz.add(new CornerCellResult("采访", "小草根变明星", true));
		mCornerz.setCellList(cellsz);	
		mCornerz.setHeader("积分");
		mCornerz.setFooter("兑换");
	}
	
	public View getView() {
		return mEnergy;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
	

}
