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
	JTextField textField = new JTextField("");
	
	DefaultListModel listModel;
	//listModel.addElement("no query");
	JList table = new JList(listModel);
	
	
	JScrollPane scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	//Vector<String> results = new Vector<String>();
	db_helper db = new db_helper();
	
	public ClientGUI() {
		
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocation(200,300);	
	
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
					String[] tmp_array;
					
					if (query.equalsIgnoreCase("") || query.equalsIgnoreCase(" ")) {
						query = "nonsenseSearchTerm";
					}	
					
					Vector<movie> movieResults = new Vector<movie>(); 
					movieResults = db.getMoviedata(query);
				
					Vector<person> peopleResults = new Vector<person>();
					peopleResults = db.getPeopleData(query);
				
					Vector<character> charResults = new Vector<character>();
					charResults = db.getCharacterData(query);
				
					
					//convert movie objects to just titles
					Iterator itr = movieResults.iterator();
					
					while (itr.hasNext() ) {
						movie tempMovie = new movie();
						tempMovie = (movie)itr.next();
						
						listModel.addElement(tempMovie.toString());
						
						
					}

										
					
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
