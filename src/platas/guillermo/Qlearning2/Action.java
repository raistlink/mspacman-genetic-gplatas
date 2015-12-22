package platas.guillermo.Qlearning2;

public class Action {
	public static final Action NORTH = new Action("NORTH", 0);
	public static final Action SOUTH = new Action("SOUTH", 1);
	public static final Action EAST  = new Action("EAST" , 2);
	public static final Action WEST  = new Action("WEST" , 3);
	
	private static final Action[] Actions= {NORTH, SOUTH, EAST, WEST};
	
	private String action_name ;
	private int action;
	
	private Action(String name, int dir){
		action_name = name;
		action = dir;
	}
	public int asInt() {
		return action;
	}
	
	public String toString(){
		return "A: "+action_name+" ";
	}

	public static Action getAction(int dir){
		return Actions[dir];
	}
}
