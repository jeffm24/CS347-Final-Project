import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.*;
import java.util.ArrayList;

public class ListView extends JPanel implements MouseInputListener{
	
	Rectangle2D.Double right, left;
	ArrayList<ArrayList<ListItem>> pages;
	TaskList parent;
	int currentPage;
	int xPoints[], yPoints[];
	boolean hasLeft, hasRight;
	boolean initLR;
	
	/*
	 * Constructor.
	 */
	public ListView(TaskList p) {		
		xPoints = new int[3];
		yPoints = new int[3];
		initLR = false;
		
		pages = new ArrayList<ArrayList<ListItem>>();
		currentPage = 0;
		
		parent = p;
		
		this.addMouseListener(this);
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);

        g2.setRenderingHints(rh);

        //Draw Current Group name (if there is one)
        g2.setFont(new Font("Ariel", Font.BOLD, 18));
        String pageTitle;
        if (pages.size() == 0) {
        	pageTitle = "No Groups to Show";
        } else {
        	pageTitle = parent.groups.get(currentPage).getName();
        }
        g2.drawString(pageTitle, this.getWidth() / 2 - (g2.getFontMetrics().stringWidth(pageTitle) / 2), 16);
        	
        //draw the left and right buttons
        drawLRButtons(g2);
        
        //draw all of tasks on the current page
        for (int i = 0 ; pages.size() != 0 && i < pages.get(currentPage).size() ; i++) {
        	pages.get(currentPage).get(i).drawSelf(g2);
        }
    }
    
    /*
     * Instructions for drawing the left/right buttons
     */
    public void drawLRButtons(Graphics2D g2) {
    	if (!initLR) {
    		right = new Rectangle2D.Double(this.getWidth() - 35, this.getHeight() / 2 - 50, 35, 100);
    		left = new Rectangle2D.Double(0, this.getHeight() / 2 - 50, 35, 100);
    		initLR = true;
    	}
    	
        //Right Button
    	g2.setColor(new Color(0, 0, 0, 155));
    	g2.fill(right); 
    	
    	if (currentPage < pages.size() - 1)
    		g2.setColor(Color.WHITE);
    	else
    		g2.setColor(Color.GRAY);
    	xPoints[0] = (int)right.getX() + 4;
    	xPoints[1] = (int)right.getX() + 4;
    	xPoints[2] = this.getWidth() - 4;
    	yPoints[0] = (int)right.getY() + (int)right.getHeight() / 5;
    	yPoints[1] = (int)right.getY() + ((int)right.getHeight() / 5) * 4;
    	yPoints[2] = (int)right.getY() + (int)right.getHeight() / 2;
    	g2.fillPolygon(xPoints, yPoints, 3);
    	
    	g2.setColor(Color.BLACK);
    	g2.drawPolygon(xPoints, yPoints, 3);
    	g2.draw(right);
    	
    	//Left Button
    	g2.setColor(new Color(0, 0, 0, 155));
    	g2.fill(left);
    	
    	if (currentPage > 0)
    		g2.setColor(Color.WHITE);
    	else
    		g2.setColor(Color.GRAY);
    	xPoints[0] = (int)left.getWidth() - 4;
    	xPoints[1] = (int)left.getWidth() - 4;
    	xPoints[2] = 4;
    	yPoints[0] = (int)left.getY() + (int)left.getHeight() / 5;
    	yPoints[1] = (int)left.getY() + ((int)left.getHeight() / 5) * 4;
    	yPoints[2] = (int)left.getY() + (int)left.getHeight() / 2;
    	g2.fillPolygon(xPoints, yPoints, 3);
    	
    	g2.setColor(Color.BLACK);
    	g2.drawPolygon(xPoints, yPoints, 3);
    	g2.draw(left);
    }
    
    /*
     * Populates the pages array
     */
    public void generatePages(ArrayList<Group> groups) {
    	int i, j;
    	int x, y, w, h;
    	
    	//clear out the old pages arraylist
    	pages.clear();
    	
    	//add a page for every group
    	for (i = 0 ; i < groups.size() ; i++) {
    		pages.add(new ArrayList<ListItem>());
    		
    		//for every task in the current group, create a ListItem with the proper positioning
    		for (x = 40, y = 30, w = 300, h = 50, j = 0 ; j < groups.get(i).tasks.size() ; j++) {
    			pages.get(i).add(new ListItem(x, y, w, h, groups.get(i), groups.get(i).tasks.get(j)));
    			
    			//if placing the next task would go out of bounds (of the window), wrap to the next column
    			if (y + h + 10 + h > this.getHeight()) {
    				y = 30;
    				x += w + 10;
    			} else {
    				y += h + 10;
    			}
    		}
    	}
    	this.repaint();
    }
    
    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
	@Override
	public void mouseClicked(MouseEvent e) {
		//if the right button is clicked, move page right (if possible)
		if (right.contains(e.getPoint())) {
			System.out.println("Right button clicked");
			if (currentPage < pages.size() - 1) {
				currentPage++;
				repaint();
			}
		//if the left button is clicked, move page left (if possible)
		} else if (left.contains(e.getPoint())) {
			System.out.println("Left button clicked");
			if (currentPage > 0) {
				currentPage--;
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}  
}
