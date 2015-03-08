import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;


public class Region {

	int width, height;
	
	int[][] regions;
	
	int numberOfRegions;
	
	int[][] processed;
	
	public Region(int x, int y) {
		width = x;
		height = y;
		
		regions = new int[x][y];
		processed = new int[x][y];
		
		for(int i = 0; i != x; i++) {
			for(int j = 0; j != y; j++) {
				regions[i][j] = -1;
				processed[i][j] = 0;
			}
		}
		
		numberOfRegions = 0;
	}
	
	public Graphics draw(Graphics g) {
		for(int i = 0; i != width; i++) {
			for(int j = 0; j != height; j++) {
				switch(regions[i][j]) {
				case -1:
					g.setColor(Color.WHITE); break;
				case 0:
					g.setColor(Color.RED); break;
				case 1:
					g.setColor(Color.BLUE); break;
				}
				g.fillRect(i, j, 1, 1);
			}
		}
		return g;
	}
	
	public int getRegion(Point p) {
		return regions[p.x][p.y];
	}
	
	public int floodFill(Point start, ArrayList<Line2D> lines) {
		int replacementRegion = numberOfRegions++;
		
		for(Line2D l : lines) {
			int x1 = (int)l.getX1(); int x2 = (int)l.getX2();
			int y1 = (int)l.getY1(); int y2 = (int)l.getY2();
			
			Point topLeft = new Point(x1 > x2 ? x2 : x1, y1 > y2 ? y2 : y1);
			Point botRite = new Point(x1 > x2 ? x1 : x2, y1 > y2 ? y1 : y2);
			
			for(int i = topLeft.x; i != botRite.x+3; i++) {
				for(int j = topLeft.y; j != botRite.y+3; j++) {
					regions[i][j] = replacementRegion;
				}
			}
		}
		
		int targetRegion = regions[start.x][start.y];
		
		for(int i = 0; i != width; i++) {
			for(int j = 0; j != height; j++) {
				processed[i][j] = 0;
			}
		}
		
		if(targetRegion == replacementRegion) {
			return replacementRegion;
		}
  	  
		LinkedList<Point> queue = new LinkedList<Point>();
		queue.add(start);
  
		while(queue.size() != 0) {
			Point n = queue.getLast();
			queue.removeLast();
			if(regions[n.x][n.y] == targetRegion) {
				regions[n.x][n.y] = replacementRegion;
				
				if(n.y - 1 >= 0) {
					if(processed[n.x][n.y-1] == 0) {
						queue.add(new Point(n.x, n.y-1));
						processed[n.x][n.y-1] =  1;
					}
				}
				
				if(n.x - 1 >= 0) {
					if(processed[n.x-1][n.y] == 0) {
						queue.add(new Point(n.x-1, n.y));
						processed[n.x-1][n.y] = 1;
					}
				}
				
				if(n.y + 1 < height) {
					if(processed[n.x][n.y+1] == 0) {
						queue.add(new Point(n.x, n.y+1));
						processed[n.x][n.y+1] = 1;
					}
				}
				
				if(n.x + 1 < width) {
					if(processed[n.x+1][n.y] == 0) {
						queue.add(new Point(n.x+1, n.y));
						processed[n.x+1][n.y] = 1;
					}
				}
				
			}
		}
		
		return replacementRegion;
		
	}
	
	
}
