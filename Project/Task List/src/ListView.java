import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListView extends JPanel implements MouseInputListener, ActionListener {

	Rectangle2D.Double right, left;
	Rectangle2D.Double titleBorder;
	ArrayList<ArrayList<ListItem>> listItems;
	TaskList parent;
	int currentPage;
	int xPoints[], yPoints[];
	boolean initLR;
	JButton removeTask, removeGroup;
	Task openedTask;
	
	/*
	 * Constructor.
	 */
	public ListView(TaskList p) {
		xPoints = new int[3];
		yPoints = new int[3];
		initLR = false;

		listItems = new ArrayList<ArrayList<ListItem>>();
		currentPage = 0;

		parent = p;

		this.addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2.setRenderingHints(rh);

		// Draw Current Group name (if there is one)
		String pageTitle;
		g2.setFont(new Font("Ariel", Font.BOLD, 18));
		if (listItems.size() == 0) {
			pageTitle = "No Groups to Show";
		} else {
			pageTitle = parent.groups.get(currentPage).getName();
		}
		g2.drawString(pageTitle, this.getWidth() / 2
				- (g2.getFontMetrics().stringWidth(pageTitle) / 2), 16);
		titleBorder = new Rectangle2D.Double(this.getWidth() / 2
				- (g2.getFontMetrics().stringWidth(pageTitle) / 2), 0, g2
				.getFontMetrics().stringWidth(pageTitle), g2.getFontMetrics()
				.getHeight());

		// draw the left and right buttons
		drawLRButtons(g2);

		// draw all of tasks on the current page
		for (int i = 0; listItems.size() != 0
				&& i < listItems.get(currentPage).size(); i++) {
			listItems.get(currentPage).get(i).drawSelf(g2);
		}

		// recreate listItems for window resize
		generatePages(parent.groups);
	}

	/*
	 * Instructions for drawing the left/right buttons
	 */
	public void drawLRButtons(Graphics2D g2) {
		right = new Rectangle2D.Double(this.getWidth() - 35,
				this.getHeight() / 2 - 50, 35, 100);
		left = new Rectangle2D.Double(0, this.getHeight() / 2 - 50, 35, 100);

		// Right Button
		g2.setColor(new Color(0, 0, 0, 155));
		g2.fill(right);

		if (currentPage < listItems.size() - 1)
			g2.setColor(Color.WHITE);
		else
			g2.setColor(Color.GRAY);
		xPoints[0] = (int) right.getX() + 4;
		xPoints[1] = (int) right.getX() + 4;
		xPoints[2] = this.getWidth() - 4;
		yPoints[0] = (int) right.getY() + (int) right.getHeight() / 5;
		yPoints[1] = (int) right.getY() + ((int) right.getHeight() / 5) * 4;
		yPoints[2] = (int) right.getY() + (int) right.getHeight() / 2;
		g2.fillPolygon(xPoints, yPoints, 3);

		g2.setColor(Color.BLACK);
		g2.drawPolygon(xPoints, yPoints, 3);
		g2.draw(right);

		// Left Button
		g2.setColor(new Color(0, 0, 0, 155));
		g2.fill(left);

		if (currentPage > 0)
			g2.setColor(Color.WHITE);
		else
			g2.setColor(Color.GRAY);
		xPoints[0] = (int) left.getWidth() - 4;
		xPoints[1] = (int) left.getWidth() - 4;
		xPoints[2] = 4;
		yPoints[0] = (int) left.getY() + (int) left.getHeight() / 5;
		yPoints[1] = (int) left.getY() + ((int) left.getHeight() / 5) * 4;
		yPoints[2] = (int) left.getY() + (int) left.getHeight() / 2;
		g2.fillPolygon(xPoints, yPoints, 3);

		g2.setColor(Color.BLACK);
		g2.drawPolygon(xPoints, yPoints, 3);
		g2.draw(left);
	}

	/*
	 * Populates the listItems 2D ArrayList with all of the current tasks for
	 * each group.
	 */
	public void generatePages(ArrayList<Group> groups) {
		int i, j;
		double x, y, w, h;

		// clear out the old pages arraylist
		listItems.clear();

		// add a page for every group
		for (i = 0; i < groups.size(); i++) {
			listItems.add(new ArrayList<ListItem>());

			x = left.width + 10;
			y = titleBorder.getHeight();
			w = ((this.getWidth() - (left.getWidth() + right.getWidth()) - 20) / 3)
					/ ((groups.get(i).tasks.size() / 30) + 1);
			h = ((this.getHeight() - titleBorder.getHeight() - 10) / 11)
					/ ((groups.get(i).tasks.size() / 30) + 1);

			// for every task in the current group, create a ListItem with the
			// proper positioning
			for (j = 0; j < groups.get(i).tasks.size(); j++) {
				listItems.get(i).add(
						new ListItem(x, y, w, h, groups.get(i),
								groups.get(i).tasks.get(j)));

				// if placing the next task would go out of bounds (of the
				// window), wrap to the next column
				if (y + h + 10 + h > this.getHeight()) {
					y = titleBorder.getHeight();
					x += w + 10;
				} else {
					y += h + 10;
				}
			}
		}
		this.repaint();
	}

	/*
	 * Opens up a JOptionPane with info about the given task and the option to
	 * edit it.
	 */
	public void expandTaskInfo(ListItem li) {
		openedTask = li.myTask;
		
		// create pop-up dialogue fields
		JComboBox<String> groupBox = new JComboBox<String>();
		for (int i = 0; i < parent.groups.size(); i++)
			groupBox.addItem(parent.groups.get(i).getName());
		groupBox.setSelectedItem(li.myGroup.getName());

		JTextField nameField = new JTextField();
		nameField.setText(li.myTask.getName());

		JComboBox<Integer> prioBox = new JComboBox<Integer>();
		for (int i = 1; i <= 10; i++)
			prioBox.addItem(i);
		prioBox.setSelectedItem((Integer) li.myTask.getPriority());

		JTextField descField = new JTextField();
		descField.setText(li.myTask.getDescription());

		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		JFormattedTextField dateField = new JFormattedTextField(format);
		dateField.setText(format.format(li.myTask.getDueDate()));

		JFormattedTextField alarmField = new JFormattedTextField(format);
		alarmField.setText(format.format(li.myTask.getAlarmDate()));
		
		removeTask = new JButton("Remove Task");
		removeTask.addActionListener(this);

		// add components to JPanel for pop-up
		JPanel myPanel = new JPanel(new GridLayout(7, 1));
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
		myPanel.add(removeTask);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Edit Task Info", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			while (true) {
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

				// set new info if possible with given input
				try {
					Date dueDate = format.parse(dateField.getText());
					Date alarmDate = format.parse(alarmField.getText());

					// set new task info
					li.myTask.setName(nameField.getText());
					li.myTask.setPriority((int) prioBox.getSelectedItem());
					li.myTask.setDescription(descField.getText());
					li.myTask.setDueDate(dueDate);
					li.myTask.setAlarmDate(alarmDate);

					// switch groups if necessary
					if (!li.myGroup.getName().equals(groupBox.getSelectedItem())) {
						Group newGroup = null;
						String newGroupName = (String) groupBox
								.getSelectedItem();
						int i;
						for (i = 0; i < parent.groups.size(); i++) {
							if (parent.groups.get(i).getName()
									.equals(newGroupName)) {
								newGroup = parent.groups.get(i);
								break;
							}
						}

						li.myGroup.removeTask(li.myTask);
						listItems.get(currentPage).remove(li);
						li.myGroup = newGroup;
						li.myGroup.addTask(li.myTask);
						listItems.get(i).add(li);	
					}
					
					//re-generate group pages with new tasks in proper sorting order
					for (int i = 0 ; i < parent.groups.size() ; i++) {
						parent.sortTasks(parent.groups.get(i).tasks, parent.currentTaskSort);
					}
					generatePages(parent.groups);
					

					break;
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null,
							"Invalid date format. Please try again.", "ERROR",
							JOptionPane.OK_OPTION);
				}
			}
		}
		
		openedTask = null;
	}

	/*
	 * Opens up a JOptionPane with info about the current group and the option
	 * to edit it.
	 */
	public void expandGroupInfo() {
		// create pop-up dialogue fields
		JTextField nameField = new JTextField();
		nameField.setText(parent.groups.get(currentPage).getName());
		JComboBox<Integer> prioBox = new JComboBox<Integer>();
		for (int i = 1; i <= 10; i++)
			prioBox.addItem(i);
		prioBox.setSelectedItem(parent.groups.get(currentPage).getPriority());
		JTextField descField = new JTextField();
		descField.setText(parent.groups.get(currentPage).getDescription());
		
		removeGroup = new JButton("Remove Group");
		removeGroup.addActionListener(this);

		// add components to JPanel for pop-up
		JPanel myPanel = new JPanel(new GridLayout(4, 1));
		myPanel.add(new JLabel("Name:"));
		myPanel.add(nameField);
		myPanel.add(new JLabel("Priority:"));
		myPanel.add(prioBox);
		myPanel.add(new JLabel("Description:"));
		myPanel.add(descField);
		myPanel.add(removeGroup);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Edit Group Info", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			while (true) {
				// check if any of the fields were left blank
				if (nameField.getText().equals("")
						|| descField.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Please make sure all fields are filled out.",
							"ERROR", JOptionPane.OK_OPTION);
					continue;
				}

				// set new group info
				parent.groups.get(currentPage).setName(nameField.getText());
				parent.groups.get(currentPage).setPriority(
						(int) prioBox.getSelectedItem());
				parent.groups.get(currentPage).setDescription(
						descField.getText());

				// re-generate new pages with groups sorted according to new info
				parent.sortGroups(parent.currentGroupSort);
				generatePages(parent.groups);

				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// if the right button is clicked, move page right (if possible)
		if (right.contains(e.getPoint())) {
			if (currentPage < listItems.size() - 1) {
				currentPage++;
				repaint();
			}
			// if the left button is clicked, move page left (if possible)
		} else if (left.contains(e.getPoint())) {
			if (currentPage > 0) {
				currentPage--;
				repaint();
			}
			// if the current title is clicked, call the expandGroupInfo()
			// method to open up a JOptionPane for ediitng
		} else if (parent.groups.size() != 0
				&& titleBorder.contains(e.getPoint())) {
			expandGroupInfo();
			// if any of the tasks are clicked, call the expandTaskInfo() method
			// to open up a JOptionPane for editing
		} else if (!listItems.isEmpty()) {
			for (int i = 0; i < listItems.get(currentPage).size(); i++) {
				if (listItems.get(currentPage).get(i).contains(e.getPoint())) {
					expandTaskInfo(listItems.get(currentPage).get(i));
					break;
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	/*
	 * For capturing remove button clicks
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(removeTask)) {
			//remove the opened task
			parent.removeTask(parent.groups.get(currentPage), openedTask);
			
			//dispose of JOptionPane pop-up
			((JDialog)((JButton)e.getSource()).getRootPane().getParent()).dispose();
		} else if (e.getSource().equals(removeGroup)) {
			//remove the current group
			parent.removeGroup(parent.groups.get(currentPage));
			if (currentPage > 0)
				currentPage--;
			
			//dispose of JOptionPane pop-up
			((JDialog)((JButton)e.getSource()).getRootPane().getParent()).dispose();
		}
	}
}