import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Test;

public class TaskListTest {
	ArrayList<Group> g = new ArrayList<Group>();
	TaskList t = new TaskList(g);
	Date d1 = new Date(1003);
	Date d2 = new Date(1000);
	Date a1 = new Date(2000);
	Date a2 = new Date(3000);
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
		assertEquals(g,t.groups);
	}
}
