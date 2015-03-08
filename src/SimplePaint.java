import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * A simple applet where the user can sketch curves in a variety of
 * colors.  A color palette is shown along the right edge of the applet.
 * The user can select a drawing color by clicking on a color in the
 * palette.  Under the colors is a "Clear button" that the user
 * can press to clear the sketch.  The user draws by clicking and
 * dragging in a large white area that occupies most of the applet.
 * The user's drawing is not persistent.  It is lost whenever
 * the applet is repainted for any reason.
 * <p>The drawing that is done in this example violates the rule
 * that all drawing should be done in the paintComponent() method.
 * Although it works, it is NOT good style.
 * <p>This class also contains a main program, and it can be run as
 * a stand-alone application that has exactly the same functionality
 * as the applet.
 */
public class SimplePaint extends JApplet {
	
	public static final int WindowWidth = 1200, WindowHeight = 650;
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Simple Paint");
		SimplePaintPanel content = new SimplePaintPanel();
		window.setContentPane(content);
		window.setSize(WindowWidth, WindowHeight);
		window.setLocation(300, 100);
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setVisible(true);
		
		while(true) {
			content.repaint();
			
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		}
	}
	public static class SimplePaintPanel extends JPanel implements MouseListener, MouseMotionListener {

		private final static int BLACK = 0,
                        RED = 1,     
                        GREEN = 2,   
                        BLUE = 3, 
                        CYAN = 4,   
                        MAGENTA = 5,
                        YELLOW = 6;
      
		private int currentColor = BLACK;
		
		private int prevX, prevY;
		private boolean dragging;

		private ArrayList<Component> components;
		private Drawing currentDrawing;
		private Region region;
		
		SimplePaintPanel() {
			setBackground(Color.WHITE);
			addMouseListener(this);
			addMouseMotionListener(this);
			components = new ArrayList<Component>();
         
			region = new Region(WindowWidth, WindowHeight);
		}
		
		public void paint(Graphics g) {
			super.paintComponent(g);
			
			int width = getWidth();
			int height = getHeight();
			
			int colorSpacing = (height - 56) / 7;
			
			g.setColor(Color.GRAY);
			g.drawRect(0, 0, width-1, height-1);
			g.drawRect(1, 1, width-3, height-3);
			g.drawRect(2, 2, width-5, height-5);
          
			g.fillRect(width - 56, 0, 56, height);
          
			g.setColor(Color.WHITE);
			g.fillRect(width-53,  height-53, 50, 50);
			g.setColor(Color.BLACK);
			g.drawRect(width-53, height-53, 49, 49);
			g.drawString("CLEAR", width-48, height-23); 
			                    
			g.setColor(Color.BLACK);
			g.fillRect(width-53, 3 + 0*colorSpacing, 50, colorSpacing-3);
			g.setColor(Color.RED);
			g.fillRect(width-53, 3 + 1*colorSpacing, 50, colorSpacing-3);
			g.setColor(Color.GREEN);
			g.fillRect(width-53, 3 + 2*colorSpacing, 50, colorSpacing-3);
			g.setColor(Color.BLUE);
			g.fillRect(width-53, 3 + 3*colorSpacing, 50, colorSpacing-3);
			g.setColor(Color.CYAN);
			g.fillRect(width-53, 3 + 4*colorSpacing, 50, colorSpacing-3);
			g.setColor(Color.MAGENTA);
			g.fillRect(width-53, 3 + 5*colorSpacing, 50, colorSpacing-3);
			g.setColor(Color.YELLOW);
			g.fillRect(width-53, 3 + 6*colorSpacing, 50, colorSpacing-3);
			          
			g.setColor(Color.WHITE);
			g.drawRect(width-55, 1 + currentColor*colorSpacing, 53, colorSpacing);
			g.drawRect(width-54, 2 + currentColor*colorSpacing, 51, colorSpacing-2);
			    	   
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(3));
			    	  
			if(currentDrawing != null) {
				g2 = currentDrawing.draw(g2);
			}
			    	  
			for(Component c : components) {
				g2 = c.draw(g2);
			}
		}
		
      private void changeColor(int y) {
         int width = getWidth();           // Width of applet.
         int height = getHeight();         // Height of applet.
         int colorSpacing = (height - 56) / 7;  // Space for one color rectangle.
         int newColor = y / colorSpacing;       // Which color number was clicked?
         
         if (newColor < 0 || newColor > 6)      // Make sure the color number is valid.
            return;
         
         /* Remove the hilite from the current color, by drawing over it in gray.
          Then change the current drawing color and draw a hilite around the
          new drawing color.  */
         
         Graphics g = getGraphics();
         g.setColor(Color.GRAY);
         g.drawRect(width-55, 1 + currentColor*colorSpacing, 53, colorSpacing);
         g.drawRect(width-54, 2 + currentColor*colorSpacing, 51, colorSpacing-2);
         currentColor = newColor;
         g.setColor(Color.WHITE);
         g.drawRect(width-55, 1 + currentColor*colorSpacing, 53, colorSpacing);
         g.drawRect(width-54, 2 + currentColor*colorSpacing, 51, colorSpacing-2);
         g.dispose();
         
      }
      
      /**
       * Returns the currently selected color
       */
      private Color getCurrentColor() {
    	  switch (currentColor) {
    	  case BLACK:
    		  return Color.BLACK;
    	  case RED:
    		  return Color.RED;
    	  case GREEN:
    		  return Color.GREEN;
    	  case BLUE:
    		  return Color.BLUE;
    	  case CYAN:
    		  return Color.CYAN;
    	  case MAGENTA:
    		  return Color.MAGENTA;
    	  case YELLOW:
    		  return Color.YELLOW;
    	  default: 
    		  return null;
    	  }
      }
      
      /**
       * Turn currentDrawing into a component and add a region for it
       */
      public void addComponent() {
    	  Component.evaluate(currentDrawing, components, region);
                   
          currentDrawing = null;
      }
      
      
      /**
       * This is called when the user presses the mouse anywhere in the applet.  
       * There are three possible responses, depending on where the user clicked:  
       * Change the current color, clear the drawing, or start drawing a curve.  
       * (Or do nothing if user clicks on the border.)
       */
      public void mousePressed(MouseEvent evt) {
    	  if(evt.getButton() == MouseEvent.BUTTON1) {
	    	  int x = evt.getX();
	    	  int y = evt.getY();
	         
	    	  int width = getWidth();
	    	  int height = getHeight();
	         
	    	  if(dragging == true) {
	    		  return;      
	    	  }
	    	  
	    	  if(x > width - 53) {
	    		  if(y > height - 53) {
	    			  repaint();
	    			  components = new ArrayList<Component>();
	    			  region = new Region(WindowWidth, WindowHeight);
	    		  } else {
	    			  changeColor(y);  // Clicked on the color palette.
	    		  }
	    	  } else if (x > 3 && x < width - 56 && y > 3 && y < height - 3) {
	    		  prevX = x;
			      prevY = y;
			      dragging = true;
			      currentDrawing = new Drawing();
			      currentDrawing.setColor(getCurrentColor());
			      currentDrawing.addLine(new Line2D.Float(x, y, x, y));
			  }
    	  }
      }
      
      
      /**
       * Called whenever the user releases the mouse button. If the user was drawing 
       * a curve, the curve is done, so we should set drawing to false and get rid of
       * the graphics context that we created to use during the drawing.
       */
      public void mouseReleased(MouseEvent evt) {
         if (dragging == false)
            return;  // Nothing to do because the user isn't drawing.
         dragging = false;
         
         addComponent();
      }      
      
      /**
       * Called whenever the user moves the mouse while a mouse button is held down.  
       * If the user is drawing, draw a line segment from the previous mouse location 
       * to the current mouse location, and set up prevX and prevY for the next call.  
       * Note that in case the user drags outside of the drawing area, the values of
       * x and y are "clamped" to lie within this area.  This avoids drawing on the color 
       * palette or clear button.
       */
      public void mouseDragged(MouseEvent evt) {
         
         if (dragging == false)
            return;  // Nothing to do because the user isn't drawing.
         
         int x = evt.getX();   // x-coordinate of mouse.
         int y = evt.getY();   // y-coordinate of mouse.
         
         if (x < 3)                          // Adjust the value of x,
            x = 3;                           //   to make sure it's in
         if (x > getWidth() - 57)       //   the drawing area.
            x = getWidth() - 57;
         
         if (y < 3)                          // Adjust the value of y,
            y = 3;                           //   to make sure it's in
         if (y > getHeight() - 4)       //   the drawing area.
            y = getHeight() - 4;
         
         currentDrawing.addLine(new Line2D.Float(prevX, prevY, x, y));
         
         prevX = x;  // Get ready for the next line segment in the curve.
         prevY = y;
         
      } // end mouseDragged()
      
      
      public void mouseEntered(MouseEvent evt) { }   // Some empty routines.
      public void mouseExited(MouseEvent evt) { }    //    (Required by the MouseListener
      public void mouseClicked(MouseEvent evt) {
    	  if(evt.getButton() == MouseEvent.BUTTON3) {
    		  System.out.println(JavaParser.parse(components));
    	  }
      }   //    and MouseMotionListener
      
      public void mouseMoved(MouseEvent evt) {
    	  int reg = region.getRegion(evt.getPoint());
    	  
    	  for(Component c : components) {
    		  if(c.region == reg) {
    			  if(c instanceof VariableComponent) {
    				  System.out.println(((VariableComponent)c).getNumberValue());
    			  }
    		  }
    	  }
      }
   }

}