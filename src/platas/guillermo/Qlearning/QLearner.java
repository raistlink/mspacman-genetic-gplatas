package platas.guillermo.Qlearning;


import java.util.List;



import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class QLearner {

	
	public QTable qvalues;
	
	private long updates =0;
	public QLearner(Game game){
		qvalues = new QTable(game.getCurrentMaze().graph.length, Action.Actions.length);
		updates =0;
	}
	public void initialize(){
		qvalues.init();
		updates=0;
	}
	
	public State getState(Game e){
		return new State(e);	
	}
	
	
	public int getAction(State s){
		MOVE[] actions = s.getActions();
		
		return qvalues.getBestAction(s);
	}
	
	public void update(State s, int a, float reward, int sprime){
		qvalues.update(s, a, sprime, reward);
		updates ++;
	}
	
	public String toString(){
		String res = String.format("Updates: %d\n%s", updates,qvalues.toString());
		return res;
	}
}
