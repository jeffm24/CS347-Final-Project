import java.util.ArrayList;
import javax.swing.JPanel;

public abstract class View extends JPanel {
	
	static int currentSort;
	static ArrayList<Group> groups;
	
	public View() {
		groups = new ArrayList<Group>();
	}
}
