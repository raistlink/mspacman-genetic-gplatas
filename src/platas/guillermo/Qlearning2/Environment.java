package platas.guillermo.Qlearning2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.Constants.DM;

public class Environment {
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST  = 2;
	public static final int WEST  = 3;
	
	
	private float[] rewards;
	public Game game;
	
	private int currentNode;
	
	public Environment(Game game){
		
		this.game = game;
		
		currentNode =  game.getGhostCurrentNodeIndex(GHOST.PINKY);
		int NodeNumber = game.getNumberOfNodes();
		
		rewards=new float[NodeNumber];
		for (int i=0;i<NodeNumber;i++){
				rewards[i]=(float) (100-game.getDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(GHOST.PINKY), DM.PATH));
		}
		rewards[game.getPacmanCurrentNodeIndex()]=100f;
		
	}
	
	public Environment(int pos, Game game){
		
		this.game = game;
		
		currentNode =  pos;
		int NodeNumber = game.getNumberOfNodes();
		
		rewards=new float[NodeNumber];
		for (int i=0;i<NodeNumber;i++){
				rewards[i]=(float) (100-game.getDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(GHOST.PINKY), DM.PATH));
		}
		rewards[game.getPacmanCurrentNodeIndex()]=100f;
	}
	
	
	public float getActualReward(){
		return rewards[currentNode];
	}
	
	

	public int getCurrentNode(){
		return currentNode;
	}
	

	public int getNumCols() {
		return 4;
	}
	public int getNumStates(){
		//Usamos el numero del maze mas grande para poder usar el QLearner en los 4 mapas
		return 1379;
		
	}
	
	public MOVE[] getBestAction(){
		
		return game.getPossibleMoves(currentNode);
		
	}
}
