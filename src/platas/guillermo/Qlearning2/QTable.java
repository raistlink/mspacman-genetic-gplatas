package platas.guillermo.Qlearning2;

import pacman.game.Constants.MOVE;

public class QTable {
	private static final float alpha = 0.3f;
	private static final float gamma = 0.9f; 
	
	float[][] qvalues;
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
				qvalues[i][j]=0;
			}
		}
	}
	
	public float update(State s, MOVE a, State s_prime, float reward){
		int state = s.asInt();
		int action = a.ordinal();
		
		int best_action = getBestAction(s_prime).ordinal();
		qvalues[state][action]=(1-alpha)*qvalues[state][action]+alpha*(reward+gamma*qvalues[s_prime.asInt()][best_action]);
		
		return qvalues[state][action];
	}

	public MOVE getBestAction(State s) {
		MOVE best = null;
		float max = 0.0f;
		for (MOVE a: s.getBestAction()){
			if (best == null){
				best = a;
				max = qvalues[s.asInt()][a.ordinal()];
			} else if (qvalues[s.asInt()][a.ordinal()]>max){
				best = a;
				max = qvalues[s.asInt()][a.ordinal()];
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
