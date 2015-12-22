package platas.guillermo.Qlearning2;

import pacman.Executor;
import pacman.controllers.examples.NearestPillPacMan;
import platas.guillermo.Qlearning2.QLearningController;

public class MainClass {

	public static void main(String[] args){
		long millis = System.currentTimeMillis() % 1000;
		QLearningController controller = new QLearningController();
		
		
		
		Executor exec = new Executor();
		exec.runExperiment(new NearestPillPacMan(),controller, 1000);
		
		System.out.println(QLearningController.learner.toString());
		
		
		millis = System.currentTimeMillis() % 1000 - millis;
		System.out.println("Elapsed Time: "+millis);
	}
}
