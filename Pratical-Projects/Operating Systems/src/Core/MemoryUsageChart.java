package Core;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

/**
 * The MemoryUsageChart class displays a line chart of memory usage data over time
 * in a graphical user interface window. It extends JFrame to provide the visual
 * presentation of the chart using the JFreeChart library.
 *
 * The chart visualizes memory usage data passed to the constructor and is displayed
 * as a category dataset with "Time" on the x-axis and "Memory Usage (MB)" on the y-axis.
 */
public class MemoryUsageChart extends JFrame {

    /**
     * Constructs a MemoryUsageChart instance that displays a line chart representing
     * memory usage data over time within a graphical user interface window.
     *
     * @param title            The title of the chart window.
     * @param memoryUsageData  A list of integers representing memory usage data
     *                         points, where each integer corresponds to memory
     *                         usage at a specific point in time.
     */
    public MemoryUsageChart(String title, List<Integer> memoryUsageData) {
        super(title);

        DefaultCategoryDataset dataset = createDataset(memoryUsageData);
        JFreeChart chart = ChartFactory.createLineChart(
                "Memory Usage Over Time",
                "Time",
                "Memory Usage (MB)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    /**
     * Creates a category dataset for visualizing memory usage data over time.
     * The dataset contains data points where each value represents memory usage
     * at a specific point in time.
     *
     * @param memoryUsageData A list of integers representing memory usage data.
     *                        Each integer corresponds to memory usage at a time
     *                        step, indexed by order in the list.
     * @return A DefaultCategoryDataset containing the memory usage data,
     *         with "Memory Usage" as the series name and the index of the list
     *         as the category axis labels.
     */
    private DefaultCategoryDataset createDataset(List<Integer> memoryUsageData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < memoryUsageData.size(); i++) {
            dataset.addValue(memoryUsageData.get(i), "Memory Usage", Integer.toString(i));
        }
        return dataset;
    }
}