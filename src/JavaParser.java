import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JavaParser {

	public static boolean parse(ArrayList<Component> components) {
		StringBuffer sb = new StringBuffer();
		
		for(Component c : components) {
			if(c instanceof KeyComponent) {
				KeyComponent kc = ((KeyComponent)c);
				if(kc.type == KeyComponent.START) {
					sb.append("public class Hello {"
								+ "\n"
								+ "\n    public static void main(String[] args) {"
								+ "\n        ");
					
					sb = start(sb, kc);
					
					sb.append("\n    }"
								+ "\n}");
				}
			}
		}
		
		
		try {
			File file = new File("src/Hello.java");
			file.createNewFile();
			PrintWriter writer = new PrintWriter("src/Hello.java", "UTF-8");
			writer.println(sb.toString());
			writer.close();
		} catch (Exception e) { }
		
		return true;
	}
	
	public static StringBuffer start(StringBuffer sb, KeyComponent kc) {
		if(kc.isConnected()) {
			LineComponent lc = kc.getConnection();
			if(lc.hasEnd()) {
				BagComponent bc = (BagComponent)(lc.getEnd());
				ArrayList<String> vars = new ArrayList<String>();
				int varSoFar = 0;
				sb = parseBag(sb, bc, vars, varSoFar);
			}
		}
		
		return sb;
	}

	private static StringBuffer parseBag(StringBuffer sb, BagComponent bc, ArrayList<String> vars, int varSoFar) {
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		char[] alphabet = alpha.toCharArray();
			
		// Declaration
		for(Component c : bc.getSubComponents()) {
			if(c instanceof VariableComponent) {
				VariableComponent vc = ((VariableComponent)c);
				
				vars.add("" + alphabet[vars.size()%26]);
				
				switch(vc.type) {
				case VariableComponent.NUMBER:
					sb.append("int " + vars.get(vars.size()-1) + " = " + vc.getNumberValue() + ";\n        ");
				}
			}
		}
		
		// Usages
		for(Component c : bc.getSubComponents()) {
			if(c instanceof VariableComponent) {
				VariableComponent vc = ((VariableComponent)c);
				
				for(LineComponent lc : vc.getConnections()) {
					if(lc.hasEnd()) {
						Component ec = lc.getEnd();
						if(ec instanceof KeyComponent) {
							KeyComponent kc = ((KeyComponent)ec);
							if(kc.type == KeyComponent.OUTPUT) {
								sb.append("System.out.print(Character.toChars(" + vars.get(varSoFar) + "));\n        ");
							}
						}
					}
				}
				varSoFar++;
			}
		}
		
		for(LineComponent lc : bc.getCallees()) {
			if(lc.hasEnd());
			BagComponent subBc = (BagComponent)lc.getEnd();
			sb = parseBag(sb, subBc, vars, varSoFar);
		}
		
		return sb;
	}
	
	
}
