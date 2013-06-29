
public class character {

	String name;
	Integer id;
	
	public character(){
		name = "";
		id =  null;
	}
	
	public character(int id, String name){
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override public String toString(){
		return name;
	}
	

}

