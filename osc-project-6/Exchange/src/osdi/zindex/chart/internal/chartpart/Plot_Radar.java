package osdi.zindex.chart.internal.chartpart;

import osdi.zindex.chart.RadarSeries;
import osdi.zindex.chart.style.RadarStyler;

public class Plot_Radar<ST extends RadarStyler, S extends RadarSeries>
    extends Plot_Circular<ST, S> {

  /**
   * Constructor
   *
   * @param chart
   */
  public Plot_Radar(Chart<ST, S> chart) {

    super(chart);
  }

  @Override
  protected void initContentAndSurface(Chart<ST, S> chart) {

    this.plotContent = new PlotContent_Radar<ST, S>(chart);
    this.plotSurface = new PlotSurface_Pie<ST, S>(chart);
  }
}
