import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;


public class TaskTest {

	Task t1 = new Task();
	Date due = null;
	Date alarm = null;
	
	@Test
	public void testSettersGetters() {
		t1.setName("Final Iteration");
		t1.setPriority(2);
		t1.setDescription("Write Unit Test Cases for Project");
		t1.setDueDate(due);
		t1.setAlarmDate(alarm);
		
		assertEquals("Final Iteration", t1.getName());
		assertEquals(2, t1.getPriority());
		assertEquals("Write Unit Test Cases for Project", t1.getDescription());
		assertEquals(null, t1.getDueDate());
		assertEquals(null, t1.getAlarmDate());
	}
	

}
