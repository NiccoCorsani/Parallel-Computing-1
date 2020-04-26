package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class App {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Charts");

				frame.setSize(600, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

				XYDataset ds = null;
				try {
					ds = createDataset(null,"Parallelo");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JFreeChart chart = ChartFactory.createXYLineChart(" 4 threads c", "Dimension byte", "Time nano_seconds", ds, PlotOrientation.VERTICAL,
						true, true, false);

				ChartPanel cp = new ChartPanel(chart);

				frame.getContentPane().add(cp);
			}
		});

	}

	
	////era private 
	public static XYDataset createDataset(double[][] DimensioneFile_TempoDiProcessamento, String tipoProcessamento) throws IOException {

		DefaultXYDataset ds = new DefaultXYDataset();
		//ds.addSeries(tipoProcessamento, App.createDataSetFromFile("Parallelo Dim Velocità"));
		//ds.addSeries("Sequenziale", App.createDataSetFromFile("Sequenziale Dim Velocità"));
		ds.addSeries("Parallelo in c", App.createDataSetFromFile("/Users/nicche/Desktop/Progetti c/zzzz_09-03-2020/ParalleloC.txt"));
		return ds;
	}

	
	
	public static double[][] createDataSetFromFile(String file_name) throws IOException{
		

        
        int lines=0;
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        while(br.readLine()!= null) lines++;
        double[][] data = new double[2][lines];

        String ln;
        double time; 
        double dimension;
        int i=0;        
        BufferedReader br1 = new BufferedReader(new FileReader(file_name));

        while((ln=br1.readLine())!= null) {       	
        String ss[]=ln.split(" ");
        time=Double.valueOf(ss[0]);
        dimension=Double.valueOf(ss[1]);
        data[0][i]=dimension;
        data[1][i]=time;
        i++;
        }


		return data;
		
		
		
		
	}
	
	
	
	
}