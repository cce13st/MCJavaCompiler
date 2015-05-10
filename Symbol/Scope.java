package Symbol;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope {
	public HashMap<String, Symbol> map;
	public Scope outer;
	public String loc;
	public boolean isFunc;
	public ArrayList<Scope> descend;
	
	public int ifnum = 0;
	public int whilenum = 0;
	public int fornum = 0;
	public int comnum = 0;

	public Scope(Scope out, String l, boolean isF) {
		map = new HashMap<String, Symbol>();
		descend = new ArrayList<Scope>();
		outer = out;
		if (out != null)
			out.descend.add(this);
		loc = l;
		isFunc = isF;
	}
	
	public Scope newForScope() {
		Scope s = new Scope(this, "for(" + this.fornum + ")", false);
		this.fornum++;
		return s;
	}

	public Scope newIfScope(boolean isIf) {
		String name;
		if (isIf) 
			name = "if(";
		else
			name = "else(";
		
		Scope s = new Scope(this, name + this.fornum + ")", false);
		this.fornum++;
		return s;
	}

	public Scope newCompoundScope() {
		Scope s = new Scope(this, "compound(" + this.fornum + ")", false);
		this.fornum++;
		return s;
	}
	
	public Scope newWhileScope() {
		Scope s = new Scope(this, "while(" + this.fornum + ")", false);
		this.fornum++;
		return s;
	}

	public void addBind(String key, Symbol s) {
		map.put(key, s);
//		System.out.println("addBind");
	}
	
	public Symbol lookup(String key) {
		Scope current = outer;
		while (current != null) {
			Symbol s = current.map.get(key);
			if (s != null) {
				return s;
			}
			current = current.outer;
		}
		return null;
	}
	
	public String getLocName() {
		String locName = loc;
		Scope current = this;
		while(!current.isFunc) {
			current = current.outer;
			if (current.loc == "GLOBAL")
				break;
			locName = current.loc + "-" + locName;
		}
		
		return locName;
	}
}