import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Iterator;


public class db_helper {
	
	Connection server;
	Statement stmt;
	DatabaseMetaData dbmd;
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
			dbmd = server.getMetaData();
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
	public Vector<movie> getMovieData(String searchTitle){
		Vector<movie> movie_data = new Vector<movie>();
		System.out.println("querying title:" + searchTitle + " in movies" );
		
		String query = "select * from movie where title like '%" + searchTitle + "%';";
		//String query = "select * from movie;";
		//S/ystem.out.println("created query for SQL");
		try {
			//System.out.println("query: " + query);
			
			ResultSet movies = stmt.executeQuery(query);
			while(movies.next()){
				//create temp movie object to store data
				movie tmp_movie = new movie();
				
				String runtime = null;
				
				
				
				tmp_movie.setMid(Integer.parseInt(movies.getString("mid")));
				String title = movies.getString("title");
				System.out.println("working on movie " + title);
				tmp_movie.setTitle(title);
				tmp_movie.setGenre(movies.getString("genre"));
				System.out.println("got genre");
				runtime = movies.getString("runtime");
				System.out.println("got runtime of " + runtime);
				if(!(runtime == null)){
					tmp_movie.setRuntime(Integer.parseInt(runtime));
				}
				
				System.out.println("got runtime");
				tmp_movie.setRating(movies.getString("rating"));
				System.out.println("got rating");
				tmp_movie.setPlot(movies.getString("plot"));
				System.out.println("got plot");
				String prod_year = movies.getString("production_year");
				if(!(prod_year == null)){
					System.out.println("we are here");
					tmp_movie.setProduction_year(Integer.parseInt(prod_year));
				}
				System.out.println("got production year of " + prod_year);
				tmp_movie.setRelease_date(movies.getString("release_date"));
				
				movie_data.add(tmp_movie);
				
			}
		}catch(SQLException e){
			System.err.println("error getting movie names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception getting movies" + d);
		}
		
		return movie_data;
	}
	
