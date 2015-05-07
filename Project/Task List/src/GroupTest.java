import static org.junit.Assert.*;

import org.junit.Test;


public class GroupTest {

	Group g = new Group();
	Task t1 = new Task("Final Iteration",2,"Write Unit Test Cases for Project");
	Task t2 = new Task("Group Presentation",1,"Present Project");
	
	@Test
	public void testaddTask() {
		assertEquals(-1,g.addTask(null));
		assertEquals(1,g.addTask(t1));
		
	}
	
	@Test
	public void testremoveTask() {
		assertEquals(false,g.removeTask(t1));
		g.addTask(t1);
		assertEquals(true,g.removeTask(t1));
	}
	
	@Test
	public void testgetTask() {
		assertEquals(null,g.getTask("Final Iteration"));
		g.addTask(t1);
		assertEquals(t1,g.getTask("Final Iteration"));
	}
	@Test
	public void testSettersGetters() {
		g.setName("CS347");
		g.setPriority(1);
		g.setDescription("Best Class Ever");
		
		assertEquals("CS347", g.getName());
		assertEquals(1, g.getPriority());
		assertEquals("Best Class Ever", g.getDescription());
	}
	
	
}
