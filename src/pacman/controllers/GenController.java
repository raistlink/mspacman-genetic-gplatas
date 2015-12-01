package pacman.controllers;


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
import com.fuzzylite.term.Trapezoid;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.Game;

public class GenController extends Controller<MOVE>{

	
	int phenotype[][];
	
	public GenController(int[][] phen){
		
		phenotype = new int[11][4];
		
		for(int i = 0; i < 11; i++){
			for(int j = 0; j<4; j++){
				phenotype[i][j] = phen[i][j];			
				}
		}
		
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		
		Engine engine = new Engine();
		engine.setName("DANGER");
		
		InputVariable Eatability = new InputVariable();
		Eatability.setEnabled(true);
		Eatability.setName("Eatability");
		Eatability.setRange(0.000, 1.000);
		Eatability.addTerm(new Trapezoid("NO", (double) phenotype[0][0],(double) phenotype[0][1], (double) phenotype[0][2], (double) phenotype[0][3]));
		Eatability.addTerm(new Trapezoid("YES", (double) phenotype[1][0],(double) phenotype[1][1], (double) phenotype[1][2], (double) phenotype[1][3]));
		engine.addInputVariable(Eatability);
		
		InputVariable DangerDistance = new InputVariable();
		DangerDistance.setEnabled(true);
		DangerDistance.setName("DangerDistance");
		DangerDistance.setRange(0.000, 1.000);
		DangerDistance.addTerm(new Trapezoid("NEAR", (double) phenotype[2][0],(double) phenotype[2][1], (double) phenotype[2][2], (double) phenotype[2][3]));
		DangerDistance.addTerm(new Trapezoid("NORMAL", (double) phenotype[3][0],(double) phenotype[3][1], (double) phenotype[3][2], (double) phenotype[3][3]));
		DangerDistance.addTerm(new Trapezoid("FAR", (double) phenotype[4][0],(double) phenotype[4][1], (double) phenotype[4][2], (double) phenotype[4][3]));
		engine.addInputVariable(DangerDistance);
		
		InputVariable BigPillDistance = new InputVariable();
		BigPillDistance.setEnabled(true);
		BigPillDistance.setName("BigPillDistance");
		BigPillDistance.setRange(0.000, 1.000);
		BigPillDistance.addTerm(new Trapezoid("NEAR", (double) phenotype[5][0],(double) phenotype[5][1], (double) phenotype[5][2], (double) phenotype[2][3]));
		BigPillDistance.addTerm(new Trapezoid("NORMAL", (double) phenotype[6][0],(double) phenotype[6][1], (double) phenotype[6][2], (double) phenotype[3][3]));
		BigPillDistance.addTerm(new Trapezoid("FAR", (double) phenotype[7][0],(double) phenotype[7][1], (double) phenotype[7][2], (double) phenotype[4][3]));
		engine.addInputVariable(BigPillDistance);
		
		InputVariable SmallPillDistance = new InputVariable();
		SmallPillDistance.setEnabled(true);
		SmallPillDistance.setName("SmallPillDistance");
		SmallPillDistance.setRange(0.000, 1.000);
		SmallPillDistance.addTerm(new Trapezoid("NEAR", (double) phenotype[8][0],(double) phenotype[8][1], (double) phenotype[8][2], (double) phenotype[8][3]));
		SmallPillDistance.addTerm(new Trapezoid("NORMAL", (double) phenotype[9][0],(double) phenotype[9][1], (double) phenotype[9][2], (double) phenotype[9][3]));
		SmallPillDistance.addTerm(new Trapezoid("FAR", (double) phenotype[10][0],(double) phenotype[10][1], (double) phenotype[10][2], (double) phenotype[10][3]));
		engine.addInputVariable(SmallPillDistance);
		
		OutputVariable danger = new OutputVariable();
		danger.setEnabled(true);
		danger.setName("danger");
		danger.setRange(0.000, 1.000);
		danger.fuzzyOutput().setAccumulation(new Maximum());
		danger.setDefuzzifier(new Centroid(200));
		danger.setDefaultValue(Double.NaN);
		danger.setLockValidOutput(false);
		danger.setLockOutputRange(false);
		danger.addTerm(new Ramp("LOW", 0.600, 0.000));
		danger.addTerm(new Ramp("HIGH", 0.400, 1.000));
		engine.addOutputVariable(danger);
		
		OutputVariable priority = new OutputVariable();
		priority.setEnabled(true);
		priority.setName("priority");
		priority.setRange(0.000, 1.000);
		priority.fuzzyOutput().setAccumulation(new Maximum());
		priority.setDefuzzifier(new Centroid(200));
		priority.setDefaultValue(Double.NaN);
		priority.setLockValidOutput(false);
		priority.setLockOutputRange(false);
		priority.addTerm(new Ramp("LOW", 0.600, 0.000));
		priority.addTerm(new Ramp("HIGH", 0.400, 1.000));
		engine.addOutputVariable(priority);
		
		
		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setEnabled(true);
		ruleBlock.setName("");
		ruleBlock.setConjunction(new Minimum());
		ruleBlock.setDisjunction(new Maximum());
		ruleBlock.setActivation(new Minimum());
		ruleBlock.addRule(Rule.parse("if Eatability is NO then danger is LOW", engine));
		ruleBlock.addRule(Rule.parse("if Eatability is YES then danger is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if DangerDistance is NEAR then danger is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if DangerDistance is NORMAL then danger is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if DangerDistance is FAR then danger is LOW", engine));
		ruleBlock.addRule(Rule.parse("if BigPillDistance is NEAR then danger is LOW", engine));
		ruleBlock.addRule(Rule.parse("if BigPillDistance is NORMAL then danger is LOW", engine));
		ruleBlock.addRule(Rule.parse("if BigPillDistance is FAR then danger is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if BigPillDistance is NEAR then priority is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if BigPillDistance is NORMAL then priority is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if BigPillDistance is FAR then priority is LOW", engine));
		ruleBlock.addRule(Rule.parse("if SmallPillDistance is NEAR then priority is LOW", engine));
		ruleBlock.addRule(Rule.parse("if SmallPillDistance is NORMAL then priority is LOW", engine));
		ruleBlock.addRule(Rule.parse("if SmallPillDistance is FAR then priority is HIGH", engine));
		engine.addRuleBlock(ruleBlock);
		
		
		
		double eatable = game.isGhostEdible(GHOST.PINKY)? 1 : 0;
		int[] indexes = game.getActivePowerPillsIndices();
		double bigPillDistance = 200.0;
		int bigPillIndex = 0;
		for(int i = 0; i < indexes.length; i++){
			if (game.getDistance(game.getPacmanCurrentNodeIndex(), indexes[i], DM.MANHATTAN) < bigPillDistance){
				bigPillDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), indexes[i], DM.MANHATTAN);
				bigPillIndex = indexes[i];
			}
		}
		
		indexes = game.getActivePillsIndices();
		double smallPillDistance = 200.0;
		int smallPillIndex = 0;
		for(int i = 0; i < indexes.length; i++){
			if (game.getDistance(game.getPacmanCurrentNodeIndex(), indexes[i], DM.MANHATTAN) < smallPillDistance){
				smallPillDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), indexes[i], DM.MANHATTAN);
				smallPillIndex = indexes[i];
			}
		}
		
		GHOST ghost = GHOST.PINKY;
		
		double ghostDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY), DM.PATH);
		if(game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY), DM.PATH) < ghostDistance){
			ghostDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY), DM.PATH);
			ghost = GHOST.BLINKY;
		}
		if(game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY), DM.PATH) < ghostDistance){
			ghostDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY), DM.PATH);
			ghost = GHOST.INKY;
		}
		if(game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE), DM.PATH) < ghostDistance){
			ghostDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE), DM.PATH);
			ghost = GHOST.SUE;
		}
		 
		
		Eatability.setInputValue(eatable);
		BigPillDistance.setInputValue(bigPillDistance);
		SmallPillDistance.setInputValue(smallPillDistance);
		DangerDistance.setInputValue(ghostDistance);
		engine.process();
		
		if(danger.defuzzify() > 400) return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), DM.PATH);
		else{
			if(priority.defuzzify() > 400) return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), bigPillIndex, DM.PATH);
			else return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), smallPillIndex, DM.PATH);
		}
		
	}

}
