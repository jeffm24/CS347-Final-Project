import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

public class TaskList extends JFrame implements ItemListener {
	
	ListView lv;								//ListView panel
	GridView gv;								//GridView panel
	CalendarView cv;							//CalendarView panel
	
	JMenuBar menuBar;							//main menu bar
	JMenu fileMenu;								//"File" menu
	JMenuItem open, save, exit, impt, expt;		//"File menu items
	JMenu addMenu;								//"Add" menu
	JMenuItem addTask, addGroup;				//"Add" menu items
	
	ArrayList<JPanel> views;					//ArrayList to hold the different views
	int currentView;							//Index in views of the current view
	JPanel cardPanel;							//CardLayout panel for switching between views
	JComboBox<String> viewSwitcher;				//JComboBox for initiating view switches
	GridBagConstraints c;						//Constraints for the menuBar GridBagLayout
	
	ArrayList<Group> groups;					//Main ArrayList of all current groups
	
	/*
	 * Constructor.
	 */
	public TaskList(String name) {
		super(name);
		
		this.setSize(1000, 650);
		this.setMinimumSize(new Dimension(1000, 650));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		//initialize the menu bar
		initMenuBar();	
		
		//create different view panels
		lv = new ListView(this);
		gv = new GridView();
		cv = new CalendarView();
		views = new ArrayList<JPanel>();
		views.add(lv);
		views.add(gv);
		views.add(cv);
		currentView = 0;
		
		//add all view panels to the cardlayout
		cardPanel = new JPanel(new CardLayout());		
		cardPanel.add(lv, "List View");
		cardPanel.add(gv, "Grid View");	
		cardPanel.add(cv, "Calendar View");
		this.add(cardPanel);	
		this.setContentPane(cardPanel);
		
		groups = new ArrayList<Group>();
		
		this.pack();
		this.setVisible(true);
	}
	
	/*
	 * Initializes all of the menus and menu items in the menu bar.
	 */
	public void initMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		MenuActionListener ma = new MenuActionListener();
		
		//create file menu
		fileMenu = new JMenu("File");
		open = new JMenuItem("Open");
		open.addActionListener(ma);
		fileMenu.add(open);
		save = new JMenuItem("Save");
		save.addActionListener(ma);
		fileMenu.add(save);
		impt = new JMenuItem("Import");
		impt.addActionListener(ma);
		fileMenu.add(impt);
		expt = new JMenuItem("Export");
		expt.addActionListener(ma);
		fileMenu.add(expt);
		exit = new JMenuItem("Exit");
		exit.addActionListener(ma);
		fileMenu.add(exit);
		
		//place file menu
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0.005;
		gc.gridx = 0;
		gc.gridy = 0;
		menuBar.add(fileMenu, gc);	
		
		//create add menu
		addMenu = new JMenu("Add");
		addTask = new JMenuItem("Add Task");
		addTask.addActionListener(ma);
		addMenu.add(addTask);
		addGroup = new JMenuItem("Add Group");
		addGroup.addActionListener(ma);
		addMenu.add(addGroup);
		
		//place add menu
		gc.weightx = 0.005;
		gc.gridx = 1;
		gc.gridy = 0;
		menuBar.add(addMenu, gc);
		
		String comboBoxItems[] = {"List View", "Grid View", "Calendar View"};
		viewSwitcher = new JComboBox<String>(comboBoxItems);
		viewSwitcher.setEditable(false);
		viewSwitcher.addItemListener(this);
		gc.anchor = GridBagConstraints.LINE_END;
		gc.weightx = .99;
		gc.gridx = 2;
		gc.gridy = 0;
		menuBar.add(viewSwitcher, gc);
		
