
/**
 * @author prm1908
 *
 */
public class movie {
  
	String title, genre, plot, rating,release_date;
	int mid,runtime, production_year;
	
	public movie(){
		title = "";		
		genre = "";
		plot = "";
		rating = "";
		release_date = "";
		
		mid = (Integer) null;
		runtime = (Integer) null;
		production_year = (Integer)null;		
	}

	/**
	 *  gets title of movie object
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * sets title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	
	/**
	 * 
	 * @return genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * sets genre
	 * @param genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPlot() {
		return plot;
	}

	/**
	 * set plot
	 * @param plot
	 */
	public void setPlot(String plot) {
		this.plot = plot;
	}

	/**
	 *  get rating
	 * @return rating
	 */
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRelease_date() {
		return release_date;
	}

	/**
	 * get release_date
	 * 
	 * @param release_date
	 */
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public int getProduction_year() {
		return production_year;
	}

	public void setProduction_year(int production_year) {
		this.production_year = production_year;
	}
	
	
	
	
	
}
