import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class LineComponent extends Component {

	private Component start, end;
	
	private ArrayList<Line2D> startBox, endBox;
	
	public static final int delta = 20;
	
	public LineComponent(Drawing drawing, Component start, Component end) {
		super.drawing = new ArrayList<Drawing>();
		super.drawing.add(new Drawing(drawing.getLines(), drawing.getColor()));
		this.start = start;
		this.end = end;
		
		startBox = new ArrayList<Line2D>();
		endBox = new ArrayList<Line2D>();
		
		if(drawing.getLines().size() < 2) {
			
		} else if(drawing.getLines().size() < 6) {
			for(int i = 0; i != 1; i++) {
				startBox.add(drawing.getLines().get(i));
				endBox.add(drawing.getLines().get(drawing.getLines().size()-i-1));
			}
		} else {
			for(int i = 0; i != 3; i++) {
				startBox.add(drawing.getLines().get(i));
				endBox.add(drawing.getLines().get(drawing.getLines().size()-i-1));
			}
		}
		
	}
	
	public boolean intersectsStart(Drawing d) {
		if(start != null) 
			return false;
		
		for(Line2D dl : d.getLines()) {
			for(Line2D sl : startBox) {
				double shortDist = Helpers.shortestDistance(dl, sl);
				if(shortDist < delta) {
					return true;
				}
			}
		}
			
		return false;
	}
	
	public boolean intersectsEnd(Drawing d) {
		if(end != null) 
			return false;
		
		for(Line2D dl : d.getLines()) {
			for(Line2D sl : endBox) {
				double shortDist = Helpers.shortestDistance(dl, sl);
				if(shortDist < delta) {
					return true;
				}
			}
		}
			
		return false;
	}
	
	public void setStart(Component c) {
		start = c;
	}
	
	public void setEnd(Component c) {
		end = c;
	}
	
	public boolean capped() {
		return hasStart() && hasEnd();
	}
	
	public boolean hasStart() {
		return start != null;
	}
	
	public boolean hasEnd() {
		return end != null;
	}

	public Component getStart() {
		return start;
	}
	
	public Component getEnd() {
		return end;
	}
	
	@Override
	public String getCode() {
		return "l";
	}

	@Override
	public void addSubComponent(Component c) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
