import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.*;

public class TaskList extends JFrame implements ItemListener {

	ListView lv; // ListView panel
	GridView gv; // GridView panel
	CalendarView cv; // CalendarView panel

	JMenuBar menuBar; // main menu bar
	JMenu fileMenu; // "File" menu
	JMenuItem open, save, exit, impt; // "File menu items
	JMenu addMenu; // "Add" menu
	JMenuItem addTask, addGroup; // "Add" menu items
	JMenu sortMenu; // "Sort" menu
	JMenuItem sortTasks, sortGroups; // "Sort" menu items

	ArrayList<JPanel> views; // ArrayList to hold the different views
	int currentView; // Index in views of the current view

	Sorting sorter; // Sorter class for sorting the lists and groups
	int currentTaskSort; // Current task sort of the view
	int currentGroupSort; // Current group sort of the view

	JPanel cardPanel; // CardLayout panel for switching between views

	JComboBox<String> viewSwitcher; // JComboBox for initiating view switches
	GridBagConstraints c; // Constraints for the menuBar GridBagLayout

	ArrayList<Group> groups; // Main ArrayList of all current groups

	/*
	 * Constructor.
	 */
	public TaskList(String name) {
		super(name);

		this.setSize(1000, 650);
		this.setMinimumSize(new Dimension(1000, 650));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setResizable(false);

		// initialize the menu bar
		initMenuBar();

		// create different view panels
		lv = new ListView(this);
		gv = new GridView();
		cv = new CalendarView();
		views = new ArrayList<JPanel>();
		views.add(lv);
		views.add(gv);
		views.add(cv);
		currentView = 0;

		sorter = new Sorting();
		currentTaskSort = 0;
		currentGroupSort = 0;

		// add all view panels to the cardlayout
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

		// create file menu
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
		exit = new JMenuItem("Exit");
		exit.addActionListener(ma);
		fileMenu.add(exit);

		// place file menu
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0.005;
		gc.gridx = 0;
		gc.gridy = 0;
		menuBar.add(fileMenu, gc);

		// create add menu
		addMenu = new JMenu("Add");
		addTask = new JMenuItem("Add Task");
		addTask.addActionListener(ma);
		addMenu.add(addTask);
		addGroup = new JMenuItem("Add Group");
		addGroup.addActionListener(ma);
		addMenu.add(addGroup);

		// place add menu
		gc.weightx = 0.005;
		gc.gridx = 1;
		gc.gridy = 0;
		menuBar.add(addMenu, gc);

		// create sort menu
		sortMenu = new JMenu("Sort");
		sortTasks = new JMenuItem("Sort Tasks");
		sortTasks.addActionListener(ma);
		sortMenu.add(sortTasks);
		sortGroups = new JMenuItem("Sort Groups");
		sortGroups.addActionListener(ma);
		sortMenu.add(sortGroups);

		// place sort menu
		gc.weightx = 0.005;
		gc.gridx = 2;
		gc.gridy = 0;
		menuBar.add(sortMenu, gc);

		// add combo box
		String comboBoxItems[] = { "List View", "Grid View", "Calendar View" };
		viewSwitcher = new JComboBox<String>(comboBoxItems);
		viewSwitcher.setEditable(false);
		viewSwitcher.addItemListener(this);
		gc.anchor = GridBagConstraints.LINE_END;
		gc.weightx = .99;
		gc.gridx = 3;
		gc.gridy = 0;
		menuBar.add(viewSwitcher, gc);

		this.setJMenuBar(menuBar);
	}

