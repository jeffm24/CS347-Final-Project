import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics2D;

/*
 * Graphical representation of a Task for ListView and GridView.
 */
public class ListItem extends Rectangle2D.Double{
	
	Group myGroup;
	Task myTask;
	
	public ListItem (double x, double y, double w, double h, Group g, Task t) {
		super(x, y, w, h);
		myGroup = g;
		myTask = t;
	}
	
	public Group getGroup() {
		return myGroup;
	}
	
	public Task getTask() {
		return myTask;
	}
	
	public void drawSelf(Graphics2D g2) {
		g2.setColor(new Color(0, 0, 0, 155));
		g2.fill(this);
		g2.setColor(Color.BLACK);
		g2.draw(this);
		g2.setColor(Color.WHITE);
		g2.drawString(myTask.getName(), (int)this.getCenterX(), (int)this.getCenterY());
	}
}
