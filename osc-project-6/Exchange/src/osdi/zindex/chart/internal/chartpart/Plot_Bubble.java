package osdi.zindex.chart.internal.chartpart;

import osdi.zindex.chart.BubbleSeries;
import osdi.zindex.chart.style.BubbleStyler;

/** @author timmolter */
public class Plot_Bubble<ST extends BubbleStyler, S extends BubbleSeries>
    extends Plot_AxesChart<ST, S> {

  /**
   * Constructor
   *
   * @param chart
   */
  public Plot_Bubble(Chart<ST, S> chart) {

    super(chart);
    this.plotContent = new PlotContent_Bubble<ST, S>(chart);
  }
}
