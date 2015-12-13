package platas.guillermo.Qlearning;

import pacman.game.Constants.MOVE;

public class QTable {
	private static final float alpha = 0.3f;
	private static final float gamma = 0.9f; 
	
	public float[][] qvalues;
	private int numStates;
	private int numActions;
	
	public QTable(int numstates, int numactions){
		numStates = numstates;
		numActions = numactions;
		qvalues= new float[numstates][numactions];
	}
	
	public void init(){
		for (int i=0; i<numStates; i++){
			for (int j=0; j<numActions; j++){
				qvalues[i][j]=-1;
			}
		}
	}
	
	public float update(State s, int a, int sprime, float reward){
		int state = s.asInt();
		int action = a;
		
		int best_action = getBestAction(s,sprime);
		qvalues[state][action]=(1-alpha)*qvalues[state][action]+alpha*(reward+gamma*qvalues[sprime][best_action]);
		
		return qvalues[state][action];
	}

	public int getBestAction(State s, int i) {
		int best = 0;
		float max = 0.0f;
		int actionNumber = 0;
		MOVE[] a = s.getActions(s, i);
		for(int j = 0; j < a.length; j++){
			if(a[i] == MOVE.UP){
				actionNumber = 0;
			}
			if(a[i] == MOVE.DOWN){
				actionNumber = 1;
			}
			if(a[i] == MOVE.LEFT){
				actionNumber = 2;
			}
			if(a[i] == MOVE.RIGHT){
				actionNumber = 3;
			}
			if (qvalues[s.asInt()][actionNumber]>max){
				best = actionNumber;
				max = qvalues[s.asInt()][actionNumber];
			}
		}
		return best;
	}
	
	
	public int getBestAction(State s) {
		int best = 0;
		float max = 0.0f;
		int actionNumber = 0;
		for (MOVE a: s.getActions()){
			if(a == MOVE.UP){
				actionNumber = 0;
			}
			if(a == MOVE.DOWN){
				actionNumber = 1;
			}
			if(a == MOVE.LEFT){
				actionNumber = 2;
			}
			if(a == MOVE.RIGHT){
				actionNumber = 3;
			}
			if (qvalues[s.asInt()][actionNumber]>max){
				best = actionNumber;
				max = qvalues[s.asInt()][actionNumber];
			}
		}
		return best;
	}
	
	public float QValue(State s, Action a){
		return qvalues[s.asInt()][a.asInt()];
	}
	
	
	public String toString(){
		String res ="--------- QVALUES ----------\n";
		for (int s =0; s<qvalues.length; s++){
			res += String.format("%-3d :", s);
			for (int a=0; a<qvalues[0].length; a++){
				res +=String.format("% 5.2f ", qvalues[s][a]);
			}
			res +="\n";
		}
		return res;
	}
}
