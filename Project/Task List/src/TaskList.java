import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

public class TaskList extends JFrame implements ItemListener {
	
	ListView lv;
	GridView gv;
	CalendarView cv;
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem open, save, exit, impt, expt;
	JMenu addMenu;
	JMenuItem addTask, addGroup;
	
	ArrayList<View> views;
	int currentView;
	JPanel cardPanel;
	JComboBox<String> viewSwitcher;
	GridBagConstraints c;
	
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
		
		lv = new ListView();
		gv = new GridView();
		cv = new CalendarView();
		views.add(lv);
		views.add(gv);
		views.add(cv);
		currentView = 0;
		
		cardPanel = new JPanel(new CardLayout());		
		cardPanel.add(lv, "List View");
		cardPanel.add(gv, "Grid View");	
		cardPanel.add(cv, "Calendar View");
		this.add(cardPanel);	
		this.setContentPane(cardPanel);
		
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
	 */
	public void addTask() {
		int i;
		
		JTextField groupField = new JTextField();
		JTextField nameField = new JTextField();
		JTextField prioField = new JTextField();
		JTextArea descField = new JTextArea();
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
      		views.get(currentView);
      		Task newTask = null;
      		
			for (i = 0 ; i < View.groups.size() ; i++) {
      			if (View.groups.get(i).equals(groupField.getText())) {
      				//create new task if possible with given input
					try {
						String name = nameField.getText();
	      				int priority = Integer.parseInt(prioField.getText());
	      				String desc = descField.getText();
						Date date = format.parse(dateField.getText());
						Date alarm = format.parse(alarmField.getText());
						newTask = new Task(name, priority, desc, date, alarm);
	      				View.groups.get(i).addTask(newTask);
					} catch (ParseException e) {
						System.out.println("Invalid date format");
					}
      			}
      		}
			//if newTask is null, the group was not found
			if (newTask == null)
				System.out.println("Could not find group");
      	}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
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
				addTask();
			} 
			//if "Add Groups" is clicked under "Add"
			else if (e.getSource().equals(addGroup)) {
				System.out.println("add group");
			}
		}
	}
}


