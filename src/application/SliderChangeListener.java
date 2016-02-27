/**
 * 
 */
package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * Updates the slider based on a text field changes. 
 * Allows only double numbers to be entered 
 *
 */
public class SliderChangeListener implements ChangeListener<String> {

	/**
	 * Slider to update 
	 */
	private Slider slider; 
	
	/**
	 * Text field to track 
	 */
	private TextField textField;

	/**
	 * Initialises a new change listener 
	 * 
	 * @param textField text field to track  
	 * @param slider slider to update 
	 */
	public SliderChangeListener(TextField textField, Slider slider) {
		this.textField = textField;
		this.slider = slider;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue) {
		try {
			double val = Double.parseDouble(newValue.replace(",",  ".")); 
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
