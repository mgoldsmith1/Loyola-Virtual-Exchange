/**
 * 
 */
package osdi.loyola.index;

import java.text.DecimalFormat;

/**
 * @author Mattata
 *
 */

import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingWorker;

import osdi.collections.OrderQueue;
import osdi.locks.Semaphore;
import osdi.locks.SpinLock;
import osdi.loyola.index.QuickChart;
import osdi.zindex.chart.SwingWrapper;
import osdi.zindex.chart.XYChart;

/** Creates a real-time chart using SwingWorker 
 * @param <T>*/
public class LoyolaRamblerIndex  {

  MySwingWorker mySwingWorker;
  SwingWrapper<XYChart> sw;
  XYChart chart;
  private DecimalFormat df = new DecimalFormat("0.00");
 // private final int bufferSize;

  private double[] array;
  
  public LoyolaRamblerIndex(){
	  //this.bufferSize = bufferSize;
    
  }
 

  public static void main(String[] args) throws Exception {

    LoyolaRamblerIndex loyolaRamblerIndexRealTimeWorker = new LoyolaRamblerIndex();
    loyolaRamblerIndexRealTimeWorker.go();
  }

  public void go() {

    // Create Chart
    chart =
        QuickChart.getChart(
            "Loyola Rambler Index Futures (Real-time)",
            "Time",
            "Price",
            "randomWalk",
            new double[] {0},
            new double[] {0});
    chart.getStyler().setLegendVisible(false);
    chart.getStyler().setXAxisTicksVisible(false);
   
    // Show it
    sw = new SwingWrapper<XYChart>(chart);
    sw.displayChart();
   
    mySwingWorker = new MySwingWorker();
    mySwingWorker.execute();
  
  }
  public void stopWithCancel(){
	  mySwingWorker.cancel(true);
  }

  private class MySwingWorker extends SwingWorker<Boolean, double[]> {

    final LinkedList<Double> fifo = new LinkedList<Double>();
    
    //00, .25, .50, .75, 1.00
    public double quarterRound(double v){
    	   return Math.floor(v*4)/4;
    }
    public MySwingWorker() {

      //random price added
    	
      fifo.add(2000.25);
    }
    
    @Override
    protected Boolean doInBackground() throws Exception {

      while (!isCancelled()) {

    	//random price added
        fifo.add(fifo.get(fifo.size() - 1) + Math.random() - .5);
        if (fifo.size() > 500) {
          fifo.removeFirst();
        }
       
        array = new double[fifo.size()];
        for (int i = 0; i < fifo.size(); i++) {
          //array[i] = fifo.get(i);
          array[i] = Double.valueOf(df.format(fifo.get(i)));
          array[i] = quarterRound(array[i]);
          //String angleFormated = df.format(array[i]);
        }
        publish(array);

        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
          // eat it. caught when interrupt is called
          System.out.println("MySwingWorker shut down.");
        }
      }

      return true;
    }

    @Override
    protected void process(List<double[]> chunks)  {
     
      System.out.println("quote: " + array[0] );
     // chart.setYAxisTitle("Quote: " + array[0]);
      System.out.println("number of chunks: " + chunks.size());
      //MathUtils.SMA mer = new MathUtils.SMA(array.length);
       
      double[] mostRecentDataSet = chunks.get(chunks.size() - 1);

      chart.updateXYSeries("randomWalk", null, mostRecentDataSet, null);
      //double smaLRF = mer.compute(array[0]);
      //System.out.println("sma quote: " + array[0] );
     // chart.addSeries("randomWalk", smaLRF);
      sw.repaintChart();

      long start = System.currentTimeMillis();
      long duration = System.currentTimeMillis() - start;
      try {
        Thread.sleep(40 - duration); // 40 ms ==> 25fps
        // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
      } catch (InterruptedException e) {
        System.out.println("InterruptedException occurred.");
      }
    }
  }


}