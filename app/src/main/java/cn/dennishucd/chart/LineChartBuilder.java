package cn.dennishucd.chart;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import cn.dennishucd.R;
import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Loader.ForceLoadContentObserver;
import android.graphics.Color;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
/**
 * @author lqb
 *
 */
public class LineChartBuilder {
	
	private XYMultipleSeriesDataset mDataset =new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer mRenderer =new XYMultipleSeriesRenderer();
	private XYSeries mCurrentSeries;
	private XYSeriesRenderer mCurrentRenderer;
	private String mDataFormatString;
	private GraphicalView mChartView;

/**
 * 拆线点的坐标
 * @return
 */
	private int[][] getValues(){
		int [][] xyValues={{ 0, 8800 }, { 1, 7900 }, { 2, 7500 }, { 3, 7100 },    
				{ 4, 9000 }, { 5, 8800 }, { 6, 6600 }, { 7, 6500 }, { 8, 5000 },    
				{ 9, 7800 }, { 10, 6700 }, { 11, 7700 }, { 12, 7900 }, { 13, 8500 },    
				{ 14, 8900 }, { 15, 9300 }, { 16, 9500 }, { 17, 9900 }, { 18, 6600 },    
				{ 19, 5500 }, { 20, 4400 },{24,3000}};
		return xyValues;
	}
	public GraphicalView execute(Context context) {
		String seriesTitle="日期";
		XYSeries series =new XYSeries(seriesTitle);
		mDataset.addSeries(series);
		mCurrentSeries=series;
		XYSeriesRenderer seriesRenderer=new XYSeriesRenderer();
		
		seriesRenderer.setColor(Color.argb(0, 0xff, 0, 0));
		seriesRenderer.setFillBelowLine(true);
		seriesRenderer.setFillBelowLineColor(Color.parseColor("#aa668800"));
		seriesRenderer.setFillPoints(true);
		seriesRenderer.setPointStyle(PointStyle.POINT);
		seriesRenderer.setLineWidth(3.0f);
		
		mRenderer.setMarginsColor(Color.argb(0, 0xff, 0, 0));
		mRenderer.addSeriesRenderer(seriesRenderer);
		mRenderer.setYAxisMin(0d);
		mRenderer.setYAxisMax(12000d);
		mRenderer.setXAxisMax(24d);
		mRenderer.setShowGrid(true);
		mRenderer.setXLabels(24);
		mRenderer.setChartTitle("一周的能量变化图");
		mCurrentRenderer=seriesRenderer;
		
		double x=0;
		double y=0;
		int [] [] xyValues=getValues();
		for (int i = 0; i < xyValues.length; i++) {
			x=xyValues[i][0];
			y=xyValues[i][1];
			mCurrentSeries.add(x, y);
		}
		mChartView=ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return mChartView;
	}
}
