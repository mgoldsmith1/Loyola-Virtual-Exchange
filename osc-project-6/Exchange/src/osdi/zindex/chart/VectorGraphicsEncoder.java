package osdi.zindex.chart;

import osdi.vectorgraphics2d.Document;
import osdi.vectorgraphics2d.Processor;
import osdi.vectorgraphics2d.VectorGraphics2D;
import osdi.vectorgraphics2d.eps.EPSProcessor;
import osdi.vectorgraphics2d.intermediate.CommandSequence;
import osdi.vectorgraphics2d.pdf.PDFProcessor;
import osdi.vectorgraphics2d.svg.SVGProcessor;
import osdi.vectorgraphics2d.util.PageSize;
import osdi.zindex.chart.internal.chartpart.Chart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A helper class with static methods for saving Charts as vectors
 *
 * @author timmolter
 */
public final class VectorGraphicsEncoder {

  /** Constructor - Private constructor to prevent instantiation */
  private VectorGraphicsEncoder() {}

  public static void saveVectorGraphic(
      Chart chart, String fileName, VectorGraphicsFormat vectorGraphicsFormat) throws IOException {

    Processor p = null;

    switch (vectorGraphicsFormat) {
      case EPS:
        p= new EPSProcessor();
        break;
      case PDF:
        p= new PDFProcessor(true);
        break;
      case SVG:
        p= new SVGProcessor();
        break;

      default:
        break;
    }

    Graphics2D vg2d = new VectorGraphics2D();
//    vg2d.draw(new Rectangle2D.Double(0.0, 0.0, chart.getWidth(), chart.getHeight()));
    CommandSequence commands = ((VectorGraphics2D) vg2d).getCommands();

    chart.paint(vg2d, chart.getWidth(), chart.getHeight());

    // Write the vector graphic output to a file
    FileOutputStream file = new FileOutputStream(addFileExtension(fileName, vectorGraphicsFormat));

    try {
      PageSize pageSize = new PageSize(0.0, 0.0, chart.getWidth(), chart.getHeight());
      Document doc = p.getDocument(commands, pageSize);
      doc.writeTo(file);
    } finally {
      file.close();
    }
  }

  /**
   * Only adds the extension of the VectorGraphicsFormat to the filename if the filename doesn't
   * already have it.
   *
   * @param fileName
   * @param vectorGraphicsFormat
   * @return filename (if extension already exists), otherwise;: filename + "." + extension
   */
  public static String addFileExtension(
      String fileName, VectorGraphicsFormat vectorGraphicsFormat) {

    String fileNameWithFileExtension = fileName;
    final String newFileExtension = "." + vectorGraphicsFormat.toString().toLowerCase();
    if (fileName.length() <= newFileExtension.length()
        || !fileName
            .substring(fileName.length() - newFileExtension.length(), fileName.length())
            .equalsIgnoreCase(newFileExtension)) {
      fileNameWithFileExtension = fileName + newFileExtension;
    }
    return fileNameWithFileExtension;
  }

  public enum VectorGraphicsFormat {
    EPS,
    PDF,
    SVG
  }
}
