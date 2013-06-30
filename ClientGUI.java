import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Vector;
import java.util.Iterator;


public class ClientGUI implements ActionListener,KeyListener {

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
	JList table = new JList();
	
	
	JScrollPane scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	Vector<Object> sideList = new Vector<Object>();
	
	//Vector<String> results = new Vector<String>();
	db_helper db = new db_helper();
	
	public ClientGUI() {
		
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocation(200,300);	
		win.setSize(1280, 800);
	
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
		//searchPanel.addKeyListener(this);
		displayPanel.add(new JScrollPane(table));
	
		retButton.addActionListener(this);
		crButton.addActionListener(this);
		upButton.addActionListener(this);
		delButton.addActionListener(this);
	
		searchButton.addActionListener(this);
		searchButton.addKeyListener(this);
		
		textField.addKeyListener(this);
		
		
		MouseListener mouseListener = new MouseAdapter(){
			public void mouseClicked(MouseEvent mouseEvent) {
				mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2) {
		          int index = table.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0) {
		        	  System.out.println("index = " + index);
		            Object o = table.getModel().getElementAt(index);
		            Object p = listModel.getElementAt(index);
		            Object z = sideList.elementAt(index);
		            System.out.println(z.toString());
		            createDialog(z);
		            //System.out.println(p.toString());
		            //System.out.println("Double-clicked on: " + o.toString());
		          }
		        }
		      }
		};
		table.addMouseListener(mouseListener);
		//win.pack();
		win.setVisible(true);
		textField.requestFocusInWindow();
		
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
					
					
					listModel = new DefaultListModel<String>();
					sideList = new Vector<Object>();
					
					Vector<movie> movieResults = new Vector<movie>(); 
					movieResults = db.getMovieData(query);
				
					Vector<person> peopleResults = new Vector<person>();
					peopleResults = db.getPeopleData(query);
				
					Vector<character> charResults = new Vector<character>();
					charResults = db.getCharacterData(query);
					
					Vector<production_company> prodResults = new Vector<production_company>();
					prodResults = db.getProductionCompaniesbyName(query);
				
					
					//convert movie objects to just titles
					Iterator itr = movieResults.iterator();
					listModel.addElement("MOVIES");
					sideList.addElement("MOVIES");
					while (itr.hasNext() ) {
						movie tempMovie = new movie();
						tempMovie = (movie)itr.next();
						
						listModel.addElement(" " + tempMovie.getTitle());
						sideList.addElement(tempMovie);
						
					}
					listModel.addElement(" ");
					sideList.addElement(" ");
					listModel.addElement("PEOPLE");
					sideList.addElement("PEOPLE");
					itr = peopleResults.iterator();
					while (itr.hasNext() ) {
						person tempPerson = new person();
						tempPerson = (person)itr.next();
						
						listModel.addElement(" " + tempPerson.getName());
						
						sideList.addElement(tempPerson);
					}
					
					listModel.addElement(" ");
					sideList.addElement(" ");
					listModel.addElement("CHARACTER");
					sideList.addElement("Element");
					itr = charResults.iterator();
					while (itr.hasNext() ) {
						character tempPerson = new character();
						tempPerson = (character)itr.next();
						
						listModel.addElement(" " + tempPerson.getName());
						sideList.addElement(tempPerson);
						
						
					}
					
					listModel.addElement(" ");
					sideList.addElement(" ");
					listModel.addElement("COMPANIES");
					sideList.addElement("COMPANIES");
					itr = prodResults.iterator();
					while (itr.hasNext() ) {
						production_company tmpComp = new production_company();
						tmpComp = (production_company)itr.next();
						
						listModel.addElement(" " + tmpComp.getName());
						sideList.addElement(tmpComp);
					}

					
					table.setModel(listModel);
					
										
					
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

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			
			System.out.println("ENTER BEING PRESSED");
			searchButton.doClick();		

			
			//PopupPanel newDialog = new PopupPanel();
		
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	
	public void createDialog(Object z){
		String data = z.toString();
		String id = data.substring(0,data.length()-2);
		char type = data.charAt(data.length()-1);
		
		
	}
}
