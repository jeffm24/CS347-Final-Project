import static org.junit.Assert.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.Test;

public class TaskListTest {
	DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	ArrayList<Group> g = new ArrayList<Group>();
	TaskList t = new TaskList(g);
	@SuppressWarnings("deprecation")
	Date d1 = new Date("1990/04/22");
	@SuppressWarnings("deprecation")
	Date a1 = new Date("1990/04/22");
	@SuppressWarnings("deprecation")
	Date d2 = new Date("1990/04/22");;
	@SuppressWarnings("deprecation")
	Date a2 = new Date("1990/04/22");
	Sorting sorter = new Sorting();
	Task t1 = new Task("Final Iteration", 2,
			"Write Unit Test Cases for Project", d1, a1);
	Task t2 = new Task("Group Presentation", 1, "Present Project", d2, a2);
	Group g1 = new Group("CS392", 2, "Systems Programming");

	@Test
	//testing save feature
	public void testSave() {
		g1.addTask(t1);
		g1.addTask(t2);
		g.add(g1);
		assertEquals(true, t.save("../save.txt"));
	}

	@Test
	//testing import feature
	public void testImport() {
		g1.addTask(t1);
		g1.addTask(t2);
		g.add(g1);
		try{
			assertEquals(true,t.importFile("../save.txt"));
		}
		catch(Exception e){
			
		}
		for(int i = 0; i < t.groups.size(); i++){
			assertEquals(t.groups.get(i).getName(),g.get(i).getName());
			assertEquals(t.groups.get(i).getDescription(),g.get(i).getDescription());
			assertEquals(t.groups.get(i).getPriority(),g.get(i).getPriority());
			for(int j = 0; j < t.groups.get(i).tasks.size(); j++)
			{
				assertEquals(t.groups.get(i).tasks.get(j).getName(),g.get(i).tasks.get(j).getName());
				assertEquals(t.groups.get(i).tasks.get(j).getDescription(),g.get(i).tasks.get(j).getDescription());
				assertEquals(t.groups.get(i).tasks.get(j).getPriority(),g.get(i).tasks.get(j).getPriority());
				assertEquals(t.groups.get(i).tasks.get(j).getDueDate(),g.get(i).tasks.get(j).getDueDate());
				assertEquals(t.groups.get(i).tasks.get(j).getAlarmDate(),g.get(i).tasks.get(j).getAlarmDate());
			}
		}
	
		
	}
}
