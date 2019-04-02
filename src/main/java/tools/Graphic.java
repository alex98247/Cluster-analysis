package tools;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graphic extends JFrame {

    private XYDataset dataset;

    public Graphic(String title) {
        super(title);

        // Create dataset
        dataset = new XYSeriesCollection();

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Diagram",
                "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, false, false, false);


        //Changes background color
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

    }

    public void setData(Point2D[] data, Point2D[] clusterCenters) {

        XYSeries series1 = new XYSeries("Points");
        Arrays.stream(data).forEach(x -> series1.add(x.getX(), x.getY()));
        ((XYSeriesCollection) dataset).addSeries(series1);

        XYSeries series2 = new XYSeries("DataCentres");
        Arrays.stream(clusterCenters).forEach(x -> series2.add(x.getX(), x.getY()));
        ((XYSeriesCollection) dataset).addSeries(series2);
    }
}
