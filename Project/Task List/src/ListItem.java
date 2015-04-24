import java.awt.geom.Rectangle2D;

public class ListItem extends Rectangle2D.Double{
	
	Group myGroup;
	Task myTask;
	
	public ListItem (double x, double y, double w, double h, Group g, Task t) {
		super(x, y, w, h);
		myGroup = g;
		myTask = t;
	}
}
