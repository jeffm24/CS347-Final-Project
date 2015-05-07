import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;


public class SortingTest {
	Date d1 = new Date(1003);
	Date d2 = new Date(1000);
	Date a1 = new Date(2000);
	Date a2 = new Date(3000);
	Sorting sorter = new Sorting();
	Task t1 = new Task("Final Iteration",2,"Write Unit Test Cases for Project",d1,a1);
	Task t2 = new Task("Group Presentation",1,"Present Project",d2,a2);
	Task Tasks[] = {t1 , t2};
	Group g1 = new Group("CS392", 2, "Systems Programming");
	Group g2 = new Group("CS370", 5, "Team Programming");
	Group Groups[] = {g1 , g2};

	@Test
	public void testTaskNameComparator() {
		Task test[] = {t1,t2};
		Arrays.sort(Tasks, sorter.new TaskNameComparator());
		assertArrayEquals(Tasks,test);
		
	}

	@Test
	public void testTaskPriorityComparator() {
		Task test[] = {t2,t1};
		Arrays.sort(Tasks, sorter.new TaskPriorityComparator());
		assertArrayEquals(Tasks,test);
	}

	@Test
	public void testTaskDueDateComparator() {
		Task test[] = {t2,t1};
		Arrays.sort(Tasks, sorter.new TaskDueDateComparator());
		assertArrayEquals(Tasks,test);
	}

	@Test
	public void testTaskAlarmDateComparator() {
		Task test[] = {t1,t2};
		Arrays.sort(Tasks, sorter.new TaskAlarmDateComparator());
		assertArrayEquals(Tasks,test);
	}

	@Test
	public void testGroupNameComparator() {
		Group test[] = {g2,g1};
		Arrays.sort(Groups, sorter.new GroupNameComparator());
		assertArrayEquals(Groups,test);
	}

	@Test
	public void testGroupPriorityComparator() {
		Group test[] = {g1,g2};
		Arrays.sort(Groups, sorter.new GroupPriorityComparator());
		assertArrayEquals(Groups,test);
	}

}
