import java.util.ArrayList;


public class BagComponent extends Component{

	private ArrayList<Component> subComponents;
	
	private ArrayList<LineComponent> callers;
	private ArrayList<LineComponent> callees;
	
	public BagComponent(Drawing drawing) {
		super.drawing = new ArrayList<Drawing>();
		super.drawing.add(new Drawing(drawing.getLines(), drawing.getColor()));
		subComponents = new ArrayList<Component>();
		callers = new ArrayList<LineComponent>();
		callees = new ArrayList<LineComponent>();
	}
	
	@Override
	public void addSubComponent(Component c) {
		subComponents.add(c);
	}
	
	public ArrayList<Component> getSubComponents() {
		return subComponents;
	}
	
	@Override
	public String getCode() {
		StringBuffer sb = new StringBuffer();
		
		for(Component c : subComponents) {
			sb.append(c.getCode());
		}
		
		return sb.toString();
	}

	public void addCaller(LineComponent lc) {
		callers.add(lc);
	}
	
	public void addCallee(LineComponent lc) {
		callees.add(lc);
	}
	
	public ArrayList<LineComponent> getCallers() {
		return callers;
	}
	
	public ArrayList<LineComponent> getCallees() {
		return callees;
	}
	
}
