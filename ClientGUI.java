import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.util.Iterator;


public class ClientGUI implements ActionListener {

	/**
	 * @param args
	 */
	
	JFrame win = new JFrame("ClientGUI");
	JPanel displayPanel = new JPanel();
	JPanel menuPanel = new JPanel();
	JPanel searchPanel = new JPanel();
	
	JButton retButton = new JButton("Retrieve");
	JButton crButton = new JButton("Create");
	JButton upButton = new JButton("Update");
	JButton delButton = new JButton("Delete");
	
	JButton searchButton = new JButton("Search");
	
	JTextField textField = new JTextField("Search Field");
	JTable table = new JTable(12,8);
	Vector<String> results = new Vector<String>();
	db_helper db = new db_helper();
	
	public ClientGUI() {
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocation(400,300);	
	
		win.add(menuPanel, BorderLayout.NORTH);
		win.add(searchPanel, BorderLayout.WEST);
		win.add(displayPanel, BorderLayout.CENTER);
	
		menuPanel.setLayout(new GridLayout(1,4));
		searchPanel.setLayout(new GridLayout(2,1));
		displayPanel.setLayout(new GridLayout(1,1));
	
		menuPanel.add(retButton);
		menuPanel.add(crButton);
		menuPanel.add(upButton);
		menuPanel.add(delButton);
	
		searchPanel.add(textField);
		searchPanel.add(searchButton);	
		displayPanel.add(table);
	
		retButton.addActionListener(this);
		crButton.addActionListener(this);
		upButton.addActionListener(this);
		delButton.addActionListener(this);
	
		searchButton.addActionListener(this);
	
		win.pack();
		win.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton clickedButton = (JButton)e.getSource();
			
			if (clickedButton.equals(retButton)) {
				
			}
			
			if (clickedButton.equals(searchButton)) {
				
				try{
					
				String query = textField.getText();
				Vector<movie> movieResults = new Vector<movie>(); 
				movieResults = db.getMoviedata(query);
				Iterator itr = movieResults.iterator();
				
				int r = 0;
				int c = 0;
				
				table.setValueAt("Movie ID:", r ,c++);
				table.setValueAt("Title:", r ,c++);
				table.setValueAt("Genre:", r, c++);
				table.setValueAt("Rating:", r, c++);
				table.setValueAt("Runtime:", r, c++);
				table.setValueAt("Production Year:", r, c++);
				table.setValueAt("Release Date:", r, c++);
				table.setValueAt("Plot", r, c++);
				
				
				while (itr.hasNext() ) {
					movie tempMovie = new movie();
					tempMovie = (movie)itr.next();
					table.setValueAt(tempMovie.getMid(),r,c++);
					table.setValueAt(tempMovie.getTitle(),r,c++);
					table.setValueAt(tempMovie.getGenre(),r,c++);
					table.setValueAt(tempMovie.getRating(),r,c++);
					table.setValueAt(tempMovie.getRuntime(),r,c++);
					table.setValueAt(tempMovie.getProduction_year(),r,c++);
					table.setValueAt(tempMovie.getRelease_date(),r,c++);
					table.setValueAt(tempMovie.getPlot(),r,c++);
					
					c = 0;
				}
				
				r++;
				c = 1;
				//results = db.getPeopleData(query);
				//itr = results.iterator();
				
				/*
				while (itr.hasNext() ) {
					
					table.setValueAt(itr.next(),r,c);
					r++;
				}
				*/
				table.revalidate();
				
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("ArrayIndexOutOfBoundsException thrown");
				}
			}
			
			if (clickedButton.equals(crButton)) {
				//textLabel.setText("Not implemented");
			}
			
			if (clickedButton.equals(upButton)) {
				//textLabel.setText("Not implemented");
			}
			
			if (clickedButton.equals(delButton)) {
				//textLabel.setText("Not implemented");
			}
		
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	ClientGUI client = new ClientGUI();
	
	}

}
