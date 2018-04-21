package osdi.zindex.chart.style;

import java.awt.*;

import osdi.zindex.chart.style.colors.ChartColor;
import osdi.zindex.chart.style.colors.XChartSeriesColors;
import osdi.zindex.chart.style.lines.XChartSeriesLines;
import osdi.zindex.chart.style.markers.Marker;
import osdi.zindex.chart.style.markers.XChartSeriesMarkers;

/** @author timmolter */
public class XChartTheme extends AbstractBaseTheme {

  // Chart Style ///////////////////////////////

  @Override
  public Color getChartBackgroundColor() {

    return ChartColor.getAWTColor(ChartColor.GREY);
  }

  // SeriesMarkers, SeriesLines, SeriesColors ///////////////////////////////

  @Override
  public Color[] getSeriesColors() {

    return new XChartSeriesColors().getSeriesColors();
  }

  @Override
  public Marker[] getSeriesMarkers() {

    return new XChartSeriesMarkers().getSeriesMarkers();
  }

  @Override
  public BasicStroke[] getSeriesLines() {

    return new XChartSeriesLines().getSeriesLines();
  }

  // Chart Title ///////////////////////////////

  @Override
  public boolean isChartTitleBoxVisible() {

    return false;
  }

  @Override
  public Color getChartTitleBoxBackgroundColor() {

    return ChartColor.getAWTColor(ChartColor.GREY);
  }

  @Override
  public Color getChartTitleBoxBorderColor() {

    return ChartColor.getAWTColor(ChartColor.GREY);
  }

  // Chart Legend ///////////////////////////////

  // Chart Axes ///////////////////////////////

  // Chart Plot Area ///////////////////////////////

  @Override
  public boolean isPlotTicksMarksVisible() {

    return false;
  }

  // Tool Tips ///////////////////////////////

  // Category Charts ///////////////////////////////

  // Pie Charts ///////////////////////////////

  // Line, Scatter, Area Charts ///////////////////////////////

  // Error Bars ///////////////////////////////

  // Annotations ///////////////////////////////

}
