package pacman.genetic;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import pacman.genetic.GeneticAlgorithm;
public class Gene{
    // --- variables:

    /**
     * Fitness evaluates to how "close" the current gene is to the
     * optimal solution (i.e. contains only 1s in its chromosome)
     * A gene with higher fitness value from another signifies that
     * it has more 1s in its chromosome, and is thus a better solution
     * While it is common that fitness is a floating point between 0..1
     * this is not necessary: the only constraint is that a better solution
     * must have a strictly higher fitness than a worse solution
     */
    public float mFitness;
    public Boolean isEvaluated;
    /**
     * The chromosome contains only integers 0 or 1 (we choose to avoid
     * using a boolean type to make computations easier)
     */
    public int mChromosome[];
    public int decodedChromosome[][];

    // --- functions:
    /**
     * Allocates memory for the mChromosome array and initializes any other data, such as fitness
     * We chose to use a constant variable as the chromosome size, but it can also be
     * passed as a variable in the constructor
     */
    Gene() {
        // allocating memory for the chromosome array
        mChromosome = new int[GeneticAlgorithm.CHROMOSOME_SIZE];
        // initializing fitness
        mFitness = 0.f;
    }

    /**
     * Randomizes the numbers on the mChromosome array to values 0 or 1
     */
 public void randomizeChromosome(){
    	
    	Random rand = new Random();
    	
        for(int i = 0; i < GeneticAlgorithm.CHROMOSOME_SIZE; i++){
        	mChromosome[i] = rand.nextInt( 26 );
        }
        
        getPhenotype(mChromosome);
        
        //System.out.println(Arrays.toString(mChromosome));
        //printPhenotype(decodedChromosome);
    }
    
    public void getPhenotype(int[] chromosome){
    	
    	decodedChromosome = new int[11][4];
    	
    	int sum = 0;
    	int count = 0;
    	
    	for(int i = 0; i < 11; i++){
    		for(int j = 0; j < 4; j++){
    			
    			decodedChromosome[i][j] = chromosome[count]+sum;
    			sum = sum+chromosome[count];
    			count++;
    			
    		}
    		sum = 0;
    	}
  	
    }
    
    public void printPhenotype(int[][] decodedChromosome){
    
    		System.out.println("Funcion de pertenencia - Eatability - POCO: "+Arrays.toString(decodedChromosome[0]));
    		System.out.println("Funcion de pertenencia - Eatability - MUCHO: "+Arrays.toString(decodedChromosome[1]));
    		System.out.println("Funcion de pertenencia - DangerDistance - NEAR: "+Arrays.toString(decodedChromosome[2]));
    		System.out.println("Funcion de pertenencia - DangerDistance - NORMAL: "+Arrays.toString(decodedChromosome[3]));
    		System.out.println("Funcion de pertenencia - DangerDistance - FAR: "+Arrays.toString(decodedChromosome[4]));
    		System.out.println("Funcion de pertenencia - BigPillDiscance - NEAR: "+Arrays.toString(decodedChromosome[5]));
    		System.out.println("Funcion de pertenencia - BigPillDiscance - NORMAL: "+Arrays.toString(decodedChromosome[6]));
    		System.out.println("Funcion de pertenencia - BigPillDiscance - FAR: "+Arrays.toString(decodedChromosome[7]));
    		System.out.println("Funcion de pertenencia - SmallPillDiscance - NEAR: "+Arrays.toString(decodedChromosome[8]));
    		System.out.println("Funcion de pertenencia - SmallPillDiscance - NORMAL: "+Arrays.toString(decodedChromosome[9]));
    		System.out.println("Funcion de pertenencia - SmallPillDiscance - FAR: "+Arrays.toString(decodedChromosome[10]));
    		
    }

    /**
     * Creates a number of offspring by combining (using crossover) the current
     * Gene's chromosome with another Gene's chromosome.
     * Usually two parents will produce an equal amount of offpsring, although
     * in other reproduction strategies the number of offspring produced depends
     * on the fitness of the parents.
     * @param other: the other parent we want to create offpsring from
     * @return Array of Gene offspring (default length of array is 2).
     * These offspring will need to be added to the next generation.
     */
    public Gene[] reproduce(Gene other){
    	
        Gene[] result = new Gene[GeneticAlgorithm.POPULATION_SIZE/2];
        
        for(int i = 0; i < GeneticAlgorithm.POPULATION_SIZE/2; i++) result[i] = new Gene();
        
        for(int j = 0; j < GeneticAlgorithm.POPULATION_SIZE/2; j++ ){
	        for(int i = 0; i < 44; i++){
	        	
	        	boolean firstTop = true;	
	        	Random rand = new Random();
	        	
	        	if(mChromosome[i] < other.mChromosome[i]) firstTop = false;
	        	
	        	
	        	if(mChromosome[i] == other.mChromosome[i]) result[j].mChromosome[i] = mChromosome[i];
	        	else if(firstTop) result[j].mChromosome[i] = rand.nextInt(mChromosome[i] - other.mChromosome[i]) + other.mChromosome[i];
	        	else result[j].mChromosome[i] = rand.nextInt(other.mChromosome[i] - mChromosome[i]) + mChromosome[i];
	        	
	        }
        }
        

        return result;
    }

    /**
     * Mutates a gene using inversion, random mutation or other methods.
     * This function is called after the mutation chance is rolled.
     * Mutation can occur (depending on the designer's wishes) to a parent
     * before reproduction takes place, an offspring at the time it is created,
     * or (more often) on a gene which will not produce any offspring afterwards.
     */
    public void mutate(){
    	Random rand = new Random();
    	mChromosome[rand.nextInt(GeneticAlgorithm.CHROMOSOME_SIZE)] = rand.nextInt(26);
    }
    /**
     * Sets the fitness, after it is evaluated in the GeneticAlgorithm class.
     * @param value: the fitness value to be set
     */
    public void setFitness(float value) { mFitness = value; }
    /**
     * @return the gene's fitness value
     */
    public float getFitness() { return mFitness; }
    /**
     * Returns the element at position <b>index</b> of the mChromosome array
     * @param index: the position on the array of the element we want to access
     * @return the value of the element we want to access (0 or 1)
     */
    public int getChromosomeElement(int index){ return mChromosome[index]; }

    /**
     * Sets a <b>value</b> to the element at position <b>index</b> of the mChromosome array
     * @param index: the position on the array of the element we want to access
     * @param value: the value we want to set at position <b>index</b> of the mChromosome array (0 or 1)
     */
    public void setChromosomeElement(int index, int value){ mChromosome[index]=value; }
    /**
     * Returns the size of the chromosome (as provided in the Gene constructor)
     * @return the size of the mChromosome array
     */
    public int getChromosomeSize() { return mChromosome.length; }
    /**
     * Corresponds the chromosome encoding to the phenotype, which is a representation
     * that can be read, tested and evaluated by the main program.
     * @return a String with a length equal to the chromosome size, composed of A's
     * at the positions where the chromosome is 1 and a's at the posiitons
     * where the chromosme is 0
     */
    public String getPhenotype() {
        // create an empty string
        String result="";
        for(int i = 0; i < mChromosome.length; i++){
            // populate it with either A's or a's, depending on the the
            if(mChromosome[i]==1){
                result+= "A";
            } else {
                result+= "a";
            }
        }
        return result;
    }

    
    
}

class GeneFitnessComparator implements Comparator<Gene> {
    public int compare(Gene gene1, Gene gene2) {
    	if(gene1.getFitness() < gene2.getFitness()) return 1;
    	if(gene1.getFitness() > gene2.getFitness()) return -1;
    	return 0;
    }
}
