import java.util.Comparator;

public class Sorting {

	public class TaskNameComparator implements Comparator<Task> {

		@Override
		public int compare(Task t1, Task t2) {
			return t1.getName().compareTo(t2.getName());
		}

	}

	public class TaskPriorityComparator implements Comparator<Task> {

		@Override
		public int compare(Task t1, Task t2) {
			return t1.getPriority() - t2.getPriority();
		}

	}

	public class TaskDueDateComparator implements Comparator<Task> {

		@Override
		public int compare(Task t1, Task t2) {
			return t1.getDueDate().compareTo(t2.getDueDate());
		}

	}
	public class TaskAlarmDateComparator implements Comparator<Task> {

		@Override
		public int compare(Task t1, Task t2) {
			return t1.getAlarmDate().compareTo(t2.getAlarmDate());
		}

	}

}