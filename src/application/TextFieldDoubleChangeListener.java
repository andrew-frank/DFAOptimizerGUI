/**
 * 
 */
package application;

import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Updates the text field to reflect changes to other numerical GUI elements 
 *
 */
public class TextFieldDoubleChangeListener implements ChangeListener<Number> {

	/**
	 * Text field to track 
	 */
	public TextField textField; 
	
	
	/**
	 * Initialises the text field updater
	 * @param textField text field to track 
	 */
	public TextFieldDoubleChangeListener(TextField textField ) {
		this.textField = textField; 
	}
	
	
	@Override
	public void changed(ObservableValue<? extends Number> observable,
			Number oldValue, Number newValue) { 
		DecimalFormat formatter = new DecimalFormat("0.0"); 
		textField.setText(formatter.format(newValue.doubleValue()));
		
	}

}
