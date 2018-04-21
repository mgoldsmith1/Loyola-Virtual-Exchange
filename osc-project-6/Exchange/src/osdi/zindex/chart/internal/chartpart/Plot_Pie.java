package osdi.zindex.chart.internal.chartpart;

import osdi.zindex.chart.PieSeries;
import osdi.zindex.chart.style.PieStyler;

/** @author timmolter */
public class Plot_Pie<ST extends PieStyler, S extends PieSeries> extends Plot_Circular<ST, S> {

  /**
   * Constructor
   *
   * @param chart
   */
  public Plot_Pie(Chart<ST, S> chart) {

    super(chart);
  }

  protected void initContentAndSurface(Chart<ST, S> chart) {

    this.plotContent = new PlotContent_Pie<ST, S>(chart);
    this.plotSurface = new PlotSurface_Pie<ST, S>(chart);
  }
}
