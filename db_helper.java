import java.io.*;
import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Iterator;


public class db_helper {
	
	Connection server;
	Statement stmt;
	
	/*
	 * db_helper constructor
	 */
	public db_helper(){
	    //connecting to red dwarf
	    try {
	    	//this is temporary.  just for debugging purposes.
		    String red_dwarf_url = "jdbc:postgresql://reddwarf.cs.rit.edu/p48501b"; 
			server = DriverManager.getConnection(red_dwarf_url, "p48501b", "heifohhitheihiqu");
			stmt = server.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("connection failed");
			e.printStackTrace();
		}
		
	}
	
	
	/*
	 * dumps all movie data into string except for id
	 * @return movie_names
	 */
	public Vector<movie> getMoviedata(String searchTitle){
		Vector<movie> movie_data = new Vector<movie>();
		System.out.println("querying " + searchTitle + " in movies" );
		
		String query = "select * from movie where title like '%" + searchTitle + "%';";
		
		//S/ystem.out.println("created query for SQL");
		try {
			//System.out.println("query: " + query);
			
			ResultSet movies = stmt.executeQuery(query);
			while(movies.next()){
				//create temp movie object to store data
				movie tmp_movie = new movie();
				
				tmp_movie.setMid(Integer.parseInt(movies.getString("mid")));
				tmp_movie.setTitle(movies.getString("title"));
				tmp_movie.setGenre(movies.getString("genre"));
				tmp_movie.setRuntime(Integer.parseInt(movies.getString("runtime")));
				tmp_movie.setRating(movies.getString("rating"));
				tmp_movie.setPlot(movies.getString("plot"));
				tmp_movie.setProduction_year(Integer.parseInt(movies.getString("production_year")));
				tmp_movie.setRelease_date(movies.getString("release_date"));
				
				movie_data.add(tmp_movie);
			}
		}catch(SQLException e){
			System.err.println("error getting movie names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		
		return movie_data;
	}
	
	/*
	 * get movie names when given a string query
	 * @return movie_names
	 */
	public Vector<String> getMovieNamesbyTitle(String searchTitle){
		Vector<String> movie_names = new Vector<String>();
		System.out.println("querying " + searchTitle + " in movies");
		
		String query = "select title from movie where title like '%" + searchTitle + "%';";
		
		//S/ystem.out.println("created query for SQL");
		try {
			//System.out.println("query: " + query);
			
			ResultSet movies = stmt.executeQuery(query);
			System.out.println("got movie names");
			while(movies.next()){
				String t = movies.getString("title");
				movie_names.add(t);
			}
		}catch(SQLException e){
			System.err.println("error getting movie names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		
		return movie_names;
	}
	
	/*
	 * get movie names when given a string query
	 * @return movie_names
	 */
	public Vector<String> getPeopleNamesbyNames(String name){
		Vector<String> people_names = new Vector<String>();
		System.out.println("querying " + name + " in people");
		
		String query = "select name from person where name like '%" + name + "%';";
		
		try {
		
			
			ResultSet people = stmt.executeQuery(query);
			while(people.next()){
				String t = people.getString("name");
				people_names.add(t);
			}
		}catch(SQLException e){
			System.err.println("error getting movie names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		return people_names;
	}
	
	/*
	 * get people names when given a string query
	 * @return movie_names
	 */
	public Vector<String> getPeopleData(String name){
		Vector<String> people_names = new Vector<String>();
		System.out.println("querying " + name + " in people" );
		
		String query = "select name,gender from person where name like '%" + name + "%';";
		
		try {
		
			
			ResultSet people = stmt.executeQuery(query);
			System.out.println("got people names");
			while(people.next()){
				String t = people.getString("name");
				String g = people.getString("gender");
				String name_string = t + "\t" + g;
				people_names.add(name_string);
			}
		}catch(SQLException e){
			System.err.println("error getting people names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		return people_names;
	}
	
	/*
	 * get people names when given a string query
	 * @return movie_names
	 */
	public Vector<String> getCharacterData(String name){
		Vector<String> char_names = new Vector<String>();
		System.out.println("querying " + name + " in characters ");
		
		String query = "select name from characters where name like '%" + name + "%';";
		
		try {
		
			
			ResultSet people = stmt.executeQuery(query);
			//System.out.println("got people names");
			while(people.next()){
				String t = people.getString("name");
				char_names.add(t);
			}
		}catch(SQLException e){
			System.err.println("error getting character names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		return char_names;
	}
	
	/*
	 * get box office data for a single movie
	 * 
	 */
	public String getBoxOfficeData(String movie_id){
		String box_office = "";
		System.out.println("querying " + movie_id + " in box_office");
		
		String query = "select BO.opening_gross_data,BO.total_gross from box_office as BO, opened as O where o.mid = " + movie_id +
				" and O.bid = BO.id;";
		
		try {
		
			ResultSet results = stmt.executeQuery(query);
			while(results.next()){
				String owg = results.getString("opening_weekend_gross");
				String tg = results.getString("total_gross");				
				box_office = owg +"\t" + tg; 
			}
		}catch(SQLException e){
			System.err.println("box office data for \n" + movie_id);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		return box_office;
	}	
	
	/*
	 * get production companies for a single movie
	 * 
	 */
	public Vector<String> getProductionCompaniesbyMovie(String movie_id){
		Vector<String>  companies = new Vector<String>();
		String line = "";
		System.out.println("querying " + movie_id + " in production companies");
		
		String query = "select PC.id,PC.name from production_companies as PC, produced as P where P.mid = " + movie_id +
				" and P.company_id = PC.id;";
		
		try {
		
			ResultSet results = stmt.executeQuery(query);
			while(results.next()){
				String id = results.getString("id");
				String tg = results.getString("name");				
				line = id +"\t" + tg;
				
				companies.add(line);
			}
		}catch(SQLException e){
			System.err.println("production company data error for \n" + movie_id);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		
		return companies;
	}	
	

	//lets create a table
	// variables(name, type, not null)
	//primary_key(name)
	//foreign key(attr referenced_table ref att);
	public int createTable(String table_name, Vector<String> variables, Vector<String> primary_keys, Vector<String> foreign_keys){
	
		//extract variable data
		String variablesString = "";
		String primaryString = "";
		String tmpString = "";
		
		System.out.println("table name is " + table_name);
		//check data values 
		for(int k = 0; k< variables.size()-1; k++){
			
			//System.out.println("print variables " + variables.elementAt(k));
			variablesString = variablesString + variables.elementAt(k)+", ";
		}
		
		for(int k = 0; k< primary_keys.size()-1; k++){
			
			//System.out.println("print primary " + primary_keys.elementAt(k));
			primaryString += primary_keys.elementAt(k);
		}
		
		
		System.out.println("Variable String: " + variablesString);
		System.out.println("Primary Keys: " + primaryString);
		
		/*
		for(int k = 0; k< foreign_keys.size()-1; k++){
			
			System.out.println("print variables " + foreign_keys.elementAt(k));
			
		}*/
		//extract primary keys
		//extract foreign_keys	
		//String query = "create table " + table_name"( );";
		
		
		String query = "create table " + table_name + " (" + variablesString + " primary key(" + primaryString + "));";
		
		System.out.println("Create table query: " + query);
		try{	
			stmt.executeUpdate(query); 
		}catch(SQLException e){
			System.err.println("ERR: couldn't create table");
			return 0;
		}
		
		
		return 1;
	}

	/*
	 * deletes a table
	 */
	public int deleteTable(String table){
		String query = "drop table " + table;
	
		/*try{
			
			//we'll add this later.  we don't want deletion yet. 
			//stmt.executeUpdate(query); 
		}catch(SQLException e){
			System.err.println("ERR: couldn't delete table " + table);
			return 0;
		}*/
		return 1;
	}	
	
	
	public int truncateTable(String table){
		String query = "truncate table " + table;
		/*try{
		
		//we'll add this later.  we don't want truncation yet. 
		//stmt.executeUpdate(query); 
	}catch(SQLException e){
		System.err.println("ERR: couldn't truncate table " + table);
		return 0;
	}*/
		return 1;
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		db_helper helper = new db_helper();
	    System.out.println("connection success!");
//		System.out.println("give me a query!");

		String userInput = "";

		
		InputStreamReader inReader = new InputStreamReader(System.in);
		BufferedReader bReader = new BufferedReader( inReader );
								
	    while (!userInput.equals("Q") && !userInput.equals("q"))
		{
		
		System.out.println("IMDB database manager\n" + "C - Create\n" + "R - Retrieve\n" + 
			"U - Update\n" + "D - Delete\n" + "Q - Quit\n" + "Select option:");


				try{
					userInput = bReader.readLine();		//read in user input
				}catch(IOException e){
					System.err.println("trouble reading query from console\n" + e);
				}catch(NullPointerException d){
					System.err.println(d.getStackTrace());
				}
			

				if (userInput.equals("R") || userInput.equals("r"))
				{

						System.out.println("Enter search term:\n");

						String query = "";
						
						try{
							query = bReader.readLine();		//read in movie search
						}catch(IOException e){
							System.err.println("trouble reading query from console\n" + e);
						}catch(NullPointerException d){
							System.err.println(d.getStackTrace());
						}
	
						Vector<String> movie_query_results;
					
						//get movie results on query
						/*movie_query_results = helper.getMoviedata(query);	    
						if(movie_query_results != null){
							
							Enumeration<String> listy = movie_query_results.elements();
							
							//inserting data into the red dwarf db
							System.out.println("movie results:");

							while(listy.hasMoreElements()){
								//System.out.println("Inserting movie id: " + listy.nextElement());
								String movieString = listy.nextElement();
								System.out.println(movieString);
							}	    
						}*/
						//get people results on query

						Vector<String> people_query_results= helper.getPeopleData(query);	    
						if(people_query_results != null){
							
							Enumeration<String> listy = people_query_results.elements();
							
							//inserting data into the red dwarf db
							System.out.println("people results:");

							while(listy.hasMoreElements()){
								String peopleString = listy.nextElement();
								System.out.println(peopleString);
							}	    
						}    
						
						//get character results on query
						
						Vector<String> char_query_results= helper.getCharacterData(query);	    
						if(char_query_results != null){
							
							Enumeration<String> listy = char_query_results.elements();
							
							//inserting data into the red dwarf db
							System.out.println("character results:");

							while(listy.hasMoreElements()){
								String peopleString = listy.nextElement();
								System.out.println(peopleString);
							}	    
						}
				}	
				if (userInput.equals("c") || userInput.equals("C"))
				{
					String table_name_query = "";
					Vector<String> variables = new Vector<String>();
					Vector<String> primary_keys = new Vector<String>(); 
					Vector<String> foreign_keys = new Vector<String>();;
					
					System.out.print("table name:");		

					String query = "";
						
						try{
							query = bReader.readLine();		//read in table name 
							table_name_query = query;
						}catch(IOException e){
							System.err.println("trouble reading query from console\n" + e);
						}catch(NullPointerException d){
							System.err.println(d.getStackTrace());
						}
					
					System.out.println("input variable data in this format (name type not null)");
					System.out.println("name - any format\n type - sql data types\n not mull - write out not null");
					while(!query.equals("")){
						System.out.print("variable:");
						try{
							query = bReader.readLine();
						}catch(IOException e){
							System.err.println("trouble reading query from console\n" + e);
						}catch(NullPointerException d){

							System.err.println(d.getStackTrace());
						}
							//System.out.println("variable entered: " + query);
							variables.add(query);
							//System.out.println("after");
					}
					query = "1";
					System.out.println("define a primary keys in format [attr name]");
					while(!query.equals("")){
						System.out.print("primary_key:");
						try{
							query = bReader.readLine();
						}catch(IOException e){
							System.err.println("trouble reading query from console\n" + e);
						}catch(NullPointerException d){
							System.err.println(d.getStackTrace());
						}
							primary_keys.add(query);
					}

					query = "1";
					
					/*
					System.out.println("define a foreign key [attr name, table reference, reference attr]");
					while(!query.equals("")){
						System.out.print("foreign_key:");
						try{
							query = bReader.readLine();
						}catch(IOException e){
							System.err.println("trouble reading query from console\n" + e);
						}catch(NullPointerException d){
							System.err.println(d.getStackTrace());
						}
							foreign_keys.add(query);
					}*/
					
					int x = helper.createTable(table_name_query, variables, primary_keys, foreign_keys);
					
					if(x == 1)
						System.out.println("created table successfully");
					else
						System.out.println("error creating table");

				}

				if (userInput.equals("u") || userInput.equals("U"))
				{
					
				}


				if (userInput.equals("d") || userInput.equals("D"))
				{
					System.out.println("Which table do you want to delete?\n");

						String qq= "";
						
						try{
							qq= bReader.readLine();		//read in  table name
						}catch(IOException e){
							System.err.println("trouble reading query from console\n" + e);
						}catch(NullPointerException d){
							System.err.println(d.getStackTrace());
						}
					helper.deleteTable(qq);
					System.out.println("THIS WILL DELETE A TABLE BUT WE DONT WANT THAT TO FUNCTION YET");
				}

	    }
   
	   System.out.println("end of program"); 
	}
}
