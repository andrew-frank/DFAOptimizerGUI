package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * Initialises and displays the graphical user interface, implemented in Java FX 2.0 
 *  
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("DFALearner.fxml")); 
			BorderPane root = (BorderPane)loader.load(); 
			DFALearnerController controller = loader.getController(); 
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene); 
			primaryStage.setTitle("AWESOME DFA G3NERATOR!!1!!!\"!"); 
			
			controller.setupStage(primaryStage); 
			controller.initControls(); 
			controller.loadInitial(); 
			primaryStage.show(); 
			
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialises and starts the application 
	 * @param args ignored 
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
}
