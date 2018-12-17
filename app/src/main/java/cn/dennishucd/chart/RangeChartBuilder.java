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

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Temperature demo range chart.
 */
public class RangeChartBuilder extends AbstractChart {

  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "能量浮动";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "能量变化范围详情";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public GraphicalView execute(Context context) {
    double[] minValues = new double[] { 4300, 3500, 5300, 4900, 3800, 2900, };
    double[] maxValues = new double[] { 8000,7000, 9700, 8600, 7000, 6500};

    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    RangeCategorySeries series = new RangeCategorySeries("波动情况");
    int length = minValues.length;
    for (int k = 0; k < length; k++) {
      series.add(minValues[k], maxValues[k]);
    }
    dataset.addSeries(series.toXYSeries());
    int[] colors = new int[] { Color.CYAN };
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    setChartSettings(renderer, "好友们最近一周的能量波动范围", "用户", "能量值", 0.5, 6.5,
        2000, 12000, Color.GRAY, Color.LTGRAY);
    renderer.setBarSpacing(1);
    renderer.setXLabels(0);
    renderer.setYLabels(10);
    renderer.addXTextLabel(1, "任东卫");
    renderer.addXTextLabel(2, "罗智宜");
    renderer.addXTextLabel(3, "郭子涵");
    renderer.addXTextLabel(4, "谢以荷");
    renderer.addXTextLabel(5, "赵一援");
    renderer.addXTextLabel(6, "萧之雁");
    renderer.addYTextLabel(3000, "心情差");
    renderer.addYTextLabel(4500, "心情有点差");
    renderer.addYTextLabel(6000, "心情一般");
    renderer.addYTextLabel(8500, "心情棒");
    renderer.setMargins(new int[] {30, 70, 30, 70});
    renderer.setYLabelsAlign(Align.RIGHT);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setDisplayChartValues(true);
    r.setChartValuesTextSize(12);
    r.setChartValuesSpacing(3);
    r.setGradientEnabled(true);
    r.setGradientStart(4000, Color.parseColor("#B0C4DE"));
    r.setGradientStop(8000, Color.parseColor("#00AAAA"));
    return ChartFactory.getRangeBarChartView(context, dataset, renderer, Type.DEFAULT);
  }

}
