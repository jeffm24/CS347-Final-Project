import java.util.ArrayList;

public class Group {
	private String name;
	private int priority;
	private String description;
	ArrayList<Task> tasks;
	
	public Group(){
		name = "";
		priority = -1;
		description = "";
		tasks = new ArrayList<Task>();
	}
	
	public Group(String n, int p, String d){
		name = n;
		priority = p;
		description = d;
		tasks = new ArrayList<Task>();
	}
	
	public int addTask(Task t){
		if(t != null){
			tasks.add(t);
			return tasks.size();
		}
		return -1;
	}
	
	public void removeTask(Task t) {
		if (t != null) {
			for (int i = 0 ; i < tasks.size() ; i++) {
				if (tasks.get(i).getName().equals(t.getName())) {
					tasks.remove(t);
				}
			}
		}
	}
	
	public Task getTask(String name){
		int i;
		for(i = 0; i < tasks.size(); i++ )
			if(tasks.get(i).getName().equalsIgnoreCase(name))
				return tasks.get(i);
		
		return null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
