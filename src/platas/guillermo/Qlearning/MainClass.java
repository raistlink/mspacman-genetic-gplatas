package platas.guillermo.Qlearning;

import pacman.Executor;
import pacman.controllers.Controller;
import pacman.controllers.GenController;
import pacman.controllers.examples.Legacy;
import pacman.controllers.examples.NearestPillPacMan;
import pacman.game.Constants.MOVE;

public class MainClass {

	public static void main(String[] args){
	
		
		long millis = System.currentTimeMillis() % 1000;
		QLearningController controller = new QLearningController();
		
		
		
		Executor exec = new Executor();
		exec.runGame(new NearestPillPacMan(),controller, true, 10);
		
		System.out.println(QLearningController.learner.toString());
		
		
		millis = System.currentTimeMillis() % 1000 - millis;
		System.out.println("Elapsed Time: "+millis);
	}
}
