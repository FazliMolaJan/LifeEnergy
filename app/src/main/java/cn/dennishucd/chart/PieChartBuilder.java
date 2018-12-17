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
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Budget demo pie chart.
 */
public class PieChartBuilder extends AbstractChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "能量股份";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "当前的能量值比重";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public GraphicalView execute(Context context) {
    double[] values = new double[] { 12000, 14000, 11000, 10000, 19000 };
    int[] colors = new int[] { Color.parseColor("#00AAAA"),Color.parseColor("#B0E0E6"),Color.parseColor("#B0C4DE"), Color.parseColor("#00AA88"), Color.parseColor("#007799") };
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitle("能量百分比");
    renderer.setChartTitleTextSize(14);
    renderer.setDisplayValues(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, Color.parseColor("#B0C4DE"));
    r.setGradientStop(0, Color.parseColor("#00AAAA"));
    r.setHighlighted(true);
    GraphicalView view = ChartFactory.getPieChartView(context,
        buildCategoryDataset("能量百分比", values), renderer);
    return view;
  }

}
