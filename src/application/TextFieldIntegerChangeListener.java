/**
 * 
 */
package application;

import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Updates text field values to reflect changes in other numerical GUI elements. 
 * All values are rounded to be integer. 
 * 
 *
 */
public class TextFieldIntegerChangeListener implements ChangeListener<Number> {

	/**
	 * Text field to update 
	 */
	public TextField textField; 
	
	
	/**
	 * Initialises the updater 
	 * @param textField text field to update 
	 */
	public TextFieldIntegerChangeListener(TextField textField ) {
		this.textField = textField; 
	}
	
	
	@Override
	public void changed(ObservableValue<? extends Number> observable,
			Number oldValue, Number newValue) { 
		textField.setText(String.valueOf(Math.round(newValue.doubleValue())));
		
	}

}