	/**
	 * returns movie object of one movie hopefully
	 * @return movie_names
	 */
	public movie getMovieDatabyID(String movie_id){
		movie movie_data = new movie();
		System.out.println("querying " + movie_id + " in movies" );
		
		String query = "select * from movie where mid = " + movie_id + ";";
		
		try {
		
			
			ResultSet movies = stmt.executeQuery(query);
			
			/*check length of result set*/
			
			
			if(movies.next()){
				System.out.println("setting data in movie object");
				//create temp movie object to store data
				movie_data.setMid(Integer.parseInt(movie_id));
				
				String title = movies.getString("title");
				System.out.println("title of movie retrieving is " + title);
				movie_data.setTitle(title);
				
				movie_data.setGenre(movies.getString("genre"));
				String runtime = movies.getString("runtime");
				
				if(runtime == null){
					movie_data.setRuntime(0);
				}else{
					movie_data.setRuntime(Integer.parseInt(runtime));
				}
					
				
				
				movie_data.setRating(movies.getString("rating"));
				
				movie_data.setPlot(movies.getString("plot"));
				String py = movies.getString("production_year");
				if(py == null){
					movie_data.setProduction_year(0);
				}else{
					movie_data.setProduction_year(Integer.parseInt(py));
				}
			
				
				movie_data.setRelease_date(movies.getString("release_date"));
				
			}
		}catch(SQLException e){
			System.err.println("error getting movie names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d.getStackTrace());
		}
		
		return movie_data;
	}
	/*
	 * get people names when given a string query
	 * @return movie_names
	 */
	public Vector<person> getPeopleData(String name){
		Vector<person> people_names = new Vector<person>();
		System.out.println("querying " + name + " in people" );
		
		String query = "select pid,name,gender from person where name like '%" + name + "%';";
		
		try {
			
			ResultSet people = stmt.executeQuery(query);
			System.out.println("got people names");
			while(people.next()){
				person tmp_person = new person();
				tmp_person.setId(Integer.parseInt(people.getString("pid")));
				tmp_person.setName(people.getString("name"));
				
				tmp_person.setGender(people.getString("gender"));
				
				people_names.add(tmp_person);
			}
		}catch(SQLException e){
			System.err.println("error getting people names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception while getting people names" + d);
		}
		return people_names;
	}
	
	
	/**
	 * get people names when given a string query
	 * @return movie_names
	 */
	public person getPeopleDataByID(String movie_id){
		person personData = new person();
		System.out.println("querying " + movie_id + " in people" );
		
		String query = "select pid,name,gender from person where pid = " + movie_id + ";";
		
		try {
			
			ResultSet people = stmt.executeQuery(query);
			
			if(people.next()){
				
				personData.setId(Integer.parseInt(movie_id));
				personData.setName(people.getString("name"));
				
				personData.setGender(people.getString("gender"));
				
				
			}
		}catch(SQLException e){
			System.err.println("error getting people names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception while getting people names" + d);
		}
		return personData;
	}
	/**
	 * 
	 * searches character table for a name.  returns all data on names like the one passed in.
	 * 
	 * @param name
	 * @return characterData
	 */
	public Vector<character> getCharacterData(String name){
		Vector<character> characterData = new Vector<character>();
		System.out.println("querying " + name + " in characters ");
		
		String query = "select rid,name from characters where name like '%" + name + "%';";
		
		try {
		
			
			ResultSet people = stmt.executeQuery(query);
			//System.out.println("got people names");
			while(people.next()){
				character tmp_char = new character();
				tmp_char.setId(Integer.parseInt(people.getString("rid")));
				tmp_char.setName(people.getString("name"));
				characterData.add(tmp_char);
			}
		}catch(SQLException e){
			System.err.println("error getting character names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		return characterData;
	}
	
	/**
	 * 
	 * searches character table for a name.  returns all data on names like the one passed in.
	 * 
	 * @param name
	 * @return characterData
	 */
	public character getCharacterDataByID(String id){
		character characterData = new character();
		System.out.println("querying " + id + " in characters ");
		
		String query = "select rid,name from characters where rid = " + id + ";";
		
		try {
		
			ResultSet people = stmt.executeQuery(query);

			while(people.next()){
				
				characterData.setId(Integer.parseInt(id));
				characterData.setName(people.getString("name"));

			}
		}catch(SQLException e){
			System.err.println("error getting character names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		return characterData;
	}
	
	public Vector<String> getPersonAccomplistments(String person_id){
		Vector<String> accomp = new Vector<String>();
		
		//get movies and characters played by person
		String query = "select M.release_date,M.title,C.name from movie as M, characters as C, person as P, acts_in as A where P.pid = " + person_id + " and A.pid = P.pid and M.mid = A.mid and A.rid = C.rid order by release_date";
		
		
		//get movies acted in
		try {
			ResultSet movieroles = stmt.executeQuery(query);
			accomp.addElement("ACTING IN");
			while(movieroles.next()){
				String movie = "";
				String role = "";
				String rd = "";
				rd = movieroles.getString("release_date");
				movie = movieroles.getString("title");
				role = movieroles.getString("name");
				
				if(rd == null){
					rd = "  no date   ";
				}
				
				String entry = "    (" + rd + ") in " + movie + " as " + role + "."; 
				accomp.addElement(entry);
			}
		}catch(SQLException e){
			System.err.println("error getting character names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}		
		
		accomp.addElement(" ");
		
		//get movies directed 
		try {
			query = "select M.release_date,M.title from movie as M, is_director as A where A.pid = " + person_id + " and A.mid = M.mid order by release_date";
			ResultSet movieroles = stmt.executeQuery(query);
			accomp.addElement("DIRECTED");
			while(movieroles.next()){
				String movie = "";
				String rd = "";
				rd = movieroles.getString("release_date");
				movie = movieroles.getString("title");
				
				
				if(rd == null){
					rd = "  no date   ";
				}
				
				String entry = "    (" + rd + ") in " + movie; 
				accomp.addElement(entry);
			}
		}catch(SQLException e){
			System.err.println("error getting character names\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}		
		
				
		
		
		return accomp;
	}
		
		
	/**
	 * 
	 * @param movie_id
	 * @return 
	 */
	public box_office getBoxOfficeData(String movie_id){
		box_office tmpBox = new box_office();
		
		System.out.println("querying " + movie_id + " in box_office");
		
		String query = "select BO.id,BO.opening_weekend_gross,BO.total_gross from box_office as BO, opened as O where o.mid = " + movie_id +" and O.bid = BO.id;";
		
		
		try {
		
			ResultSet results = stmt.executeQuery(query);
			while(results.next()){
				String id = results.getString("id");
				String owg = results.getString("opening_weekend_gross");
				String tg = results.getString("total_gross");				
				tmpBox = new box_office(Integer.parseInt(id),owg,tg);
			}
		}catch(SQLException e){
			System.err.println("SQL error for box office data for \n" + movie_id);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		

		return tmpBox;
	}	
	
/**
 * get production companies related to a movie
 * @param movie_id
 * @return
 */
	public Vector<production_company> getProductionCompaniesbyMovie(String movie_id){
		Vector<production_company>  companies = new Vector<production_company>();
		String line = "";
		System.out.println("querying " + movie_id + " in production companies");
		
		String query = "select PC.id,PC.name,PC.location from production_companies as PC, produced as P where P.mid = " + movie_id +
				" and P.company_id = PC.id;";
		
		try {
		
			ResultSet results = stmt.executeQuery(query);
			while(results.next()){
				
				String id = results.getString("id");
				String tg = results.getString("name");	
				String loc = results.getString("location");
				production_company tmp_comp = new production_company(Integer.parseInt(id),tg,loc);
				
				companies.add(tmp_comp);
			}
		}catch(SQLException e){
			System.err.println("production company data error for \n" + movie_id);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
		
		return companies;
	}	
	
	/**
	 * returns list of production companies by name
	 * @param movie_id
	 * @return companies
	 */
	public Vector<production_company> getProductionCompaniesbyName(String company_name){
		Vector<production_company>  companies = new Vector<production_company>();
		System.out.println("querying " + company_name + " in production companies");
		
		String query = "select * from production_companies where name like '%" + company_name +"%';";
		
		try {
		
			ResultSet results = stmt.executeQuery(query);
			while(results.next()){
				String id = results.getString("id");
				String tg = results.getString("name");	
				String loc = results.getString("location");
				production_company tmp_comp = new production_company(Integer.parseInt(id),tg,loc);
				
				companies.add(tmp_comp);
			}
		}catch(SQLException e){
			System.err.println("production company data error for \n" + company_name);
		}catch(NullPointerException d){
			System.err.println("null pointer exception getting production company by name" + d);
		}
		
		return companies;
	}	
	
	/**
	 * returns data on production company
	 * @param movie_id
	 * @return companies
	 */
	public production_company getProductionCompaniesbyID(String company_id){
		production_company  comp = new production_company();
		System.out.println("querying " + company_id + " in production companies");
		
		String query = "select * from production_companies where id = '" + company_id +"';";
		
		try {
		
			ResultSet results = stmt.executeQuery(query);
			while(results.next()){
				String id = results.getString("id");
				String tg = results.getString("name");	
				String loc = results.getString("location");
				comp = new production_company(Integer.parseInt(id),tg,loc);
				
				
			}
		}catch(SQLException e){
			System.err.println("production company data error for \n" + company_id);
		}catch(NullPointerException d){
			System.err.println("null pointer exception getting production company by name" + d);
		}
		
		return comp;
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
     * Adds a movie and all of its corresponding data, uses the stored procedure add_movie
     * @param actorArr a vector of string arrays, each array is of length 3 and
     * formatted as follows: 0th index = Name, 1st index = gender, 2nd index = Role
     * @param directorsArr, producersArr, writersArr, production_companiesArr
     * are all vectors of strings
     * @param opening_day is a Date 
     */
    public void addMovie( String name, Vector<String[]> actorArr,
                         Vector<String[]> directorsArr, Vector<String[]> producersArr,
                         Vector<String[]> writersArr, String total_gross,
                         String opening_weekend_gross, Date opening_day,
                         Vector<String[]> production_companiesArr, String genre,
                         String rating, String plot, int runtime,
                         int production_year ){
        try{
            
            String opening_dayStr = (new SimpleDateFormat( "yyyy MM dd" )).format(opening_day);
            
            String actors = parseHelper( actorArr, 3 );
            String directors = parseHelper( directorsArr, 2 );
            String writers = parseHelper( writersArr, 2 );
            String producers = parseHelper( producersArr, 2 );
            String production_companies = parseHelper( production_companiesArr, 2 );
            
            String theQuery = "SELECT * FROM create_movie('" + name + "','" + actors +
            "','" + directors + "','" + producers + "','" + writers +
            "','" + total_gross + "','" + opening_weekend_gross +
            "', TO_DATE('" + opening_dayStr + "','YYYY MM DD')" +
            ",'" + production_companies + "','" + genre + "','" + rating +
            "','" + plot + "'," + runtime + "," + production_year + ");";
            
            System.out.println( theQuery );
            
            stmt.executeQuery( theQuery );
            
        }catch(SQLException e){
			System.err.println("error adding movie\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" );
            d.printStackTrace();
		}
        
    }
    
    private String parseHelper( Vector<String[]> toParse, int strArrSize ){
        String toReturn = "";
        for( int i = 0; i < toParse.size(); i++ ){
            String[] thisInner = toParse.get(i);
            for( int inner = 0; inner < strArrSize; inner++ ){
                toReturn += "" + thisInner[inner] + "";
                if( inner < strArrSize - 1 ){
                    toReturn += ",";
                }
            }
            if( i < toParse.size() - 1 ){
                toReturn += ";";
            }
        }
        return toReturn;
    }
    
    /**
     * Deletes data from a given table where the table IDs for deletion are in IDs
     * @param table the table to delete from
     * @param IDs the ids to remove 
     */
    public void deleteData(String table, String id){
        
      
        String col = "";
        if(table.equals("movie")){
        	col = "mid";
        }else if (table.equals("person")){
        	col = "pid";
        }else if (table.equals("characters")){
        	col = "rid";
        }
        else{
        	col = "id";
        }
        
        
        try{
            
            //ResultSet rs = dbmd.getColumns(null, null, table, null);
            //String idCol = rs.getMetaData().getColumnName( 1 );
            System.out.println("using column " + col);
            
            stmt.executeUpdate("delete from " + table + " where " + col + " = " + id + ";");
            
        }catch(SQLException e){
			System.err.println("error deleting movie\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}

    }
    
    /**
     * Inserts data to a given table with columns (or null if inserting to all columns) and values vals
     * @param table the table name to insert to
     * @param cols the columns to update, or null if updating all columns
     * @param vals the values to insert to the columns
     */
    public void insertData(String table, Vector<String> cols, Vector<String> vals){
        
        String colsStr = "";
        if( cols != null ){
            colsStr = "(";
            
            for( int i = 0; i < cols.size(); i++ ){
                if( i < cols.size() - 1 ){
                    colsStr += cols.get(i) + ",";
                }
            }
            colsStr += ")";
        }
        
        String valStr = "(";
        
        for( int i = 0; i < vals.size(); i++ ){
            if( i < vals.size() - 1 ){
                valStr += vals.get(i) + ",";
            }
        }
        valStr += ")";
        
        try{
            
            stmt.executeQuery( "insert into " + table + colsStr + " values " + valStr );
            
        }catch(SQLException e){
			System.err.println("error adding movie\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
        
    }
    
    /**
     * Updates data
     * @param table the table to update
     * @param cols the columns to update
     * @param vals the values to set the columns to
     * @param IDs the IDs to update for the given table
     */
    public void updateData(String table, String id_type, String colName, String value, String id, int intSelect){
 
    	String query;
        try{
        	if(intSelect==1){
        		 query = "update " + table + " set " + colName + " = " + value + " where " + id_type + " = " + id ;
        	}else{
        		 query = "update " + table + " set " + colName + " = '" + value + "' where " + id_type + " = " + id ;
        	}
            System.out.println("running update: " + query );
            stmt.executeUpdate(query);
            
        }catch(SQLException e){
			System.err.println("error updating movie\n" + e);
		}catch(NullPointerException d){
			System.err.println("null pointer exception" + d);
		}
        
    }
    

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        String name = "this will work";
        
        Vector<String[]> actors = new Vector<String[]>();
        actors.add( new String[]{"actor", "m", "role"} );
        actors.add( new String[]{"actor1", "f", "rol1"} );
        
        Vector<String[]> directors = new Vector<String[]>();
        directors.add( new String[]{"director1 yeah", "m"} );
        directors.add( new String[]{"director2 woot", "f"} );

        Vector<String[]> producers = new Vector<String[]>();
        producers.add( new String[]{"producer1 j", "m"} );
        
        Vector<String[]> writers = new Vector<String[]>();
        writers.add( new String[]{"writer1 jkldfsj", "f"} );

        String total_gross = "$43289032";
        String opening_weekend_gross = "$9203";
        
        Date opening_day = new Date(1, 1, 1);
        
        Vector<String[]> production_companies = new Vector<String[]>();
        production_companies.add( new String[]{"dfjslk;jfdksl", "behind you"} );

        String genre = "THRILLA";
        String rating = "NC-17";
        String plot = "The quick brown fox jumped over the lazy dog";
        int runtime = 340;
        int production_year = 1984;
        
        
		db_helper helper = new db_helper();
	    System.out.println("connection success!");
//		System.out.println("give me a query!");

		String userInput = "";

		
        helper.addMovie( name, actors, directors, producers, writers, total_gross, opening_weekend_gross, opening_day,production_companies, genre, rating, plot, runtime, production_year );
        
        System.exit(0);

        
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

						/*Vector<String> people_query_results= helper.getPeopleData(query);	    
						if(people_query_results != null){
							
							Enumeration<String> listy = people_query_results.elements();
							
							//inserting data into the red dwarf db
							System.out.println("people results:");

							while(listy.hasMoreElements()){
								String peopleString = listy.nextElement();
								System.out.println(peopleString);
							}	    
						}  */  
						
						//get character results on query
						
						/*Vector<String> char_query_results= helper.getCharacterData(query);	    
						if(char_query_results != null){
							
							Enumeration<String> listy = char_query_results.elements();
							
							//inserting data into the red dwarf db
							System.out.println("character results:");

							while(listy.hasMoreElements()){
								String peopleString = listy.nextElement();
								System.out.println(peopleString);
							}	    
						}*/
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
