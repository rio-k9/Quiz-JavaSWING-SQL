package gui;

import java.util.Arrays;

/**
* BasicFieldValidator
* <p>A collection of methods to make it easier ensure that the database input is allowed</p>
* @author  Adam Kearney
*/

public class FieldLengthValidator {

	//--------------------------------------------------------------------------------//
	//Constructor//
	
	//Private constructor to prevent instantiation
	private FieldLengthValidator() {
	}
	
	//--------------------------------------------------------------------------------//
	//Public Methods//
	
	public static Integer validateText(String inputText, Integer maxLength) {
		
		Integer output = 0;
		
		//Check the the question text is within the allowed limits, is not empty, and is not null
		if ((inputText.length() <= maxLength) && (!inputText.isEmpty()) && (inputText != null)) {
			output = 1;
		}
		
		//Return 0 if invalid
		return output;	
	}
	
	public static Integer validateImageName(String inputText) {
		
		Integer output = 0;
		
		if ((inputText == null) || ((!inputText.isEmpty() && inputText.length() <= 600)) ) {
			output = 1;
		}
		return output;
	}
	
	
	public static Integer validateField(String inputText, String fieldName) {
		
		//Initialize output integer
		Integer output = 0;
		
		//Check for minor variations in the field name; the field names are not case sensitive
		String[] questionTextVariations = {"questionText", "QuestionText", "questiontext"};
		String[] optionVariations = {"option1", "Option1", "option2", "Option2", "option3", "Option3", "option4", "Option4",};
		String[] questionImage = {"questionImage", "QuestionImage", "questionimage"};
		
		//Check if input matches a known field, and validate according to maximum allowed length of the field
		if (Arrays.asList(questionTextVariations).contains(fieldName)) {
			output = validateText(inputText, 600);
		}
		
		else if (Arrays.asList(questionImage).contains(fieldName)) {
			output = validateImageName(inputText);
		}
		
		else if (Arrays.asList(optionVariations).contains(fieldName)) {
			output = validateText(inputText, 120);
		}
		
		return output;
	}
}
