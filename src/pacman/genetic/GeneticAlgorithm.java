package pacman.genetic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import pacman.Executor;
import pacman.controllers.GenController;
import pacman.controllers.examples.Legacy;

import java.util.Arrays;
import java.util.Random;        // for generating random numbers
import java.util.ArrayList;     // arrayLists are more versatile than arrays
import java.util.Scanner;


/**
 * Genetic Algorithm sample class <br/>
 * <b>The goal of this GA sample is to maximize the number of capital letters in a String</b> <br/>
 * compile using "javac GeneticAlgorithm.java" <br/>
 * test using "java GeneticAlgorithm" <br/>
 *
 * @author A.Liapis
 */

public class GeneticAlgorithm {
    // --- constants
    static int CHROMOSOME_SIZE=44;
    static int POPULATION_SIZE=50;

    // --- variables:

    /**
     * The population contains an ArrayList of genes (the choice of arrayList over
     * a simple array is due to extra functionalities of the arrayList, such as sorting)
     */
    ArrayList<Gene> mPopulation;
    

    // --- functions:

    /**
     * Creates the starting population of Gene classes, whose chromosome contents are random
     * @param size: The size of the popultion is passed as an argument from the main class
     */
    public GeneticAlgorithm(int size){
        // initialize the arraylist and each gene's initial weights HERE
        mPopulation = new ArrayList<Gene>();
        for(int i = 0; i < size; i++){
            Gene entry = new Gene();
            entry.randomizeChromosome();
            mPopulation.add(entry);
        }
    }
    /**
     * For all members of the population, runs a heuristic that evaluates their fitness
     * based on their phenotype. The evaluation of this problem's phenotype is fairly simple,
     * and can be done in a straightforward manner. In other cases, such as agent
     * behavior, the phenotype may need to be used in a full simulation before getting
     * evaluated (e.g based on its performance)
     */
    public void evaluateGeneration(){
        Collections.sort(mPopulation, new GeneFitnessComparator());
    }
    /**
     * With each gene's fitness as a guide, chooses which genes should mate and produce offspring.
     * The offspring are added to the population, replacing the previous generation's Genes either
     * partially or completely. The population size, however, should always remain the same.
     * If you want to use mutation, this function is where any mutation chances are rolled and mutation takes place.
     */
    public void produceNextGeneration(){
    	Random rand = new Random();
    	
    	if(rand.nextInt(10) == 1){
    		mPopulation.get(rand.nextInt(POPULATION_SIZE)).mutate();
    	}
    	
    	Gene[] newGenes = new Gene[POPULATION_SIZE/2];
    	for(int i = 0; i < POPULATION_SIZE/2; i++) newGenes[i] = new Gene();
    	
    	newGenes = mPopulation.get(0).reproduce(mPopulation.get(1));
    	
    	
    	for(int i = 0; i < POPULATION_SIZE/2; i++){
    		
    		mPopulation.set(i+POPULATION_SIZE/2, newGenes[i]);
    		mPopulation.get(i+POPULATION_SIZE/2).getPhenotype(mPopulation.get(i+POPULATION_SIZE/2).mChromosome);
    		
    	}
    	
    	
    }

    // accessors
    /**
     * @return the size of the population
     */
    public int size(){ return mPopulation.size(); }
    /**
     * Returns the Gene at position <b>index</b> of the mPopulation arrayList
     * @param index: the position in the population of the Gene we want to retrieve
     * @return the Gene at position <b>index</b> of the mPopulation arrayList
     */
    public static void display_menu() {
        System.out.println ( "1) Lanzar el algoritmo genetico \n2) Correr un test no visual \n3) Correr un test visual" );
        System.out.print ( "Selection: " );
      }
    public static void write (String filename, int[] x) throws IOException{
    	  BufferedWriter outputWriter = null;
    	  outputWriter = new BufferedWriter(new FileWriter(filename));
    	  for(int i = 0; i < 44; i++) outputWriter.write(x[i]+" ");  
    	  outputWriter.flush();  
    	  outputWriter.close();  
    }
    
