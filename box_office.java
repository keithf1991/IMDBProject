/**
 * 
 * holds box office data.  to be imported by db_helpber
 * @author paul
 *
 */
public class box_office{
	
	Integer id;
	String opening_weekend, total_gross;

	

	public box_office(){

	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getOpening_weekend() {
		return opening_weekend;
	}



	public void setOpening_weekend(String opening_weekend) {
		this.opening_weekend = opening_weekend;
	}



	public String getTotal_gross() {
		return total_gross;
	}



	public void setTotal_gross(String total_gross) {
		this.total_gross = total_gross;
	}



	public box_office(Integer id, String opening_weekend, String total_gross) {
		this.id = id;
		this.opening_weekend = opening_weekend;
		this.total_gross = total_gross;
	}
	
	
}
