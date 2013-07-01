
/**
 * holds production company data
 * @author paul
 *
 */
public class production_company{
	
	Integer id;
	String name,location;
	
	public production_company(){

	}
	
	public production_company(Integer id, String name, String location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override public String toString(){
		return id.toString()+"s";
	}
	
	
}
