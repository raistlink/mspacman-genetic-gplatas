package platas.guillermo.Qlearning;

import pacman.game.Constants.MOVE;

public class Action {
	public static final Action UP = new Action(MOVE.UP, 0);
	public static final Action DOWN = new Action(MOVE.DOWN, 1);
	public static final Action LEFT  = new Action(MOVE.LEFT , 2);
	public static final Action RIGHT  = new Action(MOVE.RIGHT , 3);
	
	public static final Action[] Actions= {UP, DOWN, LEFT, RIGHT};
	
	private MOVE move ;
	private int action;
	
	private Action(MOVE name, int dir){
		move = name;
		action = dir;
	}
	public int asInt() {
		return action;
	}
	
	public String toString(){
		return "A: "+move+" ";
	}

	public static Action getAction(int dir){
		return Actions[dir];
	}
}
