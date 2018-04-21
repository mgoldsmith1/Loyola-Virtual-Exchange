package osdi.zindex.chart.internal.chartpart;

/** @author timmolter */
public interface RenderableSeries {

  LegendRenderType getLegendRenderType();

  enum LegendRenderType {
    Line,
    Scatter,
    Box,
    BoxNoOutline
  }
}
