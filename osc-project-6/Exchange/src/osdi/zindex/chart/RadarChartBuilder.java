package osdi.zindex.chart;

import osdi.zindex.chart.internal.ChartBuilder;

/** @author timmolter */
public class RadarChartBuilder extends ChartBuilder<RadarChartBuilder, RadarChart> {

  public RadarChartBuilder() {}

  @Override
  public RadarChart build() {

    return new RadarChart(this);
  }
}
