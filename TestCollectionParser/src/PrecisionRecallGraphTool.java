import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class PrecisionRecallGraphTool extends Application {

	public static String queryResultsFile = "C:\\a_med\\query_relevantdocs\\queryRelDocs.txt";
	
	public static List<Query> queries = new ArrayList<Query>();
	
	public static int getQueryNum = 0;

	
	List<Integer> records;
	List<String> legends;
	
	public void init(){
		System.out.println("hello");
		// load the csv file		
		
	}
	
    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        //final NumberAxis xAxis = new NumberAxis();
        final NumberAxis xAxis = new NumberAxis(1, 30, 1);
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Query number");
        //xAxis.set
        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Precision Recall Graph");
        //defining a series
        XYChart.Series precision = new XYChart.Series();
        XYChart.Series recall = new XYChart.Series();
        precision.setName("Precision");
        recall.setName("Recall");
        //populating the series with data
        
        int i = 1;
        for(Query query : queries){
        	precision.getData().add(new XYChart.Data(i, query.getPrecision()));
        	recall.getData().add(new XYChart.Data(i, query.getRecall()));
        	++i;
        }
        
        
        Scene scene  = new Scene(lineChart,800,500);
        lineChart.getData().add(precision);
        lineChart.getData().add(recall);
        //lineChart.setCreateSymbols(false);
       
        stage.setScene(scene);
        stage.show();
    }
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		readQueryResultsFile();
		
		for (int j = getQueryNum;j<30;++j){		
		
	        try {
	            // http://localhost:8080/searchTool/bm25rankedsearch
	            URL url = new URL("http://localhost:8080/searchTool/bm25rankedsearch");
	            URLConnection conn = url.openConnection();
	            conn.setDoOutput(true);
	            
	            BufferedWriter out = 
	                new BufferedWriter( new OutputStreamWriter( conn.getOutputStream() ) );
	            //out.write("searchterms=shipment gold\r\n");
	            
	            String thisQuery = queries.get(j).getQueryString();
	            //System.out.println(thisQuery);
	            out.write("searchterms=" + thisQuery+"\r\n");
	            //out.write("&numResults=10\r\n");
	            // hereditary implications of prolonged neonatal obstructive jaundice
	            out.flush();
	            out.close();
	            
	            
	            BufferedReader in = 
	                new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
	            
	            
	            List<Integer> docsFound = new ArrayList<Integer>();
	            String response;
	            while ( (response = in.readLine()) != null ) {
	            	//System.out.println(response);
	            	if(response.contains("found")){
	            		docsFound.add(Integer.parseInt(response.substring(response.indexOf("found")+6, response.indexOf("."))));
	            	}
	            }
	            in.close();            
	                 
	         int numMatching = 0;
	         for(Integer x : queries.get(j).getRelevantDocs()){
	        	 if(docsFound.contains(x)){
	        		 numMatching++;
	        	 }
	         }       
	         System.out.println();
	         System.out.println("Query # = " + (j+1));
	         System.out.println("Query = " + queries.get(j).getQueryString());
	         for(Integer d : queries.get(j).getRelevantDocs()){
	        	 System.out.print(d + " ");
	         }
	         System.out.println();
	         System.out.println("matches   = " + numMatching);
	         double precision = (double)numMatching/(double)docsFound.size();
	         queries.get(j).setPrecision(precision);
	         System.out.println("Precision = "+ precision);
	         double recall = (double)numMatching/(double)queries.get(j).getRelevantDocs().size();
	         queries.get(j).setRecall(recall);
	         System.out.println("Recall    = " + recall);
	         System.out.println();
	        }
	        catch ( MalformedURLException ex ) {
	            // a real program would need to handle this exception
	        }
	        catch ( IOException ex ) {
	            // a real program would need to handle this exception
	        }
		}
		launch(args);
    }

	
	
	
	public static void readQueryResultsFile() throws Exception{
		File file = new File(queryResultsFile);
		FileInputStream inputStream = new FileInputStream(file);
		InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(streamReader);
		
		reader.readLine();
		Query q = new Query();
		q.setQueryNum(1);
		
		
		for (String line;(line = reader.readLine()) != null;) {
			
		   if (line.startsWith("Query")) {
			   queries.add(q);
			   q = new Query();
			   String queryNum = line.substring(8);
			   Integer num = Integer.parseInt(queryNum);
			   q.setQueryNum(num);			   
		        
		    } else if(line.startsWith("relDocs=")){
		    	
		    	String[] terms = line.split("\\s");
		    	for(int x = 1; x<terms.length;++x){//String s : terms){
		    		q.addToRelevantDocs(Integer.parseInt(terms[x]));
		    	}
		    } else {
		    	String str = q.getQueryString();
		    	//String[] words = instring.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		    	String cleanline = line.replaceAll("[^a-zA-Z ]", "").trim();
		    	q.setQueryString(str + " " + cleanline);
		    }
		}
		queries.add(q);
		
//		for(Query qy : queries){
//			System.out.println(qy.getQueryNum() +" "+qy.getQueryString());
//			for(Integer x : qy.getRelevantDocs()){
//				System.out.print(x + " ");
//			}
//			System.out.println();
//		}
		
		reader.close();
		streamReader.close();
		inputStream.close();
		
		
	}
}
