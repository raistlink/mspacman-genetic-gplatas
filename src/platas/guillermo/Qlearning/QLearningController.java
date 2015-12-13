package platas.guillermo.Qlearning;

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
	
	
	@SuppressWarnings("unused")
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		
	
		EnumMap<GHOST,MOVE> myMoves=new EnumMap<GHOST,MOVE>(GHOST.class);
		myMoves.put(GHOST.PINKY, game.getGhostLastMoveMade(GHOST.PINKY).opposite());
		myMoves.put(GHOST.INKY, game.getGhostLastMoveMade(GHOST.INKY).opposite());
		myMoves.put(GHOST.SUE, game.getGhostLastMoveMade(GHOST.SUE).opposite());
		if(counter <= 0){
			learner = new QLearner(game);
			learner.initialize();
			counter++;
		}
		
		State s = learner.getState(game);
		int a = learner.getAction(s);
		
		MOVE nextMove = null;
		if(a == 0) nextMove = MOVE.UP;
		if(a == 1) nextMove = MOVE.DOWN;
		if(a == 2) nextMove = MOVE.LEFT;
		if(a == 3) nextMove = MOVE.RIGHT;
		
		float r = s.getActualReward();
		
		if(game.getNeighbour(game.getGhostCurrentNodeIndex(GHOST.BLINKY),nextMove) > 1 && game.getNeighbour(game.getGhostCurrentNodeIndex(GHOST.BLINKY),nextMove) < game.getCurrentMaze().graph.length){
			int sprime = (int) learner.qvalues.qvalues[(game.getNeighbour(game.getGhostCurrentNodeIndex(GHOST.BLINKY),nextMove))][a];
			learner.update(s, a, r, sprime);
		}

		
		
		myMoves.put(GHOST.PINKY, nextMove);
		System.out.println(a);
		
		return myMoves;
	}



}
