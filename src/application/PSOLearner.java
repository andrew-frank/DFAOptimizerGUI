/**
 * 
 */
package application;

import java.util.ArrayList;
import java.util.List;

import pl.dfa.learner.automaton.DFA;
import pl.dfa.learner.automaton.DFAFactory;
import pl.dfa.learner.automaton.pso.Evaluator;
import pl.dfa.learner.automaton.pso.PSO;
import pl.dfa.learner.automaton.pso.PSOEventListener;
import pl.dfa.learner.automaton.pso.PSOParams;
import pl.dfa.learner.automaton.pso.Results;
import pl.dfa.learner.automaton.pso.Solution;
import pl.dfa.learner.automaton.pso.WordSet;
import pl.dfa.learner.automaton.pso.WordSetGenerator;

/**
 * Learner thread 
 *
 */
public class PSOLearner implements Runnable {

	/**
	 * PSO parameters 
	 */
	private PSOParams params; 
	
	/**
	 * Reference automaton 
	 */
	private DFA dfa; 
	
	/**
	 * Maximum word length 
	 */
	private int maxWordLength; 
	
	/**
	 * Maximum word number 
	 */
	private int maxWordNumber; 
	
	/**
	 * Best solution 
	 */
	private DFA learnedDFA; 
	
	/**
	 * Listeners
	 */
	private List<PSOEventListener> listeners; 
	
	
	/**
	 * Initialises the new learner thread 
	 * 
	 * @param params PSO parameters 
	 * @param maxWordLength maximum allowed word length 
	 * @param maxWordNumber maximum allowed number of words in the learning set 
	 * @param dfa reference deterministic finite automaton 
	 */
	public PSOLearner(PSOParams params, int maxWordLength, int maxWordNumber, DFA dfa) { 
		this.params = params; 
		this.dfa = dfa; 
		this.maxWordLength = maxWordLength; 
		this.maxWordNumber = maxWordNumber; 
		this.listeners = new ArrayList<PSOEventListener>(); 
	} 
	
	
	/**
	 * Adds a listener 
	 * @param listener listener to be added 
	 */
	public void addListener(PSOEventListener listener) { 
		this.listeners.add(listener); 
	}

	
	@Override
	public void run() {
		PSO pso = new PSO(params); 
		for(PSOEventListener listener: this.listeners) { 
			pso.addListener(listener);
		}
		WordSetGenerator generator = new WordSetGenerator(dfa); 
	
		WordSet wordSet = generator.generateWordSet(maxWordLength, maxWordNumber); 
		System.out.println(wordSet.toString()); 
		
		Results results = pso.search(dfa.getInputs(), wordSet); 
		
		Solution solution = results.bestSolutions[0]; 
		Evaluator evaluator = new Evaluator(wordSet); 
		List<List<Integer>> wrong = evaluator.evaluateVerbose(solution); 
		System.out.println(dfa);
		System.out.println(DFAFactory.convertFromSolution(solution));
		this.learnedDFA = DFAFactory.convertFromSolution(solution); 
		System.out.println(wrong.size()); 
		System.out.println(wrong); 
		
	}
}
