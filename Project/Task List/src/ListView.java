import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.*;
import java.util.ArrayList;

public class ListView extends View implements MouseInputListener{
	
	Rectangle2D.Double right, left;
	ArrayList<ArrayList<Rectangle2D.Double>> pages;
	int currentPage;
	int xPoints[], yPoints[];
	boolean hasLeft, hasRight;
	boolean initLR;
	
	/*
	 * Constructor.
	 */
	public ListView() {		
		xPoints = new int[3];
		yPoints = new int[3];
		initLR = false;
		
		pages = new ArrayList<ArrayList<Rectangle2D.Double>>();
		currentPage = 0;
		
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

        drawLRButtons(g2);
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
    	
    	if (currentPage < pages.size())
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
     * Draws the current page 
     */
    public void drawCurrentPage(Graphics2D g2) {
    	
    }
    
    /*
     * Populates the pages array
     */
    public void generatePages(ArrayList<Group> groups) {
    	
    }
    
    /*
     * (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (right.contains(e.getPoint())) {
			System.out.println("Right button clicked");
		} else if (left.contains(e.getPoint())) {
			System.out.println("Left button clicked");
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
