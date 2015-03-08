import java.awt.Color;
import java.util.ArrayList;


public class VariableComponent extends Component {

	public int type;
	public static final int NUMBER = 0;
	public static final int CHARACTER = 1;
	public static final int SKETCH = 2;
	
	private int numberValue;
	private char characterValue;
	// TODO represent a sketch with what? bufferedimage?
	
	private ArrayList<LineComponent> connections;
	
	public VariableComponent(Drawing drawing) {
		super.drawing = new ArrayList<Drawing>();
		super.drawing.add(new Drawing(drawing.getLines(), drawing.getColor()));
		
		this.type = NUMBER;
		numberValue = 0;
		
		connections = new ArrayList<LineComponent>();
	}
	
	public void addValue(Drawing drawing) {
		if(drawing.getLines().size() == 1) {
			switch(type) {
			case NUMBER:
				numberValue++;
				super.drawing.add(drawing);
			}
		} else if(drawing.getLines().size() > 1) {
			switch(type) {
			case NUMBER:
				numberValue += 10;
				drawing.setColor(new Color(20, 200, 150));
				super.drawing.add(drawing);
			}
		}
	}
	
	public void addConnection(LineComponent lc) {
		connections.add(lc);
	}
	
	public boolean connectToLine(LineComponent lc) {
		if(lc.intersectsStart(drawing.get(0))) {
			addConnection(lc);
			lc.setStart(this);
		} else if(lc.intersectsEnd(drawing.get(0))) {
			addConnection(lc);
			lc.setEnd(this);
		}
		
		return true;
	}
	
	public ArrayList<LineComponent> getConnections() {
		return connections;
	}
	
	public int getNumberValue() {
		return numberValue;
	}
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSubComponent(Component c) {
		// TODO Auto-generated method stub
		
	}

}
