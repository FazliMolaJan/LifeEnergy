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
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.DialRenderer.Type;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Budget demo pie chart.
 */
public class DialChartBuilder extends AbstractChart {
  /**
   * Returns the chart name.
   * @return the chart name
   */
  public String getName() {
    return "能量值详情";
  }
  
  /**
   * Returns the chart description.
   * @return the chart description
   */
  public String getDesc() {
    return "当天的能量值";
  }
  
  /**
   * Executes the chart demo.
   * @param context the context
   * @return the built intent
   */
  public GraphicalView execute(Context context) {
    CategorySeries category = new CategorySeries("");
    category.add("当前值", 8500);
    category.add("最小值", 6500);
    category.add("最大值", 11500);
    DialRenderer renderer = new DialRenderer();
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[] {20, 30, 15, 0});
    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
    r.setColor(Color.parseColor("#00AAAA"));
    renderer.addSeriesRenderer(r);
    r = new SimpleSeriesRenderer();
    r.setColor(Color.parseColor("#B0C4DE"));
    renderer.addSeriesRenderer(r);
    r = new SimpleSeriesRenderer();
    r.setColor(Color.parseColor("#B0C4DE"));
    renderer.addSeriesRenderer(r);
    renderer.setLabelsTextSize(10);
    renderer.setLabelsColor(Color.WHITE);
    renderer.setShowLabels(true);
    renderer.setVisualTypes(new DialRenderer.Type[] {Type.ARROW, Type.NEEDLE, Type.NEEDLE});
    renderer.setMinValue(0);
    renderer.setMaxValue(15000);
    return ChartFactory.getDialChartView(context, category, renderer);
  }

}