		this.setJMenuBar(menuBar);
	}
	
	/*
	 * Creates a JOptionPane to get input from the user for adding a task.
	 * Adds the task and returns true if all fields were entered correctly.
	 * Does nothing and returns false otherwise.
	 */
	public boolean addTask() {
		JTextField groupField = new JTextField();
		JTextField nameField = new JTextField();
		JTextField prioField = new JTextField();
		JTextField descField = new JTextField();
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	    JFormattedTextField dateField = new JFormattedTextField(format);
	    dateField.setText("yyyy/MM/dd");
		JFormattedTextField alarmField = new JFormattedTextField(format);
		alarmField.setText("yyyy/MM/dd");
		
		JPanel myPanel = new JPanel(new GridLayout(6, 1));
		myPanel.add(new JLabel("Group:"));
		myPanel.add(groupField);
		myPanel.add(new JLabel("Name:"));
		myPanel.add(nameField);
		myPanel.add(new JLabel("Priority:"));
		myPanel.add(prioField);
		myPanel.add(new JLabel("Description:"));
		myPanel.add(descField);
		myPanel.add(new JLabel("Date:"));
		myPanel.add(dateField);
		myPanel.add(new JLabel("Alarm:"));
		myPanel.add(alarmField);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, 
				"Enter Task Info", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
      		Task newTask = null;
      		
      		//check if any of the fields were left blank
	    	if (groupField.getText().equals("") || nameField.getText().equals("") || prioField.getText().equals("") 
	    			|| descField.getText().equals("") || dateField.getText().equals("") || alarmField.getText().equals("")) {
	    		JOptionPane.showMessageDialog(null, "Please make sure all fields are filled out.", "ERROR", JOptionPane.OK_OPTION);
	    		return false;
	    	}
      		
	    	//check for a group to add the task to with the same name as the given group
			for (int i = 0 ; i < groups.size() ; i++) {
      			if (groups.get(i).getName().equals(groupField.getText())) {
      				//create new task if possible with given input
					try {
						String name = nameField.getText();
	      				int priority = Integer.parseInt(prioField.getText());
	      				String desc = descField.getText();
						Date date = format.parse(dateField.getText());
						Date alarm = format.parse(alarmField.getText());
						newTask = new Task(name, priority, desc, date, alarm);
	      				groups.get(i).addTask(newTask);
	      				
	      				//re-generate pages for listView
	      				lv.generatePages(groups);
	      				
	      				//TESTING
	      				System.out.println("Created task: " + name + " in group: " + groupField.getText());
	      				return true;
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null, "Invalid date format. Please try again.", "ERROR", JOptionPane.OK_OPTION);
					}
      			}
      		} 		
			
			JOptionPane.showMessageDialog(null, "Could not find group. Please try again.", "ERROR", JOptionPane.OK_OPTION);
      	} else if (result == JOptionPane.CANCEL_OPTION) {
	    	return true;
	    }	
	    
	    return false;
	}
	
	/*
	 * Creates a JOptionPane to get input from the user for adding a group.
	 * Adds the group and returns true if all fields were entered correctly and the group does not
	 * 		not already exist.
	 * Does nothing and returns false otherwise. 
	 */
	public boolean addGroup() {
		boolean groupExists = false;
		
		JTextField nameField = new JTextField();
		JTextField prioField = new JTextField();
		JTextField descField = new JTextField();
		
		JPanel myPanel = new JPanel(new GridLayout(3, 1));
		myPanel.add(new JLabel("Name:"));
		myPanel.add(nameField);
		myPanel.add(new JLabel("Priority:"));
		myPanel.add(prioField);
		myPanel.add(new JLabel("Description:"));
		myPanel.add(descField);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, 
				"Enter Group Info", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	    	Group newGroup = null;
	    	
	    	//check if any of the fields were left blank
	    	if (nameField.getText().equals("") || prioField.getText().equals("") || descField.getText().equals("")) {
	    		JOptionPane.showMessageDialog(null, "Please make sure all fields are filled out.", "ERROR", JOptionPane.OK_OPTION);
	    		return false;
	    	}
	    	
	    	//check whether or not a group with the same name already exists
	    	String gName = nameField.getText();
	    	for (int i = 0 ; i < groups.size() ; i++) {
	    		if (groups.get(i).getName().equals(gName)) {
	    			groupExists = true;
	    			JOptionPane.showMessageDialog(null, "Group already exists.", "ERROR", JOptionPane.OK_OPTION);
	    			return false;
	    		}
	    	}
	    	
	    	//if the group does not exist, add it to the groups array
	    	if (!groupExists) {
	    		newGroup = new Group(nameField.getText(), Integer.parseInt(prioField.getText()), descField.getText());
	    		groups.add(newGroup);
	    		
	    		//re-generate pages for listView
	    		lv.generatePages(groups);
	    		
	    		//TESTING
	    		System.out.println("Group: " + nameField.getText() + " created successfully.");
	    		return true;
	    	}
	    } else if (result == JOptionPane.CANCEL_OPTION) {
	    	return true;
	    }
	    
	    JOptionPane.showMessageDialog(null, "Could not create group. Please try again.", "ERROR", JOptionPane.OK_OPTION);
	    return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 * 
	 * Gets the state changed event from the JComboBox to switch to the appropriate view.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		switch ((String)e.getItem()) {
		case "List View":
			currentView = 0;
			break;
		case "Grid View":
			currentView = 1;
			break;
		case "Calendar View":
			currentView = 2;
			break;
		}
		
		CardLayout cl = (CardLayout)(cardPanel.getLayout());
	    cl.show(cardPanel, (String)e.getItem());
	    
	    //TESTING
		System.out.println("Changed currentView to " + currentView);
	}
	
	/*
	 * Action Listener to get menu item clicks and act on them.
	 */
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//if "Open" is clicked under "File"
			if (e.getSource().equals(open)) {
				System.out.println("open");
			} 
			//if "Save" is clicked under "File"
			else if (e.getSource().equals(save)) {
				System.out.println("save");
			} 
			//if "Import" is clicked under "File"
			else if (e.getSource().equals(impt)) {
				System.out.println("import");
			} 
			//if "Export" is clicked under "File"
			else if (e.getSource().equals(expt)) {
				System.out.println("export");
			} 
			//if "Exit" is clicked under "File"
			else if (e.getSource().equals(exit)) {
				System.exit(0);
			} 
			//if "Add Task" is clicked under "Add"
			else if (e.getSource().equals(addTask)) {
				while(!addTask())
					;
			} 
			//if "Add Groups" is clicked under "Add"
			else if (e.getSource().equals(addGroup)) {
				while(!addGroup())
					;
			}
		}
	}
}


