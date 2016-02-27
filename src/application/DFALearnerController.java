package application;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.dfa.learner.automaton.DFA;
import pl.dfa.learner.automaton.DFAFactory;
import pl.dfa.learner.automaton.pso.Evaluator;
import pl.dfa.learner.automaton.pso.PSOParams;
import pl.dfa.learner.automaton.pso.Results;
import pl.dfa.learner.automaton.pso.WordSet;
import pl.dfa.learner.automaton.pso.WordSetGenerator;



/**
 * Controller for the GUI
 * 
 *
 */
public class DFALearnerController { 
	
	/**
	 * Main stage object 
	 */
	private Stage mainStage; 
	
	/**
	 * Referendce DFA 
	 */
	private DFA dfa; 
	
	/**
	 * Learned DFA 
	 */
	private DFA learnedDfa; 
	
	/**
	 * Learned status: <code>true</code> if the learned DFA is already learned and available, 
	 * <code>false</code> if not. 
	 */
	private boolean learned; 
	
	
	@FXML
	private Slider iterationNoSlider; 
	@FXML
	private Slider allowedTimeSlider; 
	@FXML
	private Slider particleNumberSlider; 
	@FXML
	private Slider velocityWeightSlider; 
	@FXML
	private Slider persBestWeightSlider; 
	@FXML
	private Slider globalBestWeightSlider; 
	
	
	@FXML
	private TextField iterationsNoTextField; 
	@FXML
	private TextField allowedTimeTextField; 
	@FXML
	private TextField particleNumberTextField; 
	@FXML
	private TextField velocityWeightTextField; 
	@FXML
	private TextField persBestWeightTextField; 
	@FXML
	private TextField globalBestWeightTextField; 
	
	
	@FXML
	private TextField maxWordLengthTextField; 
	
	@FXML
	private TextField maxWordNumberTextField; 
	
	@FXML
	private Slider maxWordLengthSlider; 
	
	@FXML
	private Slider maxWordNumberSlider; 
	
	@FXML
	private TextField maxWordLengthTextFieldTest; 
	
	@FXML
	private TextField maxWordNumberTextFieldTest; 
	
	@FXML
	private Slider maxWordLengthSliderTest; 
	
	@FXML
	private Slider maxWordNumberSliderTest;
	
	
	@FXML 
	private TextArea loadedTextArea; 
	
	@FXML 
	private TextArea learnedDFAText; 
	
	@FXML 
	private TextArea testResultsText; 
	
	@FXML
	private TabPane tabPane; 
	
	@FXML
	private Button loadButton; 
	
	@FXML
	private Button learnButton; 
	

	@FXML
	private Button testButton; 
	
	/**
	 * Search results 
	 */
	private Results results; 
	
	/**
	 * PSO parameters 
	 */
	private PSOParams params;
	
	@FXML
	private ProgressBar progressBar;  
	
	@FXML
	private Text statusText; 
	
	/**
	 * Learning set 
	 */
	private WordSet learningSet; 
	