	/*
	 * Creates a JOptionPane to get input from the user for adding a task. Adds
	 * the task and returns true if all fields were entered correctly. Does
	 * nothing and returns false otherwise.
	 */
	public void addTask() {
		boolean valid = false;

		// if there are no groups, the user cannot add a task
		if (groups.size() != 0) {
			// create pop-up dialogue fields
			JComboBox<String> groupBox = new JComboBox<String>();
			for (int i = 0; i < groups.size(); i++)
				groupBox.addItem(groups.get(i).getName());

			JTextField nameField = new JTextField();

			JComboBox<Integer> prioBox = new JComboBox<Integer>();
			for (int i = 1; i <= 10; i++)
				prioBox.addItem(i);

			JTextField descField = new JTextField();

			DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			JFormattedTextField dateField = new JFormattedTextField(format);
			dateField.setText("yyyy/MM/dd");

			JFormattedTextField alarmField = new JFormattedTextField(format);
			alarmField.setText("yyyy/MM/dd");

			// add components to JPanel for pop-up
			JPanel myPanel = new JPanel(new GridLayout(6, 1));
			myPanel.add(new JLabel("Group:"));
			myPanel.add(groupBox);
			myPanel.add(new JLabel("Name:"));
			myPanel.add(nameField);
			myPanel.add(new JLabel("Priority:"));
			myPanel.add(prioBox);
			myPanel.add(new JLabel("Description:"));
			myPanel.add(descField);
			myPanel.add(new JLabel("Date:"));
			myPanel.add(dateField);
			myPanel.add(new JLabel("Alarm:"));
			myPanel.add(alarmField);

			while (!valid) {
				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Enter Task Info", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					Task newTask = null;

					// check if any of the fields were left blank
					if (nameField.getText().equals("")
							|| descField.getText().equals("")
							|| dateField.getText().equals("")
							|| alarmField.getText().equals("")) {
						JOptionPane.showMessageDialog(null,
								"Please make sure all fields are filled out.",
								"ERROR", JOptionPane.OK_OPTION);
						continue;
					}

					// check for a group to add the task to with the same name
					// as the given group
					for (int i = 0; i < groups.size(); i++) {
						if (groups.get(i).getName()
								.equals(groupBox.getSelectedItem())) {
							// create new task if possible with given input
							try {
								String name = nameField.getText();
								int priority = (int) prioBox.getSelectedItem();
								String desc = descField.getText();
								Date date = format.parse(dateField.getText());
								Date alarm = format.parse(alarmField.getText());

								newTask = new Task(name, priority, desc, date,
										alarm);
								groups.get(i).addTask(newTask);

								// re-generate pages for listView
								lv.generatePages(groups);

								// TESTING
								System.out.println("Created task: " + name
										+ " in group: "
										+ groupBox.getSelectedItem());
								valid = true;
								continue;
							} catch (ParseException e) {
								JOptionPane
										.showMessageDialog(
												null,
												"Invalid date format. Please try again.",
												"ERROR", JOptionPane.OK_OPTION);
								break;
							}
						}
					}
				} else if (result == JOptionPane.CANCEL_OPTION
						|| result == JOptionPane.CLOSED_OPTION) {
					valid = true;
					continue;
				}

				continue;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please create a group first.",
					"ERROR", JOptionPane.OK_OPTION);
		}
	}

	/*
	 * Creates a JOptionPane to get input from the user for adding a group. Adds
	 * the group and returns true if all fields were entered correctly and the
	 * group does not not already exist. Does nothing and returns false
	 * otherwise.
	 */
	public void addGroup() {
		boolean groupExists = false;
		boolean valid = false;

		// create pop-up dialogue fields
		JTextField nameField = new JTextField();
		JComboBox<Integer> prioBox = new JComboBox<Integer>();
		for (int i = 1; i <= 10; i++)
			prioBox.addItem(i);
		JTextField descField = new JTextField();

		// add components to JPanel for pop-up
		JPanel myPanel = new JPanel(new GridLayout(3, 1));
		myPanel.add(new JLabel("Name:"));
		myPanel.add(nameField);
		myPanel.add(new JLabel("Priority:"));
		myPanel.add(prioBox);
		myPanel.add(new JLabel("Description:"));
		myPanel.add(descField);

		while (!valid) {
			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Enter Group Info", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				Group newGroup = null;

				// check if any of the fields were left blank
				if (nameField.getText().equals("")
						|| descField.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Please make sure all fields are filled out.",
							"ERROR", JOptionPane.OK_OPTION);
					continue;
				}

				// check whether or not a group with the same name already
				// exists
				String gName = nameField.getText();
				for (int i = 0; i < groups.size(); i++) {
					if (groups.get(i).getName().equals(gName)) {
						groupExists = true;
						JOptionPane.showMessageDialog(null,
								"Group already exists.", "ERROR",
								JOptionPane.OK_OPTION);
						continue;
					}
				}

				// if the group does not exist, add it to the groups array
				if (!groupExists) {
					newGroup = new Group(nameField.getText(),
							(Integer) prioBox.getSelectedItem(),
							descField.getText());
					groups.add(newGroup);

					// re-generate pages for listView
					lv.generatePages(groups);

					// TESTING
					System.out.println("Group: " + nameField.getText()
							+ " created successfully.");
					valid = true;
					continue;
				}
			} else if (result == JOptionPane.CANCEL_OPTION
					|| result == JOptionPane.CLOSED_OPTION) {
				valid = true;
				continue;
			}

			JOptionPane.showMessageDialog(null,
					"Could not create group. Please try again.", "ERROR",
					JOptionPane.OK_OPTION);
			continue;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 * 
	 * Gets the state changed event from the JComboBox to switch to the
	 * appropriate view.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		switch ((String) e.getItem()) {
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

		CardLayout cl = (CardLayout) (cardPanel.getLayout());
		cl.show(cardPanel, (String) e.getItem());
	}

	public boolean importFile(String path)  {		
		boolean newGroup = true;
		String line = "";
		String linearr[];
		ArrayList<Group> temp = new ArrayList<Group>();
		Group g;
		Task t;
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		final File file = new File(path);

		BufferedReader inputStream = null;

		try {
			inputStream = new BufferedReader(new FileReader(file));

			while ((line = inputStream.readLine()) != null) {
				g = new Group();
				g.setName(inputStream.readLine().substring(6));
				g.setPriority(Integer.parseInt(inputStream.readLine()
						.substring(10)));
				g.setDescription(inputStream.readLine().substring(13));
				//System.out.println("1. " + inputStream.readLine());
				//System.out.println("2. " + inputStream.readLine());
				//System.out.println("3. " + inputStream.readLine());
				
				//System.out.println("SKIP -->" + inputStream.readLine());
				//line = inputStream.readLine();
				//System.out.println(line);
				inputStream.readLine();
				inputStream.readLine();
				while (true) {
					
					line = inputStream.readLine();
					//System.out.println("CHECK THIS -->" +line);
					if (line.compareTo(";") != 0) {
						//System.out.println("Entering tasks");
						
						t = new Task();
						//System.out.println("1. " + inputStream.readLine());
						//System.out.println("2. " + inputStream.readLine());
						//System.out.println("3. " + inputStream.readLine());
						t.setName(inputStream.readLine().substring(6));
						t.setPriority(Integer.parseInt(inputStream.readLine().substring(10)));
						t.setDescription(inputStream.readLine().substring(13));
						Date dueDate = null;
						try {
							dueDate = format.parse(inputStream.readLine().substring(10));
						} catch (ParseException e) {
						
						}
						t.setDueDate(dueDate);
						//System.out.println("SKIP -->" + inputStream.readLine());
						//System.out.println("SKIP -->" +inputStream.readLine());
						//inputStream.readLine();
						//inputStream.readLine();
						g.addTask(t);
					}
					else
						break;
				}

				temp.add(g);
				//inputStream.readLine();
			}
			groups = temp;
			inputStream.close();
			lv.generatePages(groups);
			return true;

		}

		catch (IOException e) {
			//System.out.println("Error loading location");
			//System.exit(0);
			return false;
		}

	}

	/*
	 * 
	 */
	public void save() {
		String fileName = "../save.txt";
		PrintWriter outputStream = null;
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		try {

			outputStream = new PrintWriter(fileName); // write

		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		for (int i = 0; i < groups.size(); i++) {
			outputStream.println("Group" + i);
			outputStream.println("Name- " + groups.get(i).getName());
			outputStream.println("Priority- " + groups.get(i).getPriority());
			outputStream.println("Description- "
					+ groups.get(i).getDescription());
			outputStream.println("TASKS:\n");
			for (int j = 0; j < groups.get(i).tasks.size(); j++) {
				outputStream.println("Task" + j);
				outputStream.println("Name- "
						+ groups.get(i).tasks.get(j).getName());
				outputStream.println("Priority- "
						+ groups.get(i).tasks.get(j).getPriority());
				outputStream.println("Description- "
						+ groups.get(i).tasks.get(j).getDescription());
				outputStream.println("Due Date- "
						+ format.format(groups.get(i).tasks.get(j).getDueDate()) );
				
			}
			outputStream.println(";");
		}
		outputStream.close();
	}
	/*
	 * 
	 */
	public void importFileDialogue() {
		// create pop-up button options
		JTextField filePath = new JTextField();
		JLabel label = new JLabel("File Path:");

		// add components to JPanel for pop-up
		JPanel myPanel = new JPanel(new GridLayout(2, 1));
		myPanel.add(label);
		myPanel.add(filePath);

		while (true) {
			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Enter File Path:", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if (filePath.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,
							"Please make sure the filepath is not empty.",
							"ERROR", JOptionPane.OK_OPTION);
					continue;
				} else {
					if (importFile(filePath.getText())) {
						break;
					} else {
						JOptionPane.showMessageDialog(null,
								"Could not find file. Please try again.",
								"ERROR", JOptionPane.OK_OPTION);
						continue;
					}
				}
			} else if (result == JOptionPane.CANCEL_OPTION
					|| result == JOptionPane.CLOSED_OPTION) {
				break;
			}
		}
	}

	

	/*
	 * 
	 * taskOrGroup - true to sort tasks, false to sort groups sortType - type of
	 * sort to implement
	 */
	public void sort(boolean taskOrGroup) {
		int sortType = -1;
		boolean valid = false;

		// pop-up task sort dialogue
		if (taskOrGroup) {
			// create pop-up button options
			ButtonGroup bg = new ButtonGroup();
			JRadioButton byName = new JRadioButton("Sort by name");
			JRadioButton byPrio = new JRadioButton("Sort by priority");
			JRadioButton byDueDate = new JRadioButton("Sort by due date");
			bg.add(byName);
			bg.add(byPrio);
			bg.add(byDueDate);

			// add components to JPanel for pop-up
			JPanel myPanel = new JPanel(new GridLayout(3, 1));
			myPanel.add(byName);
			myPanel.add(byPrio);
			myPanel.add(byDueDate);

			while (!valid) {
				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Enter Group Info", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					// get sort type
					if (byName.isSelected()) {
						sortType = 0;
					} else if (byPrio.isSelected()) {
						sortType = 1;
					} else if (byDueDate.isSelected()) {
						sortType = 2;
					}

					// check if nothing is selected
					if (sortType == -1)
						continue;

					// perform the sort
					for (int i = 0; i < groups.size(); i++)
						sortTasks(groups.get(i).tasks, sortType);
					lv.generatePages(groups);

					valid = true;
				} else if (result == JOptionPane.CANCEL_OPTION) {
					valid = true;
				}
			}
		} else {
			// create pop-up button options
			ButtonGroup bg = new ButtonGroup();
			JRadioButton byName = new JRadioButton("Sort by name");
			JRadioButton byPrio = new JRadioButton("Sort by priority");
			bg.add(byName);
			bg.add(byPrio);

			// add components to JPanel for pop-up
			JPanel myPanel = new JPanel(new GridLayout(2, 1));
			myPanel.add(byName);
			myPanel.add(byPrio);

			while (!valid) {
				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Enter Group Info", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					// get sort type
					if (byName.isSelected()) {
						sortType = 0;
					} else if (byPrio.isSelected()) {
						sortType = 1;
					}

					// check if nothing is selected
					if (sortType == -1)
						continue;

					// perform the sort
					/*
			    	 * 
			    	 */

					sortGroups(sortType);
					lv.generatePages(groups);

					valid = true;
				} else if (result == JOptionPane.CANCEL_OPTION
						|| result == JOptionPane.CLOSED_OPTION) {
					valid = true;
				}
			}
		}

	}

	public void sortGroups(int type) {
		switch (type) {

		case 0:
			Collections.sort(groups, sorter.new GroupNameComparator());
			break;
		case 1:
			Collections.sort(groups, sorter.new GroupPriorityComparator());
			break;

		}
	}

	/*
	 * 
	 */
	public void sortTasks(ArrayList<Task> t, int type) {
		switch (type) {

		case 0:
			Collections.sort(t, sorter.new TaskNameComparator());
			break;
		case 1:
			Collections.sort(t, sorter.new TaskPriorityComparator());
			break;
		case 2:
			Collections.sort(t, sorter.new TaskDueDateComparator());
			break;
		case 3:
			Collections.sort(t, sorter.new TaskAlarmDateComparator());
			break;

		}

		// t.sort(null);
	}

	/*
	 * Action Listener to get menu item clicks and act on them.
	 */
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// if "Open" is clicked under "File"
			if (e.getSource().equals(open)) {
				System.out.println("open");
			}
			// if "Save" is clicked under "File"
			else if (e.getSource().equals(save)) {
				save();
			}
			// if "Import" is clicked under "File"
			else if (e.getSource().equals(impt)) {
				importFileDialogue();
			}
			// if "Exit" is clicked under "File"
			else if (e.getSource().equals(exit)) {
				System.exit(0);
			}
			// if "Add Task" is clicked under "Add"
			else if (e.getSource().equals(addTask)) {
				addTask();
			}
			// if "Add Groups" is clicked under "Add"
			else if (e.getSource().equals(addGroup)) {
				addGroup();
			}
			// if "Sort Tasks" is clicked under "Sort"
			else if (e.getSource().equals(sortTasks)) {
				sort(true);
			}
			// if "Sort Groups" is clicked under "Sort"
			else if (e.getSource().equals(sortGroups)) {
				sort(false);
			}
		}
	}
}