import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class QueryRelevantDocsBuilder {

	public static String queryFile = "C:\\a_med\\MED.QRY";
	public static String reldocsFile = "C:\\a_med\\MED.REL";
	public static String destinationFolder = "C:\\a_med\\query_relevantdocs\\";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// this input stream reads the query file
		File file1 = new File(queryFile);
		FileInputStream inputStream1 = new FileInputStream(file1);
		InputStreamReader streamReader1 = new InputStreamReader(inputStream1, "UTF-8");
		BufferedReader reader1 = new BufferedReader(streamReader1);
		
		// this input stream reads the relevant documents list
		File file2 = new File(reldocsFile);
		FileInputStream inputStream2 = new FileInputStream(file2);
		InputStreamReader streamReader2 = new InputStreamReader(inputStream2, "UTF-8");
		BufferedReader reader2 = new BufferedReader(streamReader2);
		
		// this writes out the combined file
		PrintWriter out = new PrintWriter(destinationFolder+"queryRelDocs.txt", "UTF-8");	
		
		ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
		int qNum = 1;
		int ctr = 1;
		String[] lineTokens = null;
		ArrayList<Integer> templist = new ArrayList<Integer>();
		for (String line;(line = reader2.readLine()) != null;) {
			
			lineTokens = line.split("\\s");		        	 
       	 	qNum = Integer.parseInt(lineTokens[0]);
       	 	if (qNum == ctr) {
       	 		// when the number is the same
       	 		templist.add(Integer.parseInt(lineTokens[2]));
       	 	} else {
       	 		// when qnum jumps
       	 		lists.add(templist);
       	 		templist = new ArrayList<Integer>();
       	 		templist.add(Integer.parseInt(lineTokens[2])); 	 
       	 		++ctr;
       	 	}
		}
		lists.add(templist);		
		
		reader1.readLine();
		out.println("Query : 1");
		ctr =0;
		for (String line;(line = reader1.readLine()) != null;) {
			
		   if (line.startsWith(".I")) {
			   out.print("relDocs= ");
				for(Integer i : lists.get(ctr)){
					out.print(i + " ");
				}
				++ctr;
				out.println();	
				out.println();
			   
			   
			   String queryNum = line.substring(3);
			   queryNum.trim();
			   int num = Integer.parseInt(queryNum);
		    	queryNum="Query : "+queryNum;
		        out.println(queryNum);
		        
		    } else if(line.startsWith(".W")){
		    	// skip this line
		    } else {
		    	
		        out.println(line);
		    }
		}
		out.print("relDocs= ");
		for(Integer i : lists.get(ctr)){
			out.print(i + " ");
		}
		out.flush();
		out.close();
		reader1.close();
		reader2.close();
		streamReader1.close();
		streamReader2.close();
		inputStream1.close();
		inputStream2.close();
		

	}

}
