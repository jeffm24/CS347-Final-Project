import java.awt.*;
import java.awt.event.*;
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
		
		//initialize the menu bar
		initMenuBar();	
		
		lv = new ListView();
		lv.setSize(getWidth(), getHeight());
		gv = new GridView();
		gv.setSize(getWidth(), getHeight());
		cv = new CalendarView();
		cv.setSize(getWidth(), getHeight());
		
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
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
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
				System.out.println("add task");
			} 
			//if "Add Groups" is clicked under "Add"
			else if (e.getSource().equals(addGroup)) {
				System.out.println("add group");
			}
		}
	}
}


