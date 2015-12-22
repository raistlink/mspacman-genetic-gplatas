package platas.guillermo.Qlearning2;

import java.util.List;

import pacman.game.Constants.MOVE;

public class QLearner {

	
	QTable qvalues;
	
	private long updates =0;
	public QLearner(Environment e){
		qvalues = new QTable(e.getNumStates(), e.getNumCols());
		updates =0;
	}
	public void initialize(){
		qvalues.init();
		updates=0;
	}
	
	public State getState(Environment e){
		return new State(e);
		
		
	}
	
	public MOVE getAction(State s){
		return qvalues.getBestAction(s);
	}
	
	public void update(State s, MOVE a, float reward, State s_prime){
		qvalues.update(s, a, s_prime, reward);
		updates ++;
	}
	
	public String toString(){
		String res = String.format("Updates: %d\n%s", updates,qvalues.toString());
		return res;
	}
}
