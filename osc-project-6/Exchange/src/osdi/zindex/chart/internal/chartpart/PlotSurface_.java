package osdi.zindex.chart.internal.chartpart;

import java.awt.geom.Rectangle2D;

import osdi.zindex.chart.internal.series.Series;
import osdi.zindex.chart.style.Styler;

/** @author timmolter */
public abstract class PlotSurface_<ST extends Styler, S extends Series> implements ChartPart {

  final Chart<ST, S> chart;

  /**
   * Constructor
   *
   * @param chart
   */
  PlotSurface_(Chart<ST, S> chart) {

    this.chart = chart;
  }

  @Override
  public Rectangle2D getBounds() {

    return chart.getPlot().getBounds();
  }
}
