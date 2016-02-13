package antcalc.views;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;
import java.util.List;

public class CoolChartFrame {
        private static List<Float> xData;// = new ArrayList<Float>();
        private static List<Float> yData;// = new ArrayList<Float>();

        private static void setxData(float[] X) {
            xData = new ArrayList<>();
            for (int a = 1; a < X.length - 1; a++) {
                xData.add(X[a]);
            }
        }

        private static void setyData(float[] Y) {
            yData = new ArrayList<>();
            for (int a = 1; a < Y.length - 1; a++) {
                if (Y[a] > 0) {
                    yData.add(Y[a]);
                } else {
                    yData.add(1f);
                }
            }
        }

        public static JFreeChart createChart(String Name, float[] X, float[] Y) {
            setxData(X);
            setyData(Y);
            XYSeries series = new XYSeries(Name);
            XYSeriesCollection dataSet = new XYSeriesCollection(series);
            for (int i = 0; i < xData.size(); i++) {
                series.add(xData.get(i), yData.get(i));
            }
            JFreeChart chart = ChartFactory.createXYLineChart(Name, "Градусы", "Значение", dataSet, PlotOrientation.VERTICAL, false, true, false);
            return chart;
        }

}
