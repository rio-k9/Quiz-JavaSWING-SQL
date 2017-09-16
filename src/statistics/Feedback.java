package statistics;

import java.util.ArrayList;
import java.util.HashMap;

import statistics.AnswerSets;
import statistics.Serializer;

public class Feedback {
	private AnswerSets answerSet;
	private Integer correct;
	private Integer answered;

	// constructor, pass in correct answers map
	public Feedback(HashMap<Integer, Integer> correctAnswers) {
		answerSet = new AnswerSets(correctAnswers);
		correct = 0;
		answered = 0;
	}


	// MAIN PROGRAM METHODS

	public void newAnswer(Integer q, Integer a) {
		// call after student submits new answer

		if (!answerSet.getStudent().containsKey(q)) {
			// if the relevant entry does not exist,
			// create new entry, then add answer
			ArrayList<Integer> newEntry = new ArrayList<Integer>();
			newEntry.add(a);
			answerSet.studentAnswers.put(q, newEntry);
		} else {
			// else just add answer to existing entry
			answerSet.studentAnswers.get(q).add(a);
		}
		// increment answer count
		answered++;
		// if answer is correct, then increment correct count
		if (a == answerSet.correctAnswers.get(q)) { correct++; }
		// update persistent storage
		writeResults(answerSet);
	}

	public ArrayList<Object> getFeedback(Integer q, Integer a) {
		// create feedback object:
		// [boole correct, int correct, int answered]
		ArrayList<Object> feedback = new ArrayList<Object>();
		feedback.add(checkAnswer(q,a));
		feedback.add(correct);
		feedback.add(answered);
		return feedback;
	}


	// PRIVATE METHODS

	// return boolean whether answer is correct
	private boolean checkAnswer(Integer q, Integer a) {
		
		System.out.println(q);
		System.out.println(answerSet.correctAnswers.get(q));
		
		if (a == answerSet.correctAnswers.get(q)) {
			return true;
		} else {
			return false;
		}
	}

	// Persistent data methods
	private void writeResults(AnswerSets results) {
		Serializer.write(results);
	}

	public static AnswerSets readResults() {
		return Serializer.restore();
	}

}
