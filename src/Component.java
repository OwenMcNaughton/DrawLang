import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;


public abstract class Component {
	
	public Component parent;
	
	protected ArrayList<Drawing> drawing;
	
	public int region;
	
	public static final int BAG_MIN_SIZE = 1;
	public static final int VAR_MIN_SIZE = 10;
	public static final int INOUT_MIN_SIZE = 10;
	public static final int BASIC_LOOP_MIN = 5;
		
	/**
	 * Converts drawing into a component
	 */
	public static void evaluate(Drawing drawing, ArrayList<Component> components, Region region) {		
		if(drawing.getColor().equals(Color.BLACK)) {
			evalBag(drawing, components, region);
		} else if(drawing.getColor().equals(Color.GREEN)) {
			evalVar(drawing, components, region);
		} else if(drawing.getColor().equals(Color.MAGENTA)) {
			evalKey(drawing, components, region);
		} else if(drawing.getColor().equals(Color.RED)) {
			evalLine(drawing, components, region);
		}
	}

	/**
	 * Evaluates a black drawing to see if it's a well-formed bag
	 */
	public static void evalBag(Drawing drawing, ArrayList<Component> components, Region region) {
		BagComponent bc = null;
		ArrayList<Line2D> lines = drawing.getLines();
		exit:
		for(int i = 0; i != lines.size(); i++) {
			for(int j = 0; j != lines.size(); j++) {
				if(j - i > BAG_MIN_SIZE) {
					if(lines.get(i).intersectsLine(lines.get(j))) {
						drawing.setLineBounds(i-1, j+1);
						bc = new BagComponent(drawing);
						break exit;
					}
				}
			}
		}
		
		if(bc != null) {
			Point circ1 = new Point((int)(lines.get(0).getX1()), (int)(lines.get(0).getY1()));
			Point circ2 = new Point((int)(lines.get(lines.size()/2).getX1()), (int)(lines.get(lines.size()/2).getY1()));
			Point mid = new Point((circ1.x + circ2.x) / 2, (circ1.y + circ2.y) / 2);
			
			int r = region.floodFill(mid, lines);
			 
			bc.region = r;
			
			for(Component pc : components) {
				if(pc instanceof LineComponent) {
					LineComponent lc = ((LineComponent)pc);
					if(!lc.capped()) {
						if(lc.intersectsStart(drawing)) {
							bc.addCallee(lc);
							lc.setStart(bc);
						} else if(lc.intersectsEnd(drawing)) {
							bc.addCaller(lc);
							lc.setEnd(bc);
						}
					}
				}
			}
			
			components.add(bc);
		}
	}
	
	/**
	 * Evaluates a green drawing to see if it's a well-formed variable
	 */
	public static void evalVar(Drawing drawing, ArrayList<Component> components, Region region) {
		Component c = null;
		ArrayList<Line2D> lines = drawing.getLines();
		exit:
		for(int i = 0; i != lines.size(); i++) {
			for(int j = 0; j != lines.size(); j++) {
				if(j - i > VAR_MIN_SIZE) {
					if(lines.get(i).intersectsLine(lines.get(j))) {
						drawing.setLineBounds(i-1, j+1);
						c = new VariableComponent(drawing);
						break exit;
					}
				}
			}
		}
		
		if(c != null) {
			Point circ1 = new Point((int)(lines.get(0).getX1()), (int)(lines.get(0).getY1()));
			Point circ2 = new Point((int)(lines.get(lines.size()/2).getX1()), (int)(lines.get(lines.size()/2).getY1()));
			Point mid = new Point((circ1.x + circ2.x) / 2, (circ1.y + circ2.y) / 2);
			
			int oldRegion = region.getRegion(mid);
			
			if(oldRegion == -1) {
				c.parent = null;
			} else {
				for(Component pc : components) {
					if(pc.region == oldRegion) {
						c.parent = pc;
						pc.addSubComponent(c);
					}
				}
			}
			
			int newRegion = region.floodFill(mid, lines);
			 
			c.region = newRegion;
			components.add(c);
		} else {
			Point circ1 = new Point((int)(lines.get(0).getX1()), (int)(lines.get(0).getY1()));
			Point circ2 = new Point((int)(lines.get(lines.size()/2).getX1()), (int)(lines.get(lines.size()/2).getY1()));
			Point mid = new Point((circ1.x + circ2.x) / 2, (circ1.y + circ2.y) / 2);
			
			int oldRegion = region.getRegion(mid);
			
			if(oldRegion != -1) {
				for(Component pc : components) {
					if(pc.region == oldRegion && pc instanceof VariableComponent) {
						((VariableComponent)pc).addValue(drawing);
					}
				}
			}
		}
	}
	
