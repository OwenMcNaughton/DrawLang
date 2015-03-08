import java.util.ArrayList;


public class KeyComponent extends Component {
	
	public int type;
	public static final int INOUT = -1;
	public static final int INPUT = 0;
	public static final int OUTPUT = 1;
	public static final int SEESAW = 2;
	public static final int START = 3;
	
	private LineComponent connection;
	
	public KeyComponent(Drawing drawing, int type) {
		super.drawing = new ArrayList<Drawing>();
		super.drawing.add(new Drawing(drawing.getLines(), drawing.getColor()));
		this.type = type;
	}
	
	public void setConnection(LineComponent lc) {
		connection = lc;
	}
	
	public boolean isConnected() {
		return connection != null;
	}
	
	public LineComponent getConnection() {
		return connection;
	}
	
	public boolean connectToLine(LineComponent lc) {
		if(isConnected()) {
			return false;
		}
		
		switch(type) {
		case INOUT:
			if(lc.intersectsStart(drawing.get(0))) {
				type = INPUT;
				setConnection(lc);
				lc.setStart(this);
			} else if(lc.intersectsEnd(drawing.get(0))) {
				type = OUTPUT;
				setConnection(lc);
				lc.setEnd(this);
			}
			break;
		case START:
			if(lc.intersectsStart(drawing.get(0))) {
				setConnection(lc);
				lc.setStart(this);
			}
			break;
		}
		
		return true;
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
