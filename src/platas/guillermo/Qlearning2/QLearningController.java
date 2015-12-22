package platas.guillermo.Qlearning2;

import java.util.EnumMap;

import com.fuzzylite.Engine;
import com.fuzzylite.Op;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Ramp;
import com.fuzzylite.term.Rectangle;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class QLearningController extends Controller<EnumMap<GHOST,MOVE>> {

	public static QLearner learner = null;
	public static int counter = 0;
	
	public QLearningController(){
	}
	

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		
		
		EnumMap<GHOST,MOVE> myMoves=new EnumMap<GHOST,MOVE>(GHOST.class);
		myMoves.put(GHOST.BLINKY, game.getGhostLastMoveMade(GHOST.BLINKY).opposite());
		myMoves.put(GHOST.INKY, game.getGhostLastMoveMade(GHOST.INKY).opposite());
		myMoves.put(GHOST.SUE, game.getGhostLastMoveMade(GHOST.SUE).opposite());
		
		
		
		if(counter <= 0){
			learner = new QLearner(new Environment(game));
			learner.initialize();
			counter++;
		}
		
		Environment e = new Environment(game);
		
		State s = learner.getState(e);
		MOVE a = learner.getAction(s);
		
		
		float r = e.getActualReward();
		
		if(game.getNeighbour(game.getGhostCurrentNodeIndex(GHOST.BLINKY),a) > 1 && game.getNeighbour(game.getGhostCurrentNodeIndex(GHOST.PINKY),a) < game.getCurrentMaze().graph.length){
			State sprime = new State(new Environment((game.getNeighbour(game.getGhostCurrentNodeIndex(GHOST.PINKY),a)), game));
			learner.update(s, a, r, sprime);
		}

		
		
		myMoves.put(GHOST.PINKY, a);
		
		return myMoves;
	}



}
