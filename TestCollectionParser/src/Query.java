import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class Query {

	private int queryNum;
	private String queryString = "";
	private List<Integer> relevantDocs = new ArrayList<Integer>();
	private double precision;
	private double recall;
	
	public Query(){
		
		
	}

	/**
	 * @return the queryNum
	 */
	public int getQueryNum() {
		return queryNum;
	}

	/**
	 * @param queryNum the queryNum to set
	 */
	public void setQueryNum(int queryNum) {
		this.queryNum = queryNum;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * @param queryString the queryString to set
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * @return the relevantDocs
	 */
	public List<Integer> getRelevantDocs() {
		return relevantDocs;
	}

	/**
	 * @param relevantDocs the relevantDocs to set
	 */
	public void setRelevantDocs(List<Integer> relevantDocs) {
		this.relevantDocs = relevantDocs;
	}
	
	public void addToRelevantDocs(Integer i){
		relevantDocs.add(i);
	}

	/**
	 * @return the precision
	 */
	public double getPrecision() {
		return precision;
	}

	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(double precision) {
		this.precision = precision;
	}

	/**
	 * @return the recall
	 */
	public double getRecall() {
		return recall;
	}

	/**
	 * @param recall the recall to set
	 */
	public void setRecall(double recall) {
		this.recall = recall;
	}
	
}
