import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;

public class ListView extends View implements MouseInputListener{
	
	JLabel l;
	Rectangle2D.Double right, left;
	int xPoints[], yPoints[];
	boolean hasLeft, hasRight;
	boolean init;
	
	public ListView() {
		l = new JLabel("List View");
		this.add(l);
		
		xPoints = new int[3];
		yPoints = new int[3];
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
    
    public void drawLRButtons(Graphics2D g2) {
        //Right Button
    	g2.setColor(new Color(0, 0, 0, 155));
    	right = new Rectangle2D.Double(this.getWidth() - 35, this.getHeight() / 2 - 50, 35, 100);
    	g2.fill(right); 
    	
    	g2.setColor(Color.WHITE);
    	xPoints[0] = (int)right.getX();
    	xPoints[1] = (int)right.getX();
    	xPoints[2] = this.getWidth();
    	yPoints[0] = (int)right.getY() + (int)right.getHeight() / 5;
    	yPoints[1] = (int)right.getY() + ((int)right.getHeight() / 5) * 4;
    	yPoints[2] = (int)right.getY() + (int)right.getHeight() / 2;
    	g2.fillPolygon(xPoints, yPoints, 3);
    	
    	g2.setColor(Color.BLACK);
    	g2.draw(right);
    	
    	//Left Button
    	g2.setColor(new Color(0, 0, 0, 155));
    	left = new Rectangle2D.Double(0, this.getHeight() / 2 - 50, 35, 100);
    	g2.fill(left);
    	
    	g2.setColor(Color.WHITE);
    	xPoints[0] = (int)left.getWidth();
    	xPoints[1] = (int)left.getWidth();
    	xPoints[2] = 0;
    	yPoints[0] = (int)left.getY() + (int)left.getHeight() / 5;
    	yPoints[1] = (int)left.getY() + ((int)left.getHeight() / 5) * 4;
    	yPoints[2] = (int)left.getY() + (int)left.getHeight() / 2;
    	g2.fillPolygon(xPoints, yPoints, 3);
    	
    	g2.setColor(Color.BLACK);
    	g2.draw(left);
    }
    
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}  
}
