package platas.guillermo.fuzzy;

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
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class FuzzyController extends Controller<EnumMap<GHOST,MOVE>> {

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		
		
		EnumMap<GHOST,MOVE> myMoves=new EnumMap<GHOST,MOVE>(GHOST.class);
		myMoves.put(GHOST.BLINKY, MOVE.NEUTRAL);
		myMoves.put(GHOST.INKY, MOVE.NEUTRAL);
		myMoves.put(GHOST.SUE, MOVE.NEUTRAL);
		
		Engine engine = new Engine();
		engine.setName("DANGER");
		
		InputVariable eatability = new InputVariable();
		eatability.setEnabled(true);
		eatability.setName("CANEAT");
		eatability.setRange(0.000, 1.000);
		eatability.addTerm(new Rectangle("NO", 0.0, 0.5));
		eatability.addTerm(new Rectangle("YES", 0.5, 1));
		engine.addInputVariable(eatability);
		
		
		InputVariable closeToBomb = new InputVariable();
		closeToBomb.setEnabled(true);
		closeToBomb.setName("BOMBSCARE");
		closeToBomb.setRange(0.000, 1.000);
		closeToBomb.addTerm(new Ramp("Close", 50.0, 0.0));
		closeToBomb.addTerm(new Ramp("Far", 40.0, 110.0));
		engine.addInputVariable(closeToBomb);
		
		
		
		OutputVariable danger = new OutputVariable();
		danger.setEnabled(true);
		danger.setName("DANGER");
		danger.setRange(0.000, 1.000);
		danger.fuzzyOutput().setAccumulation(new Maximum());
		danger.setDefuzzifier(new Centroid(200));
		danger.setDefaultValue(Double.NaN);
		danger.setLockValidOutput(false);
		danger.setLockOutputRange(false);
		danger.addTerm(new Ramp("LOW", 0.600, 0.000));
		danger.addTerm(new Ramp("HIGH", 0.400, 1.000));
		engine.addOutputVariable(danger);
		
		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setEnabled(true);
		ruleBlock.setName("");
		ruleBlock.setConjunction(new Minimum());
		ruleBlock.setDisjunction(new Maximum());
		ruleBlock.setActivation(new Minimum());
		ruleBlock.addRule(Rule.parse("if CANEAT is NO then DANGER is LOW", engine));
		ruleBlock.addRule(Rule.parse("if CANEAT is YES then DANGER is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if BOMBSCARE is Close then DANGER is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if BOMBSCARE is Far then DANGER is LOW", engine));
		engine.addRuleBlock(ruleBlock);
		
		//System.out.println(game.getDistance(game.getGhostCurrentNodeIndex(GHOST.BLINKY), game.getPacmanCurrentNodeIndex(), DM.MANHATTAN));
		
		int[] indexes = game.getActivePowerPillsIndices();
		double minDistance = 200.0;
		for(int i = 0; i < indexes.length; i++){
			if (game.getDistance(game.getPacmanCurrentNodeIndex(), indexes[i], DM.MANHATTAN) < minDistance){
				minDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), indexes[i], DM.MANHATTAN);
			}
		}
		
		double eatable = game.isGhostEdible(GHOST.PINKY)? 1 : 0;
		
		
		eatability.setInputValue(eatable);
		closeToBomb.setInputValue(minDistance);
		engine.process();
		
		System.out.println(Op.str(danger.defuzzify()));
		
		if(danger.defuzzify() < 0.400) myMoves.put(GHOST.PINKY, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.PINKY), game.getPacmanCurrentNodeIndex(), DM.PATH));
		else myMoves.put(GHOST.PINKY, game.getNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(GHOST.PINKY), game.getPacmanCurrentNodeIndex(), DM.PATH));
		
		return myMoves;
	}

}