	/**
	 * Testing set 
	 */
	private WordSet testingSet; 
	
	
	/**
	 * Initialises the controls (sliders and text fields) 
	 */
	public void initControls() { 
		this.learned = false; 
		
//		this.loadedTextArea.setFont(Font.font("", 12)); 
//		this.learnedTextArea.setFont(Font.font("Monospaced", 12));
		
		this.iterationsNoTextField.setText(
				String.valueOf(Math.round(this.iterationNoSlider.getValue()))); 
		this.iterationsNoTextField.textProperty().addListener(new SliderChangeListener(this.iterationsNoTextField, this.iterationNoSlider));
		this.iterationNoSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number oldValue, Number newValue) { 
                        iterationsNoTextField.setText(String.valueOf(Math.round(newValue.doubleValue())));
                }
            });
		
		this.allowedTimeTextField.setText(
				String.valueOf(Math.round(this.allowedTimeSlider.getValue()))); 
		this.allowedTimeTextField.textProperty().addListener(new SliderChangeListener(
				this.allowedTimeTextField, this.allowedTimeSlider));
		this.allowedTimeSlider.valueProperty().addListener(new TextFieldDoubleChangeListener(this.allowedTimeTextField)); 
		
		this.particleNumberTextField.setText(
				String.valueOf(Math.round(this.particleNumberSlider.getValue()))); 
		this.particleNumberTextField.textProperty().addListener(new SliderChangeListener(
				this.particleNumberTextField, this.particleNumberSlider));
		this.particleNumberSlider.valueProperty().addListener(new TextFieldIntegerChangeListener(this.particleNumberTextField)); 
     
		this.velocityWeightTextField.setText(
				String.valueOf(Math.round(this.velocityWeightSlider.getValue()))); 
		this.velocityWeightTextField.textProperty().addListener(new SliderChangeListener(
				this.velocityWeightTextField, this.velocityWeightSlider));
		this.velocityWeightSlider.valueProperty().addListener(new TextFieldDoubleChangeListener(this.velocityWeightTextField)); 
		
		this.persBestWeightTextField.setText(
				String.valueOf(Math.round(this.persBestWeightSlider.getValue()))); 
		this.persBestWeightTextField.textProperty().addListener(new SliderChangeListener(
				this.persBestWeightTextField, this.persBestWeightSlider));
		this.persBestWeightSlider.valueProperty().addListener(new TextFieldDoubleChangeListener(this.persBestWeightTextField)); 
		
		this.globalBestWeightTextField.setText(
				String.valueOf(Math.round(this.globalBestWeightSlider.getValue()))); 
		this.globalBestWeightTextField.textProperty().addListener(new SliderChangeListener(
				this.globalBestWeightTextField, this.globalBestWeightSlider));
		this.globalBestWeightSlider.valueProperty().addListener(new TextFieldDoubleChangeListener(this.globalBestWeightTextField)); 
		
		this.maxWordLengthTextField.setText(
				String.valueOf(Math.round(this.maxWordLengthSlider.getValue()))); 
		this.maxWordLengthTextField.textProperty().addListener(new SliderChangeListener(
				this.maxWordLengthTextField, this.maxWordLengthSlider));
		this.maxWordLengthSlider.valueProperty().addListener(new TextFieldIntegerChangeListener(this.maxWordLengthTextField)); 
		
		this.maxWordNumberTextField.setText(
				String.valueOf(Math.round(this.maxWordNumberSlider.getValue()))); 
		this.maxWordNumberTextField.textProperty().addListener(new SliderChangeListener(
				this.maxWordNumberTextField, this.maxWordNumberSlider));
		this.maxWordNumberSlider.valueProperty().addListener(new TextFieldIntegerChangeListener(this.maxWordNumberTextField)); 
		
		System.out.println(this.maxWordLengthSliderTest.getValue()); 
		this.maxWordLengthTextFieldTest.setText(
				String.valueOf(Math.round(this.maxWordLengthSliderTest.getValue()))); 
		this.maxWordLengthTextFieldTest.textProperty().addListener(new SliderChangeListener(
				this.maxWordLengthTextFieldTest, this.maxWordLengthSliderTest));
		this.maxWordLengthSliderTest.valueProperty().addListener(new TextFieldIntegerChangeListener(this.maxWordLengthTextFieldTest)); 
		
		System.out.println(this.maxWordNumberSliderTest.getValue());
		this.maxWordNumberTextFieldTest.setText(
				String.valueOf(Math.round(this.maxWordNumberSliderTest.getValue()))); 
		this.maxWordNumberTextFieldTest.textProperty().addListener(new SliderChangeListener(
				this.maxWordNumberTextFieldTest, this.maxWordNumberSliderTest));
		this.maxWordNumberSliderTest.valueProperty().addListener(new TextFieldIntegerChangeListener(this.maxWordNumberTextFieldTest)); 

		
	}
	
	
	/**
	 * Loads the initial default DFA 
	 */
	public void loadInitial() { 
		loadAutomatonSilentExceptions(new File("example.dfa")); 
	}

	
	/**
	 * Loads the initial default DFA 
	 */
	private void loadAutomatonSilentExceptions(File chosenFile) {
		try { 
			this.dfa = DFAFactory.parseFromFile(chosenFile); 
			System.out.println(dfa);
			displayDFA(dfa); 
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("DFA not loaded. ");
			this.setLoadedText("Click load to load DFA. "); 
		} catch(Exception e) { 
			this.setLoadedText("Click load to load DFA. "); 
		} 
	}
	
	
	/**
	 * Loads the automaton, action performed on button click. 
	 * Displays the file chooser and loads the file if a file was selected. 
	 * 
	 * @param event
	 */
	@FXML
	protected void loadAutomatonAction(ActionEvent event) { 
		File initialDirectory = new File("."); 
		FileChooser fileChooser = new FileChooser(); 
		fileChooser.setInitialDirectory(initialDirectory);
		fileChooser.setTitle("Open Resource File"); 
		File chosenFile = fileChooser.showOpenDialog(this.mainStage); 
		if(chosenFile != null) { 
			loadAutomaton(chosenFile); 
			this.learned = false; 
			this.learnedDfa = null; 
		}
	} 
	
	
	/**
	 * Learns the automaton. Action performed on button click. 
	 * Learning is performed in another thread 
	 * @param event
	 */
	@FXML
	protected void learnAutomatonAction(ActionEvent event) { 
		if(this.dfa == null) { 
			displayLearnError("Initial DFA not loaded. "); 
			return; 
		}
		try { 
			this.learned = false; 
			System.out.println("Learning DFA"); 
			this.loadButton.setDisable(true); 
			this.learnButton.setDisable(true); 
			this.testButton.setDisable(true); 
			
			this.params = new PSOParams(); 
			params.setMaxIterations(Integer.parseInt(this.iterationsNoTextField.getText())); 
			params.setParticlesCount(Integer.parseInt(this.particleNumberTextField.getText())); 
			params.setVelWeight(Double.parseDouble(this.velocityWeightTextField.getText().replace(",", "."))); 
			params.setPersonalWeight(Double.parseDouble(this.persBestWeightTextField.getText().replace(",", "."))); 
			params.setGlobalWeight(this.globalBestWeightSlider.getValue()); 
			params.setAllowedTimeMins((int)Math.round(this.allowedTimeSlider.getValue())); 
			this.learnedDFAText.setText("Learning in progress. \nPSO parameters: "+this.params); 
			selectTab(1); 
			updateProgress(0, 1.0);
			System.out.println(params); 
			
			int maxWordLength = Integer.parseInt(this.maxWordLengthTextField.getText()); 
			int wordNumber = Integer.parseInt(this.maxWordNumberTextField.getText()); 
			
			PSOLearner learner = new PSOLearner(params, maxWordLength, wordNumber, this.dfa); 
			learner.addListener(new PSOGuiListener(this)); 
			
			Thread thread = new Thread(learner); 
			thread.start(); 
			
//			displayLearnedDfa(); 
		} catch (NumberFormatException e) { 
			displayLearnError("Error while learning the automaton. Please check your parameter setup. \n\nTechnical details: \n"
					+e.getLocalizedMessage()+", "+e);
		} catch(Exception e) { 
			displayLearnError("Error while learning the automaton. Technical details: "+e.getLocalizedMessage()+", "+e); 
		}
				
	}
	
	
	/**
	 * Tests the generated DFA on a separate testing set 
	 */
	@FXML
	public void performTest() { 
		System.out.println("Running tests. "); 
		if(this.learned == false) { 
			displayTestString("\n\nTesting can be done only after learning. \nLearn the automaton first. "); 
			selectTab(2); 
			return; 
		}
		if(this.learnedDfa == null || this.results.bestSolutions[0] == null) { 
			displayTestString("\n\nNo automatons successfully learned. \nTry learning again with different settings. "); 
			selectTab(2); 
			return; 
		} 
		int maxWordLength = Integer.parseInt(this.maxWordLengthTextFieldTest.getText()); 
		int wordNumber = Integer.parseInt(this.maxWordNumberTextFieldTest.getText()); 
		
		WordSetGenerator wordSetGenerator = new WordSetGenerator(this.dfa); 
		WordSet wordSet = wordSetGenerator.generateWordSet(maxWordLength, wordNumber); 
		this.testingSet = wordSet; 
		
		Evaluator evaluatorTraining = new Evaluator(this.learningSet); 
		double trainingEvaluation = evaluatorTraining.evaluate(this.results.bestSolutions[0]); 
		
		Evaluator evaluatorTest = new Evaluator(this.testingSet); 
		double testEvaluation = evaluatorTest.evaluate(this.results.bestSolutions[0]); 
		
		String report = "Learning test: "+this.learningSet+
				"\nLearning performance: \t"+trainingEvaluation+
				(trainingEvaluation == 0 ? "\nTest PASSED" : "\nTest FAILED") + 
				"\n\nTesting test: "+this.testingSet+ 
				"\nTest set performance: \t"+testEvaluation + 
				(testEvaluation == 0 ? "\nTest PASSED" : "\nTest FAILED"); 
		
		this.testResultsText.setText(report); 
		selectTab(2); 
		
	}
	
	
	/**
	 * Selects currently active tab 
	 * @param tabIndex tab to show 
	 */
	private void selectTab(int tabIndex) {
		SingleSelectionModel<Tab> selectionModel = this.tabPane.getSelectionModel(); 
		selectionModel.select(tabIndex); 
		
	}


	/**
	 * Updates the text in the test results tab 
	 * 
	 * @param text the text to set 
	 */
	private void displayTestString(String text) {
		this.testResultsText.setText(text);
		
	}


	/**
	 * Updates the text in the loaded DFA tab 
	 * 
	 * @param toDisplay the text to set 
	 */
	private void setLoadedText(String toDisplay) { 
		this.loadedTextArea.setText(toDisplay); 
		SingleSelectionModel<Tab> selectionModel = this.tabPane.getSelectionModel();
		selectionModel.select(0); 
	}


	/**
	 * Displays learning errors 
	 * 
	 * @param string error description 
	 */
	private void displayLearnError(String string) { 
		SingleSelectionModel<Tab> selectionModel = this.tabPane.getSelectionModel();
		TextArea textArea = (TextArea)this.mainStage.getScene().lookup("#learnedDFAText"); 
		textArea.setText("\nERROR: \n\n"+string); 
		selectionModel.select(1);
		
	}


	/**
	 * Loads the automaton and displays it in the loaded DFA tab 
	 * 
	 * @param chosenFile path to the file 
	 */
	private void loadAutomaton(File chosenFile) {
		try { 
			this.dfa = DFAFactory.parseFromFile(chosenFile); 
			System.out.println(dfa);
			displayDFA(dfa); 
		} catch (IOException e) {
			displayLoadError("Error loading file, please check the input file. "); 
			
		} catch (NumberFormatException e) { 
			displayLoadError("Error loading file, please check the DFA format. "); 
			
		} catch(NullPointerException e) { 
			displayLoadError("Error loading file, please check the DFA format. "); 
		} catch(IndexOutOfBoundsException e) { 
			displayLoadError("Error loading file, please check the DFA format. "); 
		} catch(Exception e) { 
			displayLoadError("Unknown error loading file, please check the file and the DFA format. "); 
		} 
	}

	
	/**
	 * Displays loading errors 
	 *  
	 * @param string error description 
	 */
	private void displayLoadError(String string) { 
		this.loadedTextArea.setText("\nERROR: \n\n"+string); 
		TabPane tabPane = (TabPane) this.mainStage.getScene().lookup("#tabPane"); 
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(0); 
	} 

	
	/**
	 * Displays the DFA in the loaded DFA tab 
	 * 
	 * @param dfa loaded DFA 
	 */
	private void displayDFA(DFA dfa) {
		loadedTextArea.setText(dfa.toString(true));
		
	}

	
	/**
	 * Sets the stage for this controller 
	 * 
	 * @param primaryStage stage being set up by this controller 
	 */
	public void setupStage(Stage primaryStage) {
		this.mainStage = primaryStage; 
	}


	/**
	 * Updates the GUI to reflect search finished event 
	 * 
	 * @param results search results 
	 * @param wordSet training set 
	 */
	public void notifySearchFinished(Results results, WordSet wordSet) { 
		this.results = results; 
		this.learnedDfa = DFAFactory.convertFromSolution(results.bestSolutions[0]); 
		this.learned = true; 
		this.learningSet = wordSet; 
		Platform.runLater(() -> displayResults()); 
		Platform.runLater(() -> updateProgressFinished(results.bestSolutions[0].getEvaluation())); 
	}


	/**
	 * Updates the status bar text and progress bar to reflect search complete event. 
	 * Thread safe.  
	 * 
	 * @param best evaluation of the best found solution 
	 */
	private void updateProgressFinished(double best) {
		System.out.println("Will update progress after finished"); 
		Platform.runLater(() -> updateProgressFinishedGUI(best)); 
	}



	/**
	 * Updates the progress bar and the to represent search finished event. 
	 * Can be accessed only from the GUI thread. 
	 * 
	 * @param best best solution so far 
	 */
	private void updateProgressFinishedGUI(double best) {
		this.progressBar.setProgress(1); 
		if(best == 0) { 
			this.statusText.setText("Serch complete, optimal solution found, evaluation = 0"); 
		} else { 
			this.statusText.setText("Serch complete, best found "+best); 
		}
	}



	/**
	 * Displays the search results in the GUI. 
	 * Can be accessed only by the GUI thread. 
	 * 
	 */
	private void displayResults() {
		SingleSelectionModel<Tab> selectionModel = this.tabPane.getSelectionModel(); 
		learnedDFAText.setText(this.results.toStringDFAs()); 
		selectionModel.select(1); 
		this.loadButton.setDisable(false); 
		this.learnButton.setDisable(false); 
		this.testButton.setDisable(false); 
		
	}


	/**
	 * Updates elements of the GUI to reflect current progress of the search. 
	 * Thread safe. 
	 * 
	 * @param iteration current iteration 
	 * @param best evaluation of the best found solution. 
	 */
	public void updateProgress(int iteration, double best) { 
		System.out.println("Will update progress "+iteration+"/"+this.params.getMaxIterations()); 
		Platform.runLater(() -> updateProgress(
				((double)iteration)/((double)this.params.getMaxIterations()), best)); 
	} 


	/**
	 * Updates elements of the GUI to reflect current progress of the search. 
	 * Can be accessed only from the GUI thread. 
	 * 
	 * @param value
	 * @param best
	 */
	private void updateProgress(double value, double best) {
		this.progressBar.setProgress(value); 
		updateStatusText(best);
	}


	/**
	 * Updates elements of the GUI to reflect the discovery of new best event.  
	 * Thread safe. 
	 * 
	 * @param best evaluation of the best solution found so far 
	 */
	public void foundNewBest(double best) {
		System.out.println("Will update new best "+best);
		Platform.runLater(() -> updateStatusText(best)); 
	
	} 
	
	
	/**
	 * Updates status text.  
	 * Can be acessed only from the GUI thread. 
	 * 
	 * @param best evaluation of the best solution found so far 
	 */
	public void updateStatusText(double best) { 
		DecimalFormat formatter = new DecimalFormat("0.00"); 
		String bestText = formatter.format(best); 
		if(bestText.equals("0.00") || bestText.equals("0,00")) { 
			DecimalFormat formatterSmall = new DecimalFormat("0.0000000000"); 
			bestText = formatterSmall.format(best); 
			if(bestText.equals("0.0000000000") || bestText.equals("0,0000000000") && best != 0) { 
				bestText = Double.toString(best);  
			}
		}
		this.statusText.setText("Serch in progress, best found "+bestText);
	}
}
