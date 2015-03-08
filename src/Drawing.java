import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;


public class Drawing {
	
	private ArrayList<Line2D> lines;
	private Color color;
	
	public Drawing() {
		this.lines = new ArrayList<Line2D>();
	}
	
	public Drawing(ArrayList<Line2D> lines, Color color) {
		this.lines = lines;
		this.color = color;
	}
	
	public Graphics2D draw(Graphics2D g2) {
		g2.setColor(color);
		for(Line2D l : lines) {
			g2.draw(l);
		}
		return g2;
	}
	
	public void addLine(Line2D line) {
		this.lines.add(line);
	}
	
	public void addLines(ArrayList<Line2D> lines) {
		this.lines.addAll(lines);
	}
	
	public void setLineBounds(int start, int end) {
		ArrayList<Line2D> tempLines = new ArrayList<Line2D>();
		
		for(int i = start; i != end; i++) {
			tempLines.add(lines.get(i));
		}
		
		lines = tempLines;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public ArrayList<Line2D> getLines() {
		return lines;
	}
	
	public Color getColor() {
		return color;
	}

}
