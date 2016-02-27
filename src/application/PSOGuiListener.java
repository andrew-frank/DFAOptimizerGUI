/**
 * 
 */
package application;

import pl.dfa.learner.automaton.pso.PSOEventListener;
import pl.dfa.learner.automaton.pso.Results;
import pl.dfa.learner.automaton.pso.WordSet;

/**
 * Listener for the PSO GUI. 
 * Enables the graphical user interface to respond to search events. 
 *
 */
public class PSOGuiListener implements PSOEventListener {

	
	/**
	 * Controller that reacts to the events 
	 */
	DFALearnerController controller; 
	
	/**
	 * Initialises the listener 
	 * @param controller controller that reacts to the events 
	 */
	PSOGuiListener(DFALearnerController controller) { 
		this.controller = controller; 
	}
	
	
	
	/* (non-Javadoc)
	 * @see pl.dfa.learner.automaton.pso.PSOEventListener#searchFinished(pl.dfa.learner.automaton.pso.Results)
	 */
	@Override
	public void searchFinished(Results results, WordSet wordSet) {
		System.out.println("Search finished. "); 
		controller.notifySearchFinished(results, wordSet); 

	}

	/* (non-Javadoc)
	 * @see pl.dfa.learner.automaton.pso.PSOEventListener#searchStarted()
	 */
	@Override
	public void searchStarted() {
		System.out.println("Search started. "); 

	} 
	
	


	/* (non-Javadoc)
	 * @see pl.dfa.learner.automaton.pso.PSOEventListener#searchUpdate(int, double, double, double, double)
	 */
	@Override
	public void searchUpdate(int iteration, double avg, double min, double max,
			double best) {
		System.out.println("Iteration "+iteration+", avg "+avg+", min "+min+", max "+max+", best "+best); 
		this.controller.updateProgress(iteration, best); 
	}
	

	@Override
	public void foundNewBest(double best) {
		System.out.println("Found new best: "+best); 
		controller.foundNewBest(best); 
	}

}
