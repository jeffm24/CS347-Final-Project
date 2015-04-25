import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Task {
	private String name;
	private int priority;
	private String description;
	private Date dueDate;
	private Date alarmDate;
	private Timer t;
	private TimerTask alarm;
	
	public Task(){
		name = "";
		priority = -1;
		description = "";
		dueDate = new Date();
		alarmDate = new Date();
		t = new Timer();
		alarm = null;
	}
	
	public Task(String n, int p, String d, Date due, Date a){
		name = n;
		priority = p;
		description = d;
		dueDate = due;
		alarmDate = a;
		//t = tim;
		//alarm = task;
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
	}
	public Timer getT() {
		return t;
	}
	public void setT(Timer t) {
		this.t = t;
	}
	public TimerTask getAlarm() {
		return alarm;
	}
	public void setAlarm(TimerTask alarm) {
		this.alarm = alarm;
	}
}
