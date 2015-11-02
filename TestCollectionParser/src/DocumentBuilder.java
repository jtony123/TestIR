import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class DocumentBuilder {

	
	public static String fullFile = "C:\\a_med\\MED.ALL";
	public static String destinationFolder = "C:\\a_med\\files\\";
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		File file = new File(fullFile);
		FileInputStream inputStream = new FileInputStream(file);
		InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(streamReader);
		PrintWriter out = new PrintWriter(destinationFolder+"1.txt", "UTF-8");
		for (String line;(line = reader.readLine()) != null;) {
		   if (line.startsWith(".I")) {
		    	String filename = line.substring(3);
		    	filename.trim();
		    	filename=destinationFolder+filename+".txt";
		        out.flush();
		        out.close();
		        out = new PrintWriter(filename, "UTF-8");
		    } else if(line.startsWith(".W")){
		    	// skip this line
		    } else {
		        out.println(line);
		    }
		}
		out.flush();
		out.close();
		reader.close();
		streamReader.close();
		inputStream.close();
	}	
}
