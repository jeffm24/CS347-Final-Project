import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TaskListMain {

	public static void main(String[] args) {
		TaskList tl = new TaskList("Task List");
		if (args.length != 0 && args[0].equals("test")) {

			String line = "";
			BufferedReader inputStream = null;
			final File file = new File(args[1]);
			try {
				File save = new File("../save.txt");
				File save2 = new File("../save.txt");

				inputStream = new BufferedReader(new FileReader(file));
				while ((line = inputStream.readLine()) != null) {
					save.delete();
					save2.delete();
					switch (line) {
					case "save":
						// FORMAT: "save"
						if (tl.save("../save.txt")) {
							System.out.println("Saved file successfully.");
						} else {
							System.out.println("File could not be saved.");
						}

						break;

					case "import":
						// FORMAT: "import [filePath]
						String importPath = inputStream.readLine();

						if (importPath.length() == 0) {
							System.out
									.println("Please make sure the filepath is not empty.");

						} else {
							if (tl.importFile(importPath)) {
								System.out.println("Successfully Imported "
										+ line);

							} else {
								System.out
										.println("Could not find file. Please try again.");

							}
						}
						break;
					case "export":
						// FORMAT: "export [filePath]"
						String exportPath = inputStream.readLine();

						if (exportPath.length() == 0) {
							System.out
									.println("Please make sure the filepath is not empty.");
							break;
						} else {
							if (tl.save(exportPath)) {
								System.out
										.println("Exported file successfully.");
								break;
							} else {
								System.out
										.println("Could not find file. Please try again.");
								break;
							}
						}
					case "exit":
						// FORMAT: "exit"
						System.out.println("Exiting Program.");
						System.exit(0);
						break;
					case "addtask":
						// FORMAT:
						// "addtask [groupName] [taskName] [priority] [description] [2016/04/14] [2016/04/15]"
						if (tl.groups.size() != 0) {
							String groupName = inputStream.readLine();
							for (int i = 0; i < tl.groups.size(); i++) {
								if (tl.groups.get(i).getName()
										.equals(groupName)) {
									// create new task if possible with given
									// input
									try {
										DateFormat format = new SimpleDateFormat(
												"yyyy/MM/dd");
										String name = inputStream.readLine();
										int priority = Integer
												.parseInt(inputStream
														.readLine());
										String desc = inputStream.readLine();
										Date date = format.parse(inputStream
												.readLine());
										Date alarm = format.parse(inputStream
												.readLine());

										// check if any of the fields were left
										// blank
										if (name.equals("") || desc.equals("")
												|| date.equals("")
												|| alarm.equals("")) {
											System.out
													.println("Please make sure all fields are filled out.");
											break;
										}

										Task newTask = new Task(name, priority,
												desc, date, alarm);
										tl.groups.get(i).addTask(newTask);

										// sort the group that the new task was
										// added to
										// according to current sort
										tl.sortTasks(tl.groups.get(i).tasks,
												tl.currentTaskSort);

										// TESTING
										System.out.println("Created task: "
												+ name + " in group: "
												+ tl.groups.get(i).getName());
										break;
									} catch (ParseException e) {
										System.out
												.println("Invalid date format. Please try again.");
										break;
									}
								}
							}
						} else {
							System.out.println("Please create a group first.");
							break;
						}
						break;
					case "addgroup":
						// FORMAT:
						// "addgroup [groupName] [priority] [description]"
						Group newGroup = null;
						boolean groupExists = false;

						String name = inputStream.readLine();
						int priority = Integer.parseInt(inputStream.readLine());
						String desc = inputStream.readLine();

						// check if any of the fields were left blank
						if (name.equals("") || desc.equals("")) {
							System.out
									.println("Please make sure all fields are filled out.");
							break;
						}

						// check whether or not a group with the same name
						// already
						// exists
						for (int i = 0; i < tl.groups.size(); i++) {
							if (tl.groups.get(i).getName().equals(name)) {
								groupExists = true;
								System.out.println("Group already exists.");
								break;
							}
						}

						// if the group does not exist, add it to the groups
						// array
						if (!groupExists) {
							newGroup = new Group(name, priority, desc);
							tl.groups.add(newGroup);

							// re-sort groups according to current group sort
							tl.sortGroups(tl.currentGroupSort);

							// TESTING
							System.out.println("Group: " + newGroup.getName()
									+ " created successfully.");
						}
						break;
					case "removetask":
						String groupname = inputStream.readLine();
						String taskname= inputStream.readLine();

						int grp,tsk;
						if((grp = tl.findGroup(groupname)) != -1){
							if((tsk = tl.findTask(grp, taskname)) != -1){
								tl.groups.get(grp).tasks.remove(tsk);
								System.out.println("Removed task: "
										+ taskname + " in group: "
										+ groupname);
							}
							else{
								System.out.println("Task doesn't exist");
							}
						}
						else{
							System.out.println("Group doesn't exist");
						}
						
						break;
					case "removegroup":

						String groupName = inputStream.readLine();
						int g = tl.findGroup(groupName);
						if(g != -1){
							tl.groups.remove(g);
							System.out.println(groupName + " has been removed.");
						}
						else{
							System.out.println("Group doesn't exist");
						}

						break;
					case "sorttasks":
						// FORMAT:
						// "sorttasks [0-alphabetical / 1-priority / 2-due date]"
						int sortType = Integer.parseInt(inputStream.readLine());

						switch (sortType) {
						case 0:
							for (int i = 0; i < tl.groups.size(); i++)
								tl.sortTasks(tl.groups.get(i).tasks, 0);
							System.out
									.println("Tasks sorted in alphabetical order.");
							break;
						case 1:
							for (int i = 0; i < tl.groups.size(); i++)
								tl.sortTasks(tl.groups.get(i).tasks, 1);
							System.out.println("Tasks sorted by priority.");
							break;
						case 3:
							for (int i = 0; i < tl.groups.size(); i++)
								tl.sortTasks(tl.groups.get(i).tasks, 0);
							System.out.println("Tasks sorted by due date.");
							break;
						}

						break;
					case "sortgroups":
						// FORMAT: "sortgroups [0-alphabetical / 1-priority]"
						int sortType2 = Integer
								.parseInt(inputStream.readLine());

						switch (sortType2) {
						case 0:
							tl.sortGroups(0);
							System.out
									.println("Groups sorted in alphabetical order.");
							break;
						case 1:
							tl.sortGroups(1);
							System.out.println("Groups sorted by priority.");
							break;
						}
						break;

					}
				}
			} catch (Exception e) {

			}
		}
	}
}
