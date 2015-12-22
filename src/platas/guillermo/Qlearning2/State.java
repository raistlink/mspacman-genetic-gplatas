package platas.guillermo.Qlearning2;

import java.util.ArrayList;
import java.util.List;

import pacman.game.Constants.MOVE;

public class State {

	private int state;
	private int currentNode;
	private Environment env;
	
	public State(Environment e){
		this.env = e;
		this.currentNode = e.getCurrentNode();
		this.state = currentNode;
		
	}
	
	public boolean equals(Object e){
		if (e instanceof State){
			return ((State)e).state == this.state;
				
		}
		return false;
	}

	public int getState() {
		return state;
	}


	public int asInt() {
		
		return this.getState();
	}
	
	public MOVE[] getBestAction(){
		return this.env.getBestAction();
	}
}
