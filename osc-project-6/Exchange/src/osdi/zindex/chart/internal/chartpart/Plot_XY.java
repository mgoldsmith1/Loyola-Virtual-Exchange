package osdi.zindex.chart.internal.chartpart;

import osdi.zindex.chart.XYSeries;
import osdi.zindex.chart.style.AxesChartStyler;

/** @author timmolter */
public class Plot_XY<ST extends AxesChartStyler, S extends XYSeries> extends Plot_AxesChart<ST, S> {

  /**
   * Constructor
   *
   * @param chart
   */
  public Plot_XY(Chart<ST, S> chart) {

    super(chart);
    this.plotContent = new PlotContent_XY<ST, S>(chart);
  }
}
