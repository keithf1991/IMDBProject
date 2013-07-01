
/**
 * holds person data
 * @author paul
 *
 */
public class person {
  
	String name, gender;
	Integer id;
	
	public person(){
		name = "";
		gender = "";
		
		id = null;
	}
	
	public person(int id, String name, String gender){
		this.name = name;
		this.gender = gender;
		
		this.id = (Integer) id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override public String toString(){
		return id.toString() + "p";
	}
	
	
}
