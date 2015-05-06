import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class Task {
	private String name;
	private int priority;
	private String description;
	private Date dueDate;
	private Date alarmDate;
	private Timer timer;
	
	public Task(){
		name = "";
		priority = -1;
		description = "";
		dueDate = new Date();
		alarmDate = new Date();
		timer = new Timer();
	}
	
	public Task(String n, int p, String d, Date due, Date a){
		name = n;
		priority = p;
		description = d;
		dueDate = due;
		alarmDate = a;
		timer = new Timer();
		timer.schedule(new RemindTask(), alarmDate);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Date getAlarmDate() {
		return alarmDate;
	}
	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
		timer.schedule(new RemindTask(), alarmDate);
	}
	
	//class for running a reminder notification when the timer goes off
	private class RemindTask extends TimerTask {
		
		public void run() {
			JOptionPane.showMessageDialog(null, name + " is due " + dueDate, "ALARM", JOptionPane.OK_OPTION);			
		}
		
	}
}
