package platas.guillermo.Qlearning;

import java.util.ArrayList;
import java.util.List;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class State {

	public int node;
	public double distanceToPacman;
	public double distanceToPowerPill;
	public Game game;
	
	public State(Game game){
		this.game = game;
		this.node =  game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		this.distanceToPacman = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY), DM.PATH);
		
		int[] indexes = game.getActivePowerPillsIndices();
		double minDistance = 1000.0;
		for(int i = 0; i < indexes.length; i++){
			if (game.getDistance(game.getPacmanCurrentNodeIndex(), indexes[i], DM.MANHATTAN) < minDistance){
				minDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), indexes[i], DM.MANHATTAN);
			}
		}
		
		this.distanceToPowerPill = minDistance;
	}
	
	public boolean equals(Object e){
		if (e instanceof State){
			return ((State)e).node == this.node;
				
		}
		return false;
	}
	
	public MOVE[] getActions(){
	
		return game.getPossibleMoves(game.getGhostCurrentNodeIndex(GHOST.BLINKY));
		
		
	}
	
	public MOVE[] getActions(State e,int s){
		return e.game.getPossibleMoves(s);
	}

	public float getActualReward(){
		return  (float) ((1/distanceToPacman+1)*100);
	}
	
	public int asInt() {
		return node;
	}
}
