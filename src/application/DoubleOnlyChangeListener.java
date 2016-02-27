/**
 * 
 */
package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * Updates the <code>Slider</code> based on <code>TextField</code>  changes 
 *
 */
public class DoubleOnlyChangeListener implements ChangeListener<String> {

	/**
	 * Slider to update  
	 */
	private Slider slider; 
	
	/**
	 * Text field to track
	 */
	private TextField textField;

	
	/**
	 * Initialises the listener 
	 * 
	 * @param textField text field to track 
	 * @param slider slider to update
	 */
	public DoubleOnlyChangeListener(TextField textField, Slider slider) {
		this.textField = textField;
		this.slider = slider;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue) {
		try {
			double val = Double.parseDouble(newValue);
			if (val > slider.getMax()) {
				// TODO try handling this case
			}
			slider.setValue(val);
		} catch (NumberFormatException e) {
			textField.setText(oldValue);
			return;
		}

	}

}
