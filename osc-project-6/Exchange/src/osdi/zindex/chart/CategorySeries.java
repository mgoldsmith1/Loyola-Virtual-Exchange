package osdi.zindex.chart;

import java.util.List;

import osdi.zindex.chart.internal.chartpart.RenderableSeries;
import osdi.zindex.chart.internal.chartpart.RenderableSeries.LegendRenderType;
import osdi.zindex.chart.internal.series.AxesChartSeriesCategory;
import osdi.zindex.chart.internal.series.Series;

/** A Series containing category data to be plotted on a Chart */
public class CategorySeries extends AxesChartSeriesCategory {

  private CategorySeriesRenderStyle chartCategorySeriesRenderStyle = null;

  /**
   * Constructor
   *
   * @param name
   * @param xData
   * @param yData
   * @param errorBars
   * @param axisType
   */
  public CategorySeries(
      String name,
      List<?> xData,
      List<? extends Number> yData,
      List<? extends Number> errorBars,
      Series.DataType axisType) {

    super(name, xData, yData, errorBars, axisType);
  }

  public CategorySeriesRenderStyle getChartCategorySeriesRenderStyle() {

    return chartCategorySeriesRenderStyle;
  }

  public CategorySeries setChartCategorySeriesRenderStyle(
      CategorySeriesRenderStyle categorySeriesRenderStyle) {

    this.chartCategorySeriesRenderStyle = categorySeriesRenderStyle;
    return this;
  }

  @Override
  public LegendRenderType getLegendRenderType() {

    return chartCategorySeriesRenderStyle.getLegendRenderType();
  }

  public enum CategorySeriesRenderStyle implements RenderableSeries {
    Line(LegendRenderType.Line),

    Area(LegendRenderType.Line),

    Scatter(LegendRenderType.Scatter),

    SteppedBar(LegendRenderType.Box),

    Bar(LegendRenderType.BoxNoOutline),

    Stick(LegendRenderType.Line);

    private final LegendRenderType legendRenderType;

    CategorySeriesRenderStyle(LegendRenderType legendRenderType) {

      this.legendRenderType = legendRenderType;
    }

    @Override
    public LegendRenderType getLegendRenderType() {

      return legendRenderType;
    }
  }
}
