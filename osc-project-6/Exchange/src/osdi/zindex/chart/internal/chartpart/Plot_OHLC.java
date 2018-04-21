package osdi.zindex.chart.internal.chartpart;

import osdi.zindex.chart.OHLCSeries;
import osdi.zindex.chart.style.AxesChartStyler;

/** @author arthurmcgibbon */
public class Plot_OHLC<ST extends AxesChartStyler, S extends OHLCSeries>
    extends Plot_AxesChart<ST, S> {

  /**
   * Constructor
   *
   * @param chart
   */
  public Plot_OHLC(Chart<ST, S> chart) {

    super(chart);
    this.plotContent = new PlotContent_OHLC<ST, S>(chart);
  }
}
