package statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Statistics {
	
	private HashMap<Integer, Integer> correctAnswers;
	private HashMap<Integer, ArrayList<Integer>> studentAnswers;
	private HashMap<Integer, ArrayList<Boolean>> correctSet;
	
	// constructor
	public Statistics() {
		this.correctAnswers = statistics.Serializer.restore().getCorrect();
		this.studentAnswers = statistics.Serializer.restore().getStudent();
		this.correctSet = new HashMap<Integer, ArrayList<Boolean>>();
		populateCorrectSet();
	}
	
	private void populateCorrectSet() {
		// create a new map with true/false lists for each question
		
		ArrayList<Integer> allQuestions = new ArrayList<>(this.correctAnswers.keySet());

		for (int q=0; q<allQuestions.size(); q++) { // iterate over every question
			
			//Index the value from the ArrayList
			Integer outerIndexingValue = allQuestions.get(q);
			
			// get all results for a question
			ArrayList<Integer> testQuestion = this.studentAnswers.get(outerIndexingValue);
			
			for (int a=0; a<testQuestion.size(); a++) { // iterate over every answer
							
				//Integer innerIndexingValue = allQuestions.get(q);
				
				//If the correctSet already contains the key, and bool to associated ArrayList
				if (correctSet.containsKey(outerIndexingValue)) {
				
					if ((testQuestion.get(a).equals(this.correctAnswers.get(outerIndexingValue))) || testQuestion.get(a) == this.correctAnswers.get(outerIndexingValue)) {	
						correctSet.get(outerIndexingValue).add(true);
					} else {
						correctSet.get(outerIndexingValue).add(false);
					}
				}
				
				//If the correctSet doesn't already contains the key, add the key as well as the bool to the map
				else {
					
					ArrayList<Boolean> values = new ArrayList<Boolean>();
					
					if ((testQuestion.get(a).equals(this.correctAnswers.get(outerIndexingValue))) || testQuestion.get(a) == this.correctAnswers.get(outerIndexingValue)) {	
						values.add(true);
						correctSet.put(outerIndexingValue, values);
					} else {
						values.add(false);
						correctSet.put(outerIndexingValue, values);
					}
				}
			}
		}
	}
	
	public Integer getCorrectCount(Integer q) {
		// get total correct for question q
		// TODO: sort out correctSet reference
		ArrayList<Boolean> testTotal = this.correctSet.get(q);
		
		Integer count = 0;
		for (Boolean value : testTotal) {
			count += (value) ? 1 : 0;
		}
		return count;
	}
	
	public Integer getTotalCount(Integer q) {
		return this.correctSet.get(q).size();
	}
	
	public Integer getSkippedCount(Integer q) {
		// skipped stored as 0
		ArrayList<Boolean> testSet = this.correctSet.get(q);
		Integer skipped = Collections.frequency(testSet, 0);
		return skipped;
	}
}