    public  Gene getGene(int index){ return mPopulation.get(index); }

    // Genetic Algorithm maxA testing method
    public static void main( String[] args ) throws IOException{
    	
    	GeneticAlgorithm population = new GeneticAlgorithm(POPULATION_SIZE);
        Executor exec = new Executor();
    	Scanner in = new Scanner ( System.in );
    	    
    	    display_menu();
    	    switch ( in.nextInt() ) {
    	      case 1:
    	        System.out.println ( "You picked option 1" );
    	        
    	        
    	        int generationCount = 0;
    	        
    	        while(true){
    	        	
    	        	// --- evaluate current generation:
    		        for(int i = 0; i < POPULATION_SIZE; i++){
    	        		population.getGene(i).setFitness((float) exec.runExperiment(new GenController(population.getGene(i).decodedChromosome), new Legacy(), 10));
    		        }
    	            population.evaluateGeneration();
    	            // --- print results here:
    	            // we choose to print the average fitness,
    	            // as well as the maximum and minimum fitness
    	            // as part of our progress monitoring
    	            float avgFitness=0.f;
    	            float minFitness=population.getGene(POPULATION_SIZE-1).getFitness();
    	            float maxFitness=population.getGene(0).getFitness();
    	            for(int i = 0; i < population.size(); i++){
    	                float currFitness = population.getGene(i).getFitness();
    	                avgFitness += currFitness;
    	            }
    	            if(population.size()>0){ avgFitness = avgFitness/population.size(); }
    	            String output = "Generation: " + generationCount;
    	            output += "\t AvgFitness: " + avgFitness;
    	            output += "\t MinFitness: " + minFitness;
    	            output += "\t MaxFitness: " + maxFitness;
    	            System.out.println(output);
    	            // produce next generation:
    	            population.produceNextGeneration();
    	            generationCount++;
    	            if(maxFitness > 30000) break;
    	        }
    	        write("BestGene.txt",population.getGene(0).mChromosome);
    	        System.out.println("El mejor individuo de la poblacion tiene el siguiente genotipo:");
    	        System.out.println(Arrays.toString(population.getGene(0).mChromosome));
    	        System.out.println("El mejor individuo de la poblacion tiene el siguiente genotipo:");
    	        population.getGene(0).printPhenotype (population.getGene(0).decodedChromosome);
    	        break;
    	      case 2:{
    	    	System.out.println ( "You picked option 2" );
	    	    Scanner scanner = new Scanner(new File("BestGene.txt"));
	    	    int [] chromosome = new int [44];
	    	    int i = 0;
	    	    while(scanner.hasNextInt()){
	    	        chromosome[i++] = scanner.nextInt();
	    	    } 
	    	    
	    	    Gene bestGene = new Gene();
	    	    bestGene.mChromosome = chromosome;
	    	    bestGene.getPhenotype(bestGene.mChromosome);
	    	    
	    	    float result = (float) exec.runExperiment(new GenController(bestGene.decodedChromosome), new Legacy(), 1);
	    	    System.out.println("The experiment with the greatest recorded gene has a result of: "+result);
	    	    scanner.close();
	    	    break;
    	      }
    	      case 3:{
    	        System.out.println ( "You picked option 3" );
	    	    Scanner scanner = new Scanner(new File("BestGene.txt"));
	    	    int [] chromosome = new int [44];
	    	    int i = 0;
	    	    while(scanner.hasNextInt()){
	    	        chromosome[i++] = scanner.nextInt();
	    	    } 
	    	    
	    	    Gene bestGene = new Gene();
	    	    bestGene.mChromosome = chromosome;
	    	    bestGene.getPhenotype(bestGene.mChromosome);
	    	    
	    	    exec.runGameTimed(new GenController(bestGene.decodedChromosome), new Legacy(), true);
	    	    scanner.close();
	    	    break;
    	      }
    	      default:
    	        System.err.println ( "Unrecognized option" );
    	        break;
    	    }
        in.close();
       
        
        
    }
};

