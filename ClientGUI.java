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
	JTable table = new JTable(6,3);
	Vector<String> results = new Vector<String>();
	
	public ClientGUI() {
		

	results.add("test1");
	results.add("test2");
		
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
				Iterator itr = results.iterator();
				
				int x = 0;
				while (itr.hasNext() ) {
					table.setValueAt(itr.next(),0,x );
					x++;
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
