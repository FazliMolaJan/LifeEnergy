package cn.dennishucd.activity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import cn.dennishucd.R;
import cn.dennishucd.chart.BarChartBuilder;
import cn.dennishucd.chart.CubicLineChartBuilder;
import cn.dennishucd.chart.DialChartBuilder;
import cn.dennishucd.chart.LineChartBuilder;
import cn.dennishucd.chart.PieChartBuilder;
import cn.dennishucd.chart.RangeChartBuilder;
import cn.dennishucd.uibase.MyTextView;
import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Loader.ForceLoadContentObserver;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
/**
 * @author lqb
 *
 */
public class ChartBuilderActivity extends Activity {
	
	private GraphicalView mChartView;
	private Button mReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.energy_chart);
		mReturn = (Button) findViewById(R.id.chart_return);
		mReturn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}		
		});
	}
	
	/**
	 * 画图
	 */
	protected void onResume() {
		super.onResume();
		if (mChartView==null) {
			RelativeLayout layout=(RelativeLayout) findViewById(R.id.chart);
			mChartView =	getChartView();
			layout.addView(mChartView,new LayoutParams(
					LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		}else {
			mChartView.setBackgroundResource(R.id.chart);
			mChartView.repaint();
		}
	}

	private GraphicalView getChartView(){
		String chart = getIntent().getStringExtra("chart");
		GraphicalView view = null;
		if(chart.equals("bar")){
			view = new BarChartBuilder().execute(this);
		}
		else if(chart.equals("cubic")){
			view = new CubicLineChartBuilder().execute(this);
		}
		else if(chart.equals("dial")){
			view = new DialChartBuilder().execute(this);
		}
		else if(chart.equals("pie")){
			view = new PieChartBuilder().execute(this);
		}
		else if(chart.equals("range")){
			view = new RangeChartBuilder().execute(this);
		}
		else if(chart.equals("line")){
			view = new LineChartBuilder().execute(this);
		}
		return view;	
	}
}