	/**
	 * Evaluates a magenta drawing to see if it's a well-formed key
	 */
	private static void evalKey(Drawing drawing, ArrayList<Component> components, Region region) {
		KeyComponent kc = null;
		ArrayList<Line2D> lines = drawing.getLines();
		boolean noLoops = true;
		int loops = 0;
		for(int i = 0; i != lines.size(); i++) {
			for(int j = 0; j != lines.size(); j++) {
				if(j - i > BASIC_LOOP_MIN) {
					if(lines.get(i).intersectsLine(lines.get(j))) {
						noLoops = false;
						loops++;
					}
				}
			}
		}
		
		boolean evaluated = false;
		
		if(noLoops) {
			if(lines.size() > INOUT_MIN_SIZE) {
				for(Component pc : components) {
					if(pc instanceof LineComponent) {
						LineComponent lc = ((LineComponent)pc);
						if(!lc.capped()) {
							if(lc.intersectsStart(drawing)) {
								kc = new KeyComponent(drawing, KeyComponent.INPUT);
								kc.setConnection(lc);
								lc.setStart(kc);
								evaluated = true;
								break;
							} else if(lc.intersectsEnd(drawing)) {
								kc = new KeyComponent(drawing, KeyComponent.OUTPUT);
								kc.setConnection(lc);
								lc.setEnd(kc);
								evaluated = true;
								break;
							}
						}
					}
				}
			}
			if(!evaluated) {
				kc = new KeyComponent(drawing, KeyComponent.INOUT);
			}
			components.add(kc);
		} else {
			if(loops >= 2) {
				kc = new KeyComponent(drawing, KeyComponent.START);
				for(Component pc : components) {
					if(pc instanceof LineComponent) {
						LineComponent lc = ((LineComponent)pc);
						if(!lc.capped()) {
							if(lc.intersectsStart(drawing)) {
								kc.setConnection(lc);
								lc.setStart(kc);
								components.add(kc);
								System.out.println("starting");
							}
						}
					}
				}
				components.add(kc);
			}
		}		
	}
	
	/**
	 * Evaluates a red drawing to see if it's a well-formed line
	 */
	private static void evalLine(Drawing drawing, ArrayList<Component> components, Region region) {
		LineComponent lc = new LineComponent(drawing, null, null);
		
		if(!lc.capped()) {
			for(Component pc : components) {
				if(pc instanceof BagComponent) {
					BagComponent bc = ((BagComponent)pc);
					if(lc.intersectsStart(bc.drawing.get(0)) && !lc.hasStart()) {
						bc.addCallee(lc);
						lc.setStart(bc);
					} else if(lc.intersectsEnd(bc.drawing.get(0)) && !lc.hasEnd()) {
						bc.addCaller(lc);
						lc.setEnd(bc);
					}
				} else if(pc instanceof KeyComponent) {
					KeyComponent kc = ((KeyComponent)pc);
					kc.connectToLine(lc);
				} else if(pc instanceof VariableComponent) {
					VariableComponent vc = ((VariableComponent)pc);
					vc.connectToLine(lc);
				}
			}
		}
		
		if(lc.capped()) System.out.println("capped");
		components.add(lc);
	}
	
	public Drawing getDrawingPart(int i) {
		return drawing.get(i);
	}
	
	/**
	 * Draws the component
	 */
	public Graphics2D draw(Graphics2D g2) {
		for(Drawing d : drawing) {
			g2.setColor(d.getColor());
			for(Line2D l : d.getLines()) {
				g2.draw(l);
			}
		}
		
		return g2;
	}
	
	/**
	 * Converts component into code
	 */
	public abstract String getCode();

	public abstract void addSubComponent(Component c);
	
}
