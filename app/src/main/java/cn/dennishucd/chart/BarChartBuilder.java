/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.dennishucd.chart;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Sales demo bar chart.
 */
public class BarChartBuilder extends AbstractChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "好友比拼";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "好友们的能量比拼";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public GraphicalView execute(Context context) {
    String[] titles = new String[] { "任东卫", "郭子涵","谢以荷" };
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 14230, 12300, 14240, 15244,12600,10200,7090 });
    values.add(new double[] { 5230, 7300, 9240, 10540, 7900,9300,9320});
    values.add(new double[] { 5630, 6420, 4240, 5540, 9900,7060,13040});
    int[] colors = new int[] { Color.parseColor("#00AAAA"),Color.parseColor("#B0E0E6"),Color.parseColor("#B0C4DE") };
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    setChartSettings(renderer, "好友们最近一周的能量情况", "日期", "能量值", 0.5,
        7.5, 0, 17000, Color.GRAY, Color.LTGRAY);
    renderer.setMarginsColor(Color.parseColor("#22ffffff"));
    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
    renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
    renderer.setXLabels(7);
    renderer.setYLabels(10);
    renderer.setXLabelsAlign(Align.LEFT);
    renderer.setYLabelsAlign(Align.LEFT);
    renderer.setXLabelsColor(Color.parseColor("#000000"));
    renderer.setPanEnabled(true, false);
    renderer.setMargins(new int[] {10, 10, 10, 0});
    // renderer.setZoomEnabled(false);
    renderer.setZoomRate(1.1f);
    renderer.setBarSpacing(0.5f);
    renderer.setBackgroundColor(0x00ffffff); 
    renderer.setApplyBackgroundColor(true);
    return ChartFactory.getBarChartView(context, buildBarDataset(titles, values), renderer,
        Type.STACKED);
  }

}
